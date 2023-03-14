package frc.robot.autos;

import frc.robot.commands.DriveDistance;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;

public class AutoNavigate extends SequentialCommandGroup {    

    private Arm s_arm;   
    private Intake s_Intake;
    private Elevator s_Elevator;
    private Swerve s_Swerve;
    
    public AutoNavigate(Arm s_arm, Intake s_Intake, Elevator s_Elevator, Swerve s_Swerve, double speed, int position) {
        
        this.s_Intake = s_Intake;
        this.s_arm = s_arm;
        this.s_Elevator = s_Elevator;
        this.s_Swerve = s_Swerve;

        addRequirements(s_arm);
        addRequirements(s_Intake);
        addRequirements(s_Elevator);
        addRequirements(s_Swerve);
    
   
        addCommands(
            new DriveDistance(15, s_Swerve, 0.5),
            new WaitCommand(2),
            new DriveDistance(-15, s_Swerve, 0.5)
        );
    }

}
 



