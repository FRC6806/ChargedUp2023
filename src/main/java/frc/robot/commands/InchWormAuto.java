// Author: UMN Robotics Ri3d
// Last Updated : January 2023

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// This command self=balances on the charging station using gyroscope pitch as feedback
public class InchWormAuto extends SequentialCommandGroup{


  /** Command to use Gyro data to resist the tip angle from the beam - to stabalize and balanace */
  public InchWormAuto(Swerve s_Swerve) {
    super( 
      new DriveDistance(3 ,s_Swerve,  .3),
      new WaitCommand( 1 ),
      Commands.sequence(
            new DriveDistance(.1 ,s_Swerve, .2),
            new WaitCommand( 1 )
      ).repeatedly().until( s_Swerve::isLevel )

    );

    addRequirements(s_Swerve);
 
      
  }
}