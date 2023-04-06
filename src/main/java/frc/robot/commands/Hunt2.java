package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Vision;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Hunt2 extends CommandBase {
    private Swerve s_Swerve;    
    private Vision s_Vision;

    public Hunt2(Swerve s_Swerve, Vision s_Vision) {
        this.s_Vision = s_Vision;
        this.s_Swerve = s_Swerve;
        addRequirements(s_Vision);
        addRequirements(s_Swerve);
      
    }

public void intitialize(){
}

public void end(boolean interupted){
    s_Swerve.drive(
            new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            true,//robotCentricSup.getAsBoolean(), 
            true
        );
}

// public boolean isFinished(){
//     // if (s_Vision.getPitch() > 13.5 && s_Vision.getYaw() < 1.4){
//     //     return true;
//     // }
//     // else {
//     //     return false;
//     // }
    
// }

    @Override
    public void execute() {
        final double ANGULAR_P = 0.02;
        final double ANGULAR_D = 0.0;
        PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

        double forwardSpeed = 0;
        double rotationSpeed;

        var result = s_Vision.getResult();

        if (result.hasTargets()) {
            // Calculate angular turn power
            // -1.0 required to ensure positive PID controller effort _increases_ yaw
            rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0);
            SmartDashboard.putNumber("rotation speed", rotationSpeed);
        } else {
            // If we have no targets, stay still.
            rotationSpeed = 0;
        }
        s_Swerve.drive(new Translation2d(forwardSpeed, 0), rotationSpeed, true, true);
    }
}