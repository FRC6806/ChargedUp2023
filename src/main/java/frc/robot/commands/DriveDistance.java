package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistance extends CommandBase {    
    private Swerve  s_Swerve; 
    private double desiredState;
    private double currentstate;
    private double initalstate;
    private double speed;
    
    public DriveDistance(double desiredstateFeet, Swerve s_Swerve, double speed) {
        
        this.s_Swerve = s_Swerve;
        this.desiredState = desiredstateFeet*15.4839;
        initalstate = s_Swerve.mSwerveMods[0].getPosition().distanceMeters;

        if( desiredState < 0 ){
            this.speed = -speed;
        } else {
            this.speed = speed;
        }

        addRequirements(s_Swerve);
      
    }

public void intitialize(){
   initalstate = (s_Swerve.mSwerveMods[0].getPosition().distanceMeters);// + s_Swerve.mSwerveMods[1].getPosition().distanceMeters + s_Swerve.mSwerveMods[2].getPosition().distanceMeters + s_Swerve.mSwerveMods[3].getPosition().distanceMeters)/4.0;
   currentstate = (s_Swerve.mSwerveMods[0].getPosition().distanceMeters); //+ s_Swerve.mSwerveMods[1].getPosition().distanceMeters + s_Swerve.mSwerveMods[2].getPosition().distanceMeters + s_Swerve.mSwerveMods[3].getPosition().distanceMeters)/4.0;          

}

public void end(boolean interupted){
    s_Swerve.drive(
        new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
        0 * Constants.Swerve.maxAngularVelocity, 
        false,//robotCentricSup.getAsBoolean(), 
        true
    );   
}

public boolean isFinished(){
    if(desiredState < 0){
        return currentstate < initalstate + desiredState;
    }else{
        return currentstate > initalstate + desiredState;
    }
 

}

    @Override
    public void execute() {
 

        s_Swerve.drive(
            new Translation2d(speed, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            true,//robotCentricSup.getAsBoolean(), 
            true
        );  
        currentstate = (s_Swerve.mSwerveMods[0].getPosition().distanceMeters );//+ s_Swerve.mSwerveMods[1].getPosition().distanceMeters + s_Swerve.mSwerveMods[2].getPosition().distanceMeters + s_Swerve.mSwerveMods[3].getPosition().distanceMeters)/4.0;          
        SmartDashboard.putNumber("inital state", initalstate);
        SmartDashboard.putNumber("current state", currentstate);
        SmartDashboard.putNumber("desiredState", desiredState);
        
    }
}
 



