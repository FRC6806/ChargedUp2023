package frc.robot.autos;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.Balance;
import frc.robot.commands.BalanceOnBeamCommand;
import frc.robot.commands.BalanceOnBeamCommand2;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveTilt;
import frc.robot.autos.AutoHighCube;


public class AutoHighCubeCenter extends SequentialCommandGroup {    

    private Arm s_arm;   
    private Intake s_Intake;
    private Elevator s_Elevator;
    private Swerve s_swerve;

    public AutoHighCubeCenter(Swerve s_swerve,Arm s_arm, Intake s_Intake, Elevator s_Elevator, double speed, int position) {
        
        this.s_swerve = s_swerve;
        this.s_Intake = s_Intake;
        this.s_arm = s_arm;
        this.s_Elevator = s_Elevator;

        addRequirements(s_arm);
        addRequirements(s_Intake);
        addRequirements(s_Elevator);
        addRequirements(s_swerve);

        addCommands(
            new AutoHighCube(s_swerve, s_arm, s_Intake, s_Elevator, speed, position),
            new DriveDistance(8.3, s_swerve, -.2)
            // new DriveTilt(s_swerve),
            // new BalanceOnBeamCommand(s_swerve)
           // new InstantCommand(() -> new DriveDistance(-16, s_swerve, .2))
        );
        
    }

}
 



