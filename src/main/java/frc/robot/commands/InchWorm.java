// Author: UMN Robotics Ri3d
// Last Updated : January 2023

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// This command self=balances on the charging station using gyroscope pitch as feedback
public class InchWormAuto extends SequentialCommandGroup{

  private Swerve s_Swerve;
  private double drivePower;

  /** Command to use Gyro data to resist the tip angle from the beam - to stabalize and balanace */
  public InchWorm(Swerve s_Swerve) {
    super( 
      new DriveDistance(3 ,s_Swerve, double .3),
      new WaitCommand( 1 ),
      new Commands.sequence (
            new DriveDistance(.1 ,s_Swerve, double .2),
            new WaitCommand( 1 ),
      ).repeatedly().until( Math.abs( s_Swerve.getPitch() ) <.5 )
    
    
    )




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
    return Math.abs(s_Swerve.getPitch()) <=.5;
    
}

public void execute() {
  s_Swerve.driveDistance(.1, .2);
  try {
    wait(1000, 1);
  } catch (InterruptedException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
}



  


   


}