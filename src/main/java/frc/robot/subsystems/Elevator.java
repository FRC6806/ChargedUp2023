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
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import frc.robot.sim.PhysicsSim;


public class Elevator extends SubsystemBase {
    SendableChooser<String> Mode = new SendableChooser<String>();
    private WPI_TalonFX first;
    private WPI_TalonFX second;
    private int position;
    StringBuilder _sb;
    // DigitalInput bottomlimitSwitch1 = new DigitalInput(1);
    // DigitalInput bottomlimitSwitch2 = new DigitalInput(3);
    // DigitalInput toplimitSwitch1 = new DigitalInput(0);
    // DigitalInput toplimitSwitch2 = new DigitalInput(2);

public Elevator(int canone, int cantwo){
    double m_targetMin = 0;
	double m_targetMax = 25000;
	double m_targetHigh = 30000;
    
    first = new WPI_TalonFX(canone);
    second = new WPI_TalonFX(cantwo);
    _sb = new StringBuilder();
    /* Hardware */
	Joystick _joy = new Joystick(1);

	/* Used to build string throughout loop */
	StringBuilder _sb = new StringBuilder();

	/** How much smoothing [0,8] to use during MotionMagic */
	int _smoothing = 0;

	/** save the last Point Of View / D-pad value */
	int _pov = -1;

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

		first.config_kF(Constants.kSlotIdx, 0.05, Constants.kTimeoutMs); //0.238
		first.config_kP(Constants.kSlotIdx, 0.05, Constants.kTimeoutMs); //.069
		first.config_kI(Constants.kSlotIdx, 0.001, Constants.kTimeoutMs);
		first.config_kD(Constants.kSlotIdx, 0.85, Constants.kTimeoutMs);//.85

		/* Set acceleration and vcruise velocity - see documentation */
		first.configMotionCruiseVelocity(6806, Constants.kTimeoutMs); //1422
		first.configMotionAcceleration(3403, Constants.kTimeoutMs);//300

		/* Zero the sensor once on robot boot up */
		first.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    
        first.set(TalonFXControlMode.PercentOutput, 0);

}



    
public void setLevel(){
    String height = Mode.getSelected();

    if (height.equals("High")){
        setPosition(Constants.HIGH_ELEVATOR_VALUE);
        SmartDashboard.putNumber("level",3);
    }
    else if (height.equals("Med")){
        setPosition(Constants.MID_ELEVATOR_VALUE);
        SmartDashboard.putNumber("level",2);
    }
    else if(height.equals("Low")){
        setPosition(Constants.LOW_ELEVATOR_VALUE);
        SmartDashboard.putNumber("level",1);
        
    } 

 }


public void moveToPosition(int position){
    double targetPos = position;
    double horizontalHoldOutput = .13;
    first.set(TalonFXControlMode.MotionMagic, targetPos);

    /* Append more signals to print when in speed mode */
    _sb.append("\t err:");
    _sb.append(first.getClosedLoopError(Constants.kPIDLoopIdx));
    _sb.append("\t trg:");
    _sb.append(targetPos);
    Instrum.Process(first, _sb);
}

public void moveToPosition(){
    double targetPos = position;
    double horizontalHoldOutput = .13;
    first.set(TalonFXControlMode.MotionMagic, targetPos);

    /* Append more signalb s to print when in speed mode */
    _sb.append("\t err:");
    _sb.append(first.getClosedLoopError(Constants.kPIDLoopIdx));
    _sb.append("\t trg:");
    _sb.append(targetPos);
    Instrum.Process(first, _sb);
}




public void manualMove(DoubleSupplier joystick){
    double speed = joystick.getAsDouble();
    first.set(TalonFXControlMode.PercentOutput, speed);
}



public void stop(){
    first.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    first.setSelectedSensorPosition(0);

}

public void setZero(){
    first.set(ControlMode.PercentOutput, 0);

}

// public boolean getBottomLimitSwitches(){
//     return !(bottomlimitSwitch1.get() && bottomlimitSwitch2.get());
// }

// public boolean getTopLimitSwitches(){
//     return !(toplimitSwitch1.get() || toplimitSwitch2.get());
// }


public void periodic() {
        
        Mode.addOption("High Cone", "HCone");
        Mode.addOption("Med Cone", "MCone");
        Mode.addOption("Low Cone", "LCone");
        Mode.addOption("High Cube", "HCube");
        Mode.addOption("Med Cube", "MCube");
        Mode.addOption("Low Cube", "LCube");

        Mode.addOption("High", "H");
        Mode.addOption("Med", "M");
        Mode.addOption("Low", "L");

        SmartDashboard.putData("Lift Mode", Mode);
        SmartDashboard.putNumber("Elevator Encoder 1", first.getSelectedSensorPosition());

        // SmartDashboard.putBoolean("Bottom Limit Switch 1", bottomlimitSwitch1.get());
        // SmartDashboard.putBoolean("Bottom Limit Switch 2", bottomlimitSwitch2.get());

        // SmartDashboard.putBoolean("Top Limit Switch 1", toplimitSwitch1.get());
        // SmartDashboard.putBoolean("Top Limit Switch 2", toplimitSwitch2.get());

        // SmartDashboard.putBoolean("Bottom Switches", getBottomLimitSwitches());
        // SmartDashboard.putBoolean("Top Switches", getTopLimitSwitches());

        // if( getBottomLimitSwitches() ){
        //     first.setSelectedSensorPosition(0);
        // }
}

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position; 
    }

    public double getEncoder(){
        return first.getSelectedSensorPosition(); 
    }
}