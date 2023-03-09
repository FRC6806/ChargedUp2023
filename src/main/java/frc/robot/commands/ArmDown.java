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
public class ArmDown extends CommandBase {    

    private Arm s_Arm;

    public ArmDown( Arm s_Arm) {
        
        this.s_Arm = s_Arm;
        
        addRequirements(s_Arm);
        
    }

public void intitialize(){
   
   
}

public void end(boolean interupted){

}

public boolean isFinished(){
    return false;
    
}

    @Override
    public void execute() {
        s_Arm.down();        
        
    }
}
 