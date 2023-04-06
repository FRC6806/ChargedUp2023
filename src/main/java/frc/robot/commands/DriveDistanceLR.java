package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistanceLR extends CommandBase {    
    private Swerve  s_Swerve; 
    
    private double currentstate;
    private double initialstate;
    private double desiredState;
    private double speed;
    private boolean didThing = false;
    
    public DriveDistanceLR(double desiredstateFeet, Swerve s_Swerve, double speed) {
        
        this.s_Swerve = s_Swerve;
        this.desiredState = desiredstateFeet;//*14.7;//15.4839;
        // this.desiredState = desiredstateFeet; 
        initialstate = s_Swerve.mSwerveMods[0].getPosition().distanceMeters;

        if( desiredState < 0 ){
            this.speed = -speed;
        } else {
            this.speed = speed;
        }

        addRequirements(s_Swerve);
      
    }

public void intitialize(){
   initialstate = (s_Swerve.mSwerveMods[0].getPosition().distanceMeters);// + s_Swerve.mSwerveMods[1].getPosition().distanceMeters + s_Swerve.mSwerveMods[2].getPosition().distanceMeters + s_Swerve.mSwerveMods[3].getPosition().distanceMeters)/4.0;
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

        if (currentstate < desiredState - initialstate) {
            didThing = false;
        }
        
        return currentstate < desiredState + initialstate;
        
    }else if(desiredState >= 0){

        if (currentstate > initialstate + desiredState) {
            didThing = false;
        }
        
        return currentstate > initialstate + desiredState;
        
    } else {
        // System.out.println("test3");
        // didThing = false;
        return true;
    }
 

}

    @Override
    public void execute() {

        
 

        s_Swerve.drive(
            new Translation2d(0, speed).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            true,//robotCentricSup.getAsBoolean(), 
            true
        );  

        if (!didThing) {
            didThing = true;
            initialstate = s_Swerve.mSwerveMods[0].getPosition().distanceMeters;

        
            desiredState = desiredState + Math.abs(initialstate);
            
            System.out.println(didThing);
        }
        // System.out.println("currentstate: " + currentstate);
        // System.out.println("desiredState: " + desiredState);
        // System.out.println("initialstate: " + initialstate);
        currentstate = Math.abs((s_Swerve.mSwerveMods[0].getPosition().distanceMeters ));//+ s_Swerve.mSwerveMods[1].getPosition().distanceMeters + s_Swerve.mSwerveMods[2].getPosition().distanceMeters + s_Swerve.mSwerveMods[3].getPosition().distanceMeters)/4.0;          
        SmartDashboard.putNumber("inital state", initialstate);
        SmartDashboard.putNumber("current state", currentstate);
        SmartDashboard.putNumber("desiredState", desiredState);
        
    }
}
 





