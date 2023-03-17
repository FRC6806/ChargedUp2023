package frc.robot.autos;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.DriveDistance;

public class AutoMidCube extends SequentialCommandGroup {    

    private Arm s_arm;   
    private Intake s_Intake;
    private Elevator s_Elevator;
    private Swerve s_swerve;
    
    public AutoMidCube(Swerve s_swerve,Arm s_arm, Intake s_Intake, Elevator s_Elevator, double speed, int position) {
        
        this.s_swerve = s_swerve;
        this.s_Intake = s_Intake;
        this.s_arm = s_arm;
        this.s_Elevator = s_Elevator;


        addRequirements(s_arm);
        addRequirements(s_Intake);
        addRequirements(s_Elevator);
    
   
        addCommands(
            new InstantCommand(() -> s_arm.up()),
            new WaitCommand(1),
            new InstantCommand(() -> s_Elevator.moveToPosition(position)),
            new WaitCommand(7),
            new InstantCommand(() -> s_Intake.spin(speed)),
            new WaitCommand(.2),
            new InstantCommand(() -> s_Intake.up()),
            new WaitCommand(1),
            new InstantCommand(() -> s_Intake.stop()),
            new WaitCommand(1),
            new InstantCommand(() -> s_Elevator.moveToPosition(0)),
            new WaitCommand(3),
            new InstantCommand(() -> s_arm.down())
           // new InstantCommand(() -> new DriveDistance(-16, s_swerve, .2))
        );
    }

}
 



