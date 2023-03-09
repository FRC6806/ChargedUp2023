package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private CANSparkMax intakeMotor;
    private DoubleSolenoid intakeSlant;
    private PneumaticHub hub = new PneumaticHub(17);

    public Intake(int intakeMotorCAN, int port_2,int port_3){
        intakeMotor = new CANSparkMax(intakeMotorCAN, MotorType.kBrushless);
        intakeMotor.setIdleMode(IdleMode.kBrake);
        intakeSlant = new DoubleSolenoid(17, PneumaticsModuleType.REVPH,port_2, port_3);
    }

    public void spin(double speed){
        intakeMotor.set(speed);
    }

    public void stop(){
        intakeMotor.set(0);
    }
    public void up(){
        intakeSlant.set(DoubleSolenoid.Value.kReverse);     
    }
    public void off(){
        intakeSlant.set(DoubleSolenoid.Value.kOff);    
    }
    public void down(){
        intakeSlant.set(DoubleSolenoid.Value.kForward); 
    }

    @Override
    public void periodic(){
        
    }
} 