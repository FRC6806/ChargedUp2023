package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Arm;
public class AutoScore extends CommandBase {    
    private Swerve s_Swerve; 
    private Arm s_arm;   
    private Elevator s_Elevator;
    private Intake s_Intake;
    private Arm s_Arm;
    private int dist;
    public AutoScore(Swerve s_Swerve, Arm s_Arm,Intake s_Intake,Elevator s_Elevator) {
        
        this.s_Swerve = s_Swerve;
        this.s_Arm = s_Arm;
        this.s_Elevator = s_Elevator;
        this.s_Intake = s_Intake;
        dist = s_Elevator.getdist();

        addRequirements(s_Swerve);
        addRequirements(s_Arm);
        addRequirements(s_Elevator);
        addRequirements(s_Intake);
        

      
    }

public void intitialize(){
   
   //s_Elevator.moveup(dist);
}

public void end(boolean interupted){
 s_Intake.stop();
}

public boolean isFinished(){
    return false;
    
}

    @Override
    public void execute() {
        s_Arm.up();
        s_Intake.spin(.4);         
        
        
    }
}
 



