package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class HuntOffset extends CommandBase {    
    private Swerve s_Swerve;    
    private Vision s_Vision;
    private boolean run;
    private double pitchChange;
    private double yawChange;
    private double rotChange;
    // private boolean end;

    public HuntOffset(Swerve s_Swerve,Vision s_Vision) {
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
        addRequirements(s_Vision);
        addRequirements(s_Swerve);
      
    }

public void intitialize(){
    if (s_Vision.getYaw() == 0){
        run = false;
     }
     else{
        run = true;
     }

    //  end = false;
}

public void end(boolean interupted){
    s_Swerve.drive(
            new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            true,//robotCentricSup.getAsBoolean(), 
            true
        );
}

public boolean isFinished(){
    if (s_Vision.getPitch() > 13.8 && Math.abs(s_Vision.getYaw()) <= 3)
        {
            return true;
    }
    // else if(end){
    //     return true;
    // }
    else {
        return false;
    }

    
}

public void setEnd(){
    // end = true;
}

    @Override
    public void execute() {
        /* Get Values, Deadband*/

        //System.out.println("test");
        //end = false;
        // if( Math.abs(s_Swerve.getRotation()) < 5 ||s_Swerve.getRotation() > 355){
        //     rotChange = 0; 
        // }else if(  s_Swerve.getRotation() > 180 ){
        //     rotChange = -.4;
        // }else{
        //     rotChange = .4;

        // System.out.println("rotChange" + rotChange);
        // }
        

        if( Math.abs(s_Vision.getYaw()) > 2){
            yawChange = s_Vision.getYaw()*-.02;
            yawChange = -1*(MathUtil.clamp(yawChange, 0, 0.2));
            SmartDashboard.putNumber("pitchChange", yawChange);
            System.out.println("yawChange" + yawChange);
        }else{
            yawChange = 0;
        }



        if(Math.abs(s_Vision.getPitch()) < Constants.CubeTagpitch ){ // Maybe could be 14
            pitchChange = 1/Math.abs(s_Vision.getPitch());
            pitchChange = -1*(MathUtil.clamp(pitchChange, 0, 0.2));
            System.out.println("pitchChange" + pitchChange);

            
        }else{
            pitchChange = 0;
        }

        SmartDashboard.putNumber("pitchChange", pitchChange);

            s_Swerve.drive(
                new Translation2d(pitchChange, yawChange).times(Constants.Swerve.maxSpeed), 
                0 * Constants.Swerve.maxAngularVelocity, 
                true,//robotCentricSup.getAsBoolean(), 
                true
            );
        }

        public void periodic(){
            SmartDashboard.putNumber("pitchChange", pitchChange);
            SmartDashboard.putNumber("yawChange", yawChange);
            SmartDashboard.putNumber("rotChange", rotChange);

        }
        
        }
