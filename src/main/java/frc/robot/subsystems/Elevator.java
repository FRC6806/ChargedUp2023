package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import frc.lib.math.Conversions;
import frc.lib.util.CTREModuleState;
import frc.lib.util.SwerveModuleConstants;
import frc.robot.Constants;
import frc.robot.Instrum;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;


public class Elevator extends SubsystemBase {
    SendableChooser<String> Mode = new SendableChooser<String>();
    private WPI_TalonFX first;
    private WPI_TalonFX second;
    StringBuilder _sb;

public Elevator(int canone,int cantwo){
    first = new WPI_TalonFX(canone);
    second = new WPI_TalonFX(cantwo);
    _sb = new StringBuilder();

    		/* Setup Follower(s) */
		second.configFactoryDefault();
		second.follow(first);

        first.configFactoryDefault();

		/* Configure Sensor Source for Pirmary PID */
		first.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 
											Constants.kPIDLoopIdx,
											Constants.kTimeoutMs);
      /* set deadband to super small 0.001 (0.1 %).
			The default deadband is 0.04 (4 %) */
		first.configNeutralDeadband(0.001, Constants.kTimeoutMs);

		/**
		 * Configure Talon FX Output and Sensor direction accordingly Invert Motor to
		 * have green LEDs when driving Talon Forward / Requesting Postiive Output Phase
		 * sensor to have positive increment when driving Talon Forward (Green LED)
		 */
		first.setSensorPhase(true);
		first.setInverted(false);
		/*
		 * Talon FX does not need sensor phase set for its integrated sensor
		 * This is because it will always be correct if the selected feedback device is integrated sensor (default value)
		 * and the user calls getSelectedSensor* to get the sensor's position/velocity.
		 * 
		 * https://phoenix-documentation.readthedocs.io/en/latest/ch14_MCSensor.html#sensor-phase
		 */
        // _talon.setSensorPhase(true);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		first.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		first.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* Set the peak and nominal outputs */
		first.configNominalOutputForward(0, Constants.kTimeoutMs);
		first.configNominalOutputReverse(0, Constants.kTimeoutMs);
		first.configPeakOutputForward(1, Constants.kTimeoutMs);
		first.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		first.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);

		// _talon.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		// _talon.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		// _talon.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		// _talon.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

		first.config_kF(Constants.kSlotIdx, .238, Constants.kTimeoutMs);
		first.config_kP(Constants.kSlotIdx, .069, Constants.kTimeoutMs);
		first.config_kI(Constants.kSlotIdx, 0, Constants.kTimeoutMs);
		first.config_kD(Constants.kSlotIdx, 0, Constants.kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		first.configMotionCruiseVelocity(1022,Constants.kTimeoutMs);
		first.configMotionAcceleration(558, Constants.kTimeoutMs);

		/* Zero the sensor once on robot boot up */
		first.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);



}


public int getdist(){
    String height = SmartDashboard.getString("Lift Mode", "");
    if (height == "high Cone"){
        return 0;
    }
    else if (height == "med Cone"){
        return 20000;
    }
    else if(height == "Low Cone" ){
        return 0;
    }
    else if (height == "high cube"){
        return 0;

    }
    else if (height == "Mid Cube"){
        return 0;
    }
    else if (height == "Low Cube"){
        return 0;
    }
    else{
        return 0;
        }
 }


public void moveToPosition(int position){
    double targetPos = position;
    first.set(TalonFXControlMode.MotionMagic, targetPos);
    /* Append more signals to print when in speed mode */
    _sb.append("\t err:");
    _sb.append(first.getClosedLoopError(Constants.kPIDLoopIdx));
    _sb.append("\t trg:");
    _sb.append(targetPos);
}


public void manualMove(DoubleSupplier joystick){
    double speed = joystick.getAsDouble();
    first.set(TalonFXControlMode.PercentOutput, speed);
}



public void stop(){
    first.set(TalonFXControlMode.PercentOutput, 0);
    second.set(TalonFXControlMode.PercentOutput, 0);
}

    public void periodic() {
        
        Mode.addOption("High Cone", "HCone");
        Mode.addOption("Med Cone", "MCone");
        Mode.addOption("Low Cone", "LCone");
        Mode.addOption("High Cube", "HCube");
        Mode.addOption("Med Cube", "MCube");
        Mode.addOption("Low Cube", "LCube");
        SmartDashboard.putData("Lift Mode", Mode);

        SmartDashboard.putNumber("Elevator Encoder 1", first.getSelectedSensorPosition());
        SmartDashboard.putNumber("Elevator Encoder 2", second.getSelectedSensorPosition());
        Instrum.Process(first, _sb);
    }
}