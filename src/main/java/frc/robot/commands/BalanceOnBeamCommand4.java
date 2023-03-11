// Author: UMN Robotics Ri3d
// Last Updated : January 2023

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

// This command self=balances on the charging station using gyroscope pitch as feedback
public class BalanceOnBeamCommand4 extends CommandBase {

  private Swerve s_Swerve;

  private double error;
  private double currentAngle;
  private double drivePower;

  /** Command to use Gyro data to resist the tip angle from the beam - to stabalize and balanace */
  public BalanceOnBeamCommand4(Swerve s_Swerve) {
    this.s_Swerve = s_Swerve;
    addRequirements(s_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    s_Swerve.SetModeBrake();
  }

  // Called every time the schsceduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.currentAngle = s_Swerve.getPitch();

    SmartDashboard.putNumber("Angle", currentAngle);

    drivePower = Math.pow(currentAngle, 0.25) * currentAngle;

    if (currentAngle > 0.5){
      s_Swerve.drive(new Translation2d(drivePower, 0).times(Constants.Swerve.maxSpeed),
      0 * Constants.Swerve.maxAngularVelocity, 
      true,//robotCentricSup.getAsBoolean(), 
      true );
    } else if (currentAngle < -0.5){
      s_Swerve.drive(new Translation2d(drivePower, 0).times(Constants.Swerve.maxSpeed),
      0 * Constants.Swerve.maxAngularVelocity, 
      true,//robotCentricSup.getAsBoolean(), 
      true );
    }
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
    s_Swerve.SetModeCost();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(error) < Constants.BEAM_BALANCED_ANGLE_TRESHOLD_DEGREES; // End the command when we are within the specified threshold of being 'flat' (gyroscope pitch of 0 degrees)
  }
}