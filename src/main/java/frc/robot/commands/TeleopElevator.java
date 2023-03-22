package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopElevator extends CommandBase {    
    private Elevator s_Elevator;    
    private DoubleSupplier joystickValue;


    public TeleopElevator(Elevator s_Elevator, DoubleSupplier joystickValue) {
        this.s_Elevator = s_Elevator;
        addRequirements(s_Elevator);
        this.joystickValue = joystickValue;
    }


    @Override
    public void execute() {
        /* Drive */
        if (s_Elevator.getEncoder() < 42000){
            s_Elevator.manualMove( joystickValue);
        }else{
            s_Elevator.setZero();
        }
    }
}