package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class AutoIntake1 extends SequentialCommandGroup {    

    private Arm s_arm;   
    private Intake s_Intake;
    private Elevator s_Elevator;
    
    public AutoIntake1(Arm s_arm,Intake s_Intake, Elevator s_Elevator) {
        
        this.s_Intake = s_Intake;
        this.s_arm = s_arm;
        this.s_Elevator = s_Elevator;


        addRequirements(s_arm);
        addRequirements(s_Intake);
        addRequirements(s_Elevator);
    
   
        addCommands(
            new InstantCommand(() -> s_arm.up()),
            new WaitCommand(0.5),
            new InstantCommand(() -> s_Elevator.moveToPosition(43000)),
            new WaitCommand(2), 
            new InstantCommand(() -> s_Intake.spin(-.4))
        );
    }

}