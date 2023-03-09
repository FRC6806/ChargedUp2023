
package frc.robot.subsystems;

 
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Arm extends SubsystemBase {
    private DoubleSolenoid elevatorSlant;
    private PneumaticHub hub = new PneumaticHub(17);

    public Arm(int port_0,int port_1){
        elevatorSlant = new DoubleSolenoid(17,PneumaticsModuleType.REVPH,port_0,port_1);

        hub.enableCompressorAnalog(100, 120);
        
    }

    public Value get(){
        return elevatorSlant.get();
    }
        
    public void up(){
        elevatorSlant.set(DoubleSolenoid.Value.kForward);    
        
    }

    public void off(){
        elevatorSlant.set(DoubleSolenoid.Value.kOff); 
            
    }

    public void down(){
        elevatorSlant.set(DoubleSolenoid.Value.kReverse); 
    }
 
    public void toggle(){
        elevatorSlant.toggle();
    }
    public void compressoff(){
        hub.disableCompressor();
    }
    
    

    @Override
    public void periodic(){
        
    }
} 