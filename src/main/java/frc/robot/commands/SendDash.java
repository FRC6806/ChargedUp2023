package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.BalanceOnBeamCommand;
import frc.robot.subsystems.Swerve;
public class SendDash extends CommandBase {

    public void intitialize() {

    }

    public void end(boolean interupted) {

    }

    public boolean isFinished() {
        return true;

    }

    @Override
    public void execute() {
        SmartDashboard.getBoolean("MedCone", false);
        
        
    }
}