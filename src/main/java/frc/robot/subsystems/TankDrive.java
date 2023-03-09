package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class TankDrive {
    private TalonFX frontLeft;
    private TalonFX frontRight;
    private TalonFX backLeft;
    private TalonFX backRight;

    public TankDrive(int flid, int frid, int blid, int brid){
        frontLeft = new TalonFX(flid);
        frontRight = new TalonFX(frid);
        backLeft = new TalonFX(blid);
        backRight = new TalonFX(brid);
    }

    public void drive( double left, double right){
        frontLeft.set(ControlMode.Velocity, left);
        frontRight.set(ControlMode.Velocity, right);
        backLeft.set(ControlMode.Velocity, left);
        backRight.set(ControlMode.Velocity, right);
    }

    public void stop(){
        frontLeft.set(ControlMode.Velocity, 0);
        frontRight.set(ControlMode.Velocity, 0);
        backLeft.set(ControlMode.Velocity, 0);
        backRight.set(ControlMode.Velocity, 0);

    }


}