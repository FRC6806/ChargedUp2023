package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
public class Hunt extends CommandBase {    
    private Swerve s_Swerve;    
    private Vision s_Vision;
    private boolean run;

    public Hunt(Swerve s_Swerve,Vision s_Vision) {
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
        addRequirements(s_Vision);
        addRequirements(s_Swerve);
        

      
    }

public void intitialize(){
    if (s_Vision.getpos() == 0){
        run = false;
     }
     else{
        run = true;
     }
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
    if (s_Vision.getpos() == 0){
        run = false;
     }
     else{
        run = true;
     } 
    if (run == false){
        return true;
    }
    else if (s_Vision.getpos()>1 && s_Vision.getpos()<1&&s_Vision.gettilt()>1&&s_Vision.gettilt()<1){
      return true;
       }
    //else{
        return false;
    //}
    
}

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        if (s_Vision.getpos()>1 && (int)(s_Vision.getpos())>0){
            s_Swerve.drive( 
            new Translation2d(0, 0.08).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            false,//robotCentricSup.getAsBoolean(), 
            true
        );}
        else if (s_Vision.getpos()<1 && (int)(s_Vision.getpos())>0){
            s_Swerve.drive(
            new Translation2d(0, -0.08).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            false,//robotCentricSup.getAsBoolean(), 
            true
        );}

        else if (s_Vision.gettilt()>1 && (int)(s_Vision.gettilt()) != 16){
            s_Swerve.drive(
            new Translation2d(0.08, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            false,//robotCentricSup.getAsBoolean(), 
            true
        );}
        else if (s_Vision.gettilt()<1 && (int)(s_Vision.gettilt()) != 16){
            s_Swerve.drive(
            new Translation2d(-0.08, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            false,//robotCentricSup.getAsBoolean(), 
            true
        );}
        
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



