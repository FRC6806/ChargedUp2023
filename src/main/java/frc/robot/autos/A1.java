package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class A1 extends SequentialCommandGroup {
    Command[] cmds;
    public A1(Command c1, Command c2){
        cmds = new Command[2];
        cmds[0] = c1;
        cmds[1] = c2;
        // cmds[2] = c3;
        // cmds[3] = c4;
        // cmds[4] = c5;
        
        

        addCommands(
            new SequentialCommandGroup(cmds)
        );
    }
}