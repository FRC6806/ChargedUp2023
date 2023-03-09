// package frc.robot.subsystems;

// import frc.robot.SwerveModule;
// import frc.robot.Constants;

// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
// import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
// import edu.wpi.first.math.kinematics.SwerveModulePosition;

// import com.ctre.phoenix.sensors.Pigeon2;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.kinematics.SwerveModuleState;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class Swerve extends SubsystemBase {
//     public SwerveDriveOdometry swerveOdometry;
//     public SwerveModule[] mSwerveMods;
//     //public AHRS gyro;S
//     public Pigeon2 gyro;

//     public Swerve() {
        
//         gyro = new Pigeon2( Constants.Swerve.pigeonID, "Canivore1");
//         gyro.configFactoryDefault();
//         //The following two lines are for the navX
//         //gyro = new AHRS(SPI.Port.kMXP);
//         //gyro.reset();
//         zeroGyro();

//         mSwerveMods = new SwerveModule[] {
//             new SwerveModule(0, Constants.Swerve.Mod0.constants),
//             new SwerveModule(1, Constants.Swerve.Mod1.constants),
//             new SwerveModule(2, Constants.Swerve.Mod2.constants),
//             new SwerveModule(3, Constants.Swerve.Mod3.constants)
//         };

//         /* By pausing init for a second before setting module offsets, we avoid a bug with inverting motors.
//          * See https://github.com/Team364/BaseFalconSwerve/issues/8 for more info.
//          */
//         Timer.delay(1.0);
//         resetModulesToAbsolute();

//         swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
//     }

//     public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
//         SwerveModuleState[] swerveModuleStates =
//             Constants.Swerve.swerveKinematics.toSwerveModuleStates(
//                 fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
//                                     translation.getX(), 
//                                     translation.getY(), 
//                                     rotation, 
//                                     getYaw()
//                                 )
//                                 : new ChassisSpeeds(
//                                     translation.getX(), 
//                                     translation.getY(), 
//                                     rotation)
//                                 );
//         SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

//         for(SwerveModule mod : mSwerveMods){
//             mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
//         }
//     }    

//     /* Used by SwerveControllerCommand in Auto */
//     public void setModuleStates(SwerveModuleState[] desiredStates) {
//         SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
//         for(SwerveModule mod : mSwerveMods){
//             mod.setDesiredState(desiredStates[mod.moduleNumber], false);
//         }
//     }    

//     public Pose2d getPose() {
//         return swerveOdometry.getPoseMeters();
//     }

//     public void resetOdometry(Pose2d pose) {
//         swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
//     }

//     public SwerveModuleState[] getModuleStates(){
//         SwerveModuleState[] states = new SwerveModuleState[4];
//         for(SwerveModule mod : mSwerveMods){
//             states[mod.moduleNumber] = mod.getState();
//         }
//         return states;
//     }

//     public SwerveModulePosition[] getModulePositions(){
//         SwerveModulePosition[] positions = new SwerveModulePosition[4];
//         for(SwerveModule mod : mSwerveMods){
//             positions[mod.moduleNumber] = mod.getPosition();
//         }
//         return positions;
//     }

//     public void zeroGyro(){
//         gyro.setYaw(0);
//         //This is for the navx
//         //gyro.setAngleAdjustment(0);
//     }

//     public Rotation2d getYaw() {
//         return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(gyro.getYaw()%360) : Rotation2d.fromDegrees(gyro.getYaw()%360);
//     }

//     public void resetModulesToAbsolute(){
//         for(SwerveModule mod : mSwerveMods){
//             mod.resetToAbsolute();
//         }
//     }

//     public double getPitch(){
//         return gyro.getPitch();
//     }


//     @Override
//     public void periodic(){
//         swerveOdometry.update(getYaw(), getModulePositions());  
//         SmartDashboard.putNumber("Heading",getYaw().getDegrees());
//         for(SwerveModule mod : mSwerveMods){
//             SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
//             SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
//             SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond); 

//         }
//     }
// } 
package frc.robot.subsystems;

import frc.robot.SwerveModule;
import frc.robot.Constants;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public Pigeon2 gyro;

    public Swerve() {
        

        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };
        gyro = new Pigeon2(Constants.Swerve.pigeonID);
        
        gyro.configFactoryDefault();
        zeroGyro();

        /* By pausing init for a second before setting module offsets, we avoid a bug with inverting motors.
         * See https://github.com/Team364/BaseFalconSwerve/issues/8 for more info.
         */
        Timer.delay(1.0);
        resetModulesToAbsolute();

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
    }
    public void SetModeBrake(){
        mSwerveMods[0].SetBrake();
        mSwerveMods[1].SetBrake();
        mSwerveMods[2].SetBrake();
        mSwerveMods[3].SetBrake();

                        
    }
    public void SetModeCost(){
        mSwerveMods[0].SetCost();
        mSwerveMods[1].SetCost();
        mSwerveMods[2].SetCost();
        mSwerveMods[3].SetCost();

    }
    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    translation.getX(), 
                                    translation.getY(), 
                                    rotation)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }    

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    
    public double getPitch(){
        return gyro.getPitch();
    }
    public void testpitch(){
        SmartDashboard.putNumber("pitch", getPitch());
    }
    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void zeroGyro(){
        gyro.setYaw(0);
    }

    public Rotation2d getYaw() {
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
    }
    

    @Override
    public void periodic(){
        swerveOdometry.update(getYaw(), getModulePositions());  

        for(SwerveModule mod : mSwerveMods){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getPosition().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);  
              
        }
    }


    public void driveDistance( double feetDistance, double speed){

        double initalstate = Math.abs((mSwerveMods[0].getPosition().distanceMeters + mSwerveMods[1].getPosition().distanceMeters + mSwerveMods[2].getPosition().distanceMeters + mSwerveMods[3].getPosition().distanceMeters)/4);
        double currentstate = Math.abs((mSwerveMods[0].getPosition().distanceMeters + mSwerveMods[1].getPosition().distanceMeters + mSwerveMods[2].getPosition().distanceMeters + mSwerveMods[3].getPosition().distanceMeters)/4.0);          
        double desiredState = feetDistance*8.889;
        while( desiredState >= Math.abs(currentstate-initalstate) ){
            currentstate = Math.abs((mSwerveMods[0].getPosition().distanceMeters + mSwerveMods[1].getPosition().distanceMeters + mSwerveMods[2].getPosition().distanceMeters + mSwerveMods[3].getPosition().distanceMeters)/4.0);          
            drive(
                new Translation2d(speed, 0).times(Constants.Swerve.maxSpeed), 
                0 * Constants.Swerve.maxAngularVelocity, 
                false,//robotCentricSup.getAsBoolean(), 
                true
            );  


        }
        drive(
            new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
            0 * Constants.Swerve.maxAngularVelocity, 
            false,//robotCentricSup.getAsBoolean(), 
            true
        );  



    }





}