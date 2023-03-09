// Author: UMN Robotics Ri3d
// Last Updated : January 2023

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

// This command self=balances on the charging station using gyroscope pitch as feedback
public class BalanceOnBeamCommand3 extends CommandBase {

  private Swerve s_Swerve;

  private double error;
  private double currentAngle;
  private double drivePower;

  /** Command to use Gyro data to resist the tip angle from the beam - to stabalize and balanace */
  public BalanceOnBeamCommand3(Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    addRequirements(s_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    s_Swerve.SetModeBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Uncomment the line below this to simulate the gyroscope axis with a controller joystick
    // Double currentAngle = -1 * Robot.controller.getRawAxis(Constants.LEFT_VERTICAL_JOYSTICK_AXIS) * 45;
    this.currentAngle = s_Swerve.getPitch();

    error = (currentAngle - Constants.BEAM_BALANCED_GOAL_DEGREES) / 20;
    
    drivePower = Math.min(Math.abs( Constants.BEAM_BALANACED_DRIVE_KP* error) , 1);
    drivePower = Math.copySign(drivePower, error);


    //drivePower = Math.min(Constants.BEAM_BALANACED_DRIVE_KP * error, 1);



    // Our robot needed an extra push to drive up in reverse, probably due to weight imbalances
    if (drivePower < 0) {
      drivePower *= Constants.BACKWARDS_BALANCING_EXTRA_POWER_MULTIPLIER;
    }

    // Limit the max power
    if (Math.abs(drivePower) > 0.2) {
      drivePower = Math.copySign(0.1, drivePower);
    }

    s_Swerve.drive(new Translation2d(drivePower, 0).times(Constants.Swerve.maxSpeed),
    0 * Constants.Swerve.maxAngularVelocity, 
    true,//robotCentricSup.getAsBoolean(), 
    true );
    
    // Debugging Print Statments
    SmartDashboard.putNumber("Current Angle: " , currentAngle);
    SmartDashboard.putNumber("Error " , error);
    SmartDashboard.putNumber("Drive Power: " , drivePower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_Swerve.drive(
            new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            false,//robotCentricSup.getAsBoolean(), 
            true
        );
   
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
     return error >= 0;

    //return Math.abs(error) < Constants.BEAM_BALANCED_ANGLE_TRESHOLD_DEGREES || Math.abs(error) > Constants.BEAM_BALANCED_ANGLE_TRESHOLD_DEGREES; // End the command when we are within the specified threshold of being 'flat' (gyroscope pitch of 0 degrees)
  }
}