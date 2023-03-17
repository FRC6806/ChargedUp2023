package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    SendableChooser<String> Mode = new SendableChooser<String>();
    private PWMTalonFX intakeMotor;
    
    
    
    
    
    private DoubleSolenoid intakeSlant;
    private PneumaticHub hub = new PneumaticHub(17);
    private double speed; 

    public Intake(int intakeMotorCAN, int port_2,int port_3){
        intakeMotor = new PWMTalonFX(1);

        //intakeMotor.setIdleMode(IdleMode.kBrake);
        intakeSlant = new DoubleSolenoid(17, PneumaticsModuleType.REVPH,port_2, port_3);
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getSpeed(){
        return speed; 
    }

    public void spin(double speed){
        intakeMotor.set(speed);
    }

    public void outtake(){
        intakeMotor.set(speed);
    }

    public void intake(){
        intakeMotor.set(-speed);
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

    public void periodic(){
        Mode.addOption("Cone", "Co");
        Mode.addOption("Cube", "Cu");
        
        SmartDashboard.putData("Cone/Cube", Mode);
        SmartDashboard.putNumber("Intake Speed", getSpeed());
    }

    public void setElement(){
        String element = Mode.getSelected();

        if (element.equals("Cube")){
            setSpeed(Constants.CUBE_SPEED);
        } else if (element.equals("Cone")){
            setSpeed(Constants.CONE_SPEED);
        } 

    }


} 