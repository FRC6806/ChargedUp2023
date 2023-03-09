package frc.robot.commands;

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

public class CubeOut extends SequentialCommandGroup {    

    private Arm s_arm;   
    private Intake s_Intake;
    
    public CubeOut(Arm s_arm,Intake s_Intake) {
        
        this.s_Intake = s_Intake;
        this.s_arm = s_arm;


        addRequirements(s_arm);
        addRequirements(s_Intake);
    
   
        addCommands(
            new WaitCommand(5),
            new InstantCommand(() -> s_Intake.up()),
            new WaitCommand(2),
            new InstantCommand(() ->s_Intake.spin(.4)),
            new WaitCommand(2),
            new InstantCommand(() -> s_Intake.stop())

            );
    }

}
 



