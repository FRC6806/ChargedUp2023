package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;


public class Elevator extends SubsystemBase {
    SendableChooser<String> Mode = new SendableChooser<String>();
    private TalonFX first;
    private TalonFX second;
public Elevator(int canone,int cantwo){
    first = new TalonFX(canone);
    second = new TalonFX(cantwo);
}


public int getdist(){
    String height = SmartDashboard.getString("Lift Mode", "");
    if (height == "high Cone"){
        return 0;
    }
    else if (height == "med Cone"){
        return 0;
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
        return 0;}
    }

public void movedown(int dist){
    first.set(TalonFXControlMode.Position, -dist);
    second.set(TalonFXControlMode.Position, -dist);
}
public void moveup(int dist){
    first.set(TalonFXControlMode.Position, dist);
    second.set(TalonFXControlMode.Position, dist);
}
public void movemin(){
    first.set(TalonFXControlMode.Position, 0);
    second.set(TalonFXControlMode.Position, 0);
}
public void movemax(){
    first.set(TalonFXControlMode.Position, 0);
    second.set(TalonFXControlMode.Position, 0);
    
}

public void manualup(){
    first.set(TalonFXControlMode.PercentOutput, 0.1);
    second.set(TalonFXControlMode.PercentOutput, 0.1);
}

public void manualdown(){
    first.set(TalonFXControlMode.PercentOutput, -0.1);
    second.set(TalonFXControlMode.PercentOutput, -0.1);
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
        
    }
}