
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase {
    private AddressableLED light;
    private AddressableLEDBuffer ledBuffer;
    private int mode = 1;
    private int x = 0;

    public Lights(int pwmPort) {
        light = new AddressableLED(pwmPort);
        ledBuffer = new AddressableLEDBuffer(150);
        light.setLength(ledBuffer.getLength());
        light.setData(ledBuffer);
        light.start();

    }

    public void mode() {
        mode++;
        if (mode > 2) {
            mode = 1;
        }
        periodic();
    }

    private void colors(int c) {

        for (int i = 0; i < ledBuffer.getLength(); i++) {
            /*
             * if(mode == 1){
             * ledBuffer.setHSV(i, 0, 0, 140);
             * }
             * else
             */if (mode == 1 && DriverStation.getAlliance() == Alliance.Red) {
                ledBuffer.setHSV(i, 1, 255, 140);
            } else if (mode == 1 && DriverStation.getAlliance() == Alliance.Blue) {
                ledBuffer.setHSV(i, 120, 255, 140);
            }
            /*
             * else if(mode == 2 && DriverStation.getAlliance() == Alliance.Red ){
             * ledBuffer.setHSV(i, 0, 0, c);
             * }
             */
            else if (mode == 2 && DriverStation.getAlliance() == Alliance.Red) {
                ledBuffer.setHSV(i, 1, c, 140);
            } else if (mode == 2 && DriverStation.getAlliance() == Alliance.Blue) {
                ledBuffer.setHSV(i, 120, c, 140);
            }

        }

    }

    public void trail() {

        /*
         * for(int i = 1; i < ledBuffer.getLength(); i++){
         * ledBuffer.setLED(i, Color.kRed);
         * ledBuffer.setLED(i-1, Color.kBlue);
         * if(i == 149){
         * i = 1;
         * }
         * }
         */
    }

    @Override
    public void periodic() {
        
        x += 5;
        x %= 255;
        colors(x);
        light.setData(ledBuffer);


    }
}