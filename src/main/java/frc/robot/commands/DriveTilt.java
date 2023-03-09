package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class DriveTilt extends CommandBase {    
    private Swerve s_Swerve;    
    private double error;
    private double currentAngle;

    public DriveTilt(Swerve s_Swerve) {
        
        this.s_Swerve = s_Swerve;
        
        addRequirements(s_Swerve);
        

      
    }

public void intitialize(){
   
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
    return Math.abs(s_Swerve.getPitch()) >=10;
    
}

    @Override
    public void execute() {
        this.currentAngle = s_Swerve.getPitch();
        error = Constants.BEAM_BALANCED_GOAL_DEGREES - currentAngle;
        
            s_Swerve.drive(
              new Translation2d(.1, 0).times(Constants.Swerve.maxSpeed), 
              0 * Constants.Swerve.maxAngularVelocity, 
              true,//robotCentricSup.getAsBoolean(), 
              true
            );    
        
        
    }
}
    // @Override
    // public void execute() {
    //     /* Get Values, Deadband*/
    //     double xTrans = 0;
    //     double yTrans = 0;
    //     double rotation = 0;


    //     if (s_Vision.getpos()>1 && (int)(s_Vision.getpos())>0){
    //         yTrans = .08;
    //     }
    //     else if (s_Vision.getpos()<1 && (int)(s_Vision.getpos())>0){
    //         yTrans = -.08;
    //     }

    //     if (s_Vision.gettilt()>1 && (int)(s_Vision.gettilt()) != 16){
    //         xTrans = .08;}
    //     else if (s_Vision.gettilt()<1 && (int)(s_Vision.gettilt()) != 16){
    //         xTrans = -.08;
    //     }
        
    //      if (s_Vision.getskew()>1 && (int)(s_Vision.getskew()) != 16){
    //          rotation = .08;}
    //      else if (s_Vision.getskew()<1 && (int)(s_Vision.getskew()) != 16){
    //          rotation = -.08;
    //      }
        
    //     s_Swerve.drive(
    //                  new Translation2d(xTrans, yTrans).times(Constants.Swerve.maxSpeed), 
    //                  rotation * Constants.Swerve.maxAngularVelocity, 
    //                  false,//robotCentricSup.getAsBoolean(), 
    //                  true
    //              );
    // }



    //  }



