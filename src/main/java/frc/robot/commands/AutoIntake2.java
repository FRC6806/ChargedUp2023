package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class AutoIntake2 extends SequentialCommandGroup {    

    private Arm s_Arm;   
    private Intake s_Intake;
    private Elevator s_Elevator;
    
    public AutoIntake2(Arm s_Arm,Intake s_Intake, Elevator s_Elevator) {
        
        this.s_Intake = s_Intake;
        this.s_Arm = s_Arm;
        this.s_Elevator = s_Elevator;


        addRequirements(s_Arm);
        addRequirements(s_Intake);
        addRequirements(s_Elevator);
    
   
        addCommands(
            new InstantCommand(() -> s_Intake.stop()),
            new InstantCommand(() -> s_Elevator.moveToPosition(0)),
            new WaitCommand(4),
            new InstantCommand(() -> s_Arm.down())
        );
    }

}
