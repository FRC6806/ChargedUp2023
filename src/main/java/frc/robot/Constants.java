package frc.robot;

import javax.swing.plaf.basic.BasicComboBoxUI.FocusHandler;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import static edu.wpi.first.math.util.Units.degreesToRadians;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {
    public static final int speedConversion = 1;
    public static final double stickDeadband = 0.2;
    public static final int intakeMotorCAN = 15;

    public static final int armPort_0 = 0;
    public static final int armPort_1 = 1;
    public static final int armPort_2 = 2;
    public static final int armPort_3 = 3;

    /*Leveling Constants */
    public static final double BEAM_BALANCED_GOAL_DEGREES = 0;
    public static final double BEAM_BALANACED_DRIVE_KP =  0.6;
    public static final double BACKWARDS_BALANCING_EXTRA_POWER_MULTIPLIER = 1;
    public static final double BEAM_BALANCED_ANGLE_TRESHOLD_DEGREES = .5;

    

    public static final class Swerve {
        public static final int pigeonID = 13;
        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

       // public static final COTSFalconSwerveConstants chosenModule =  //TODO: This must be tuned to specific robot
         //   COTSFalconSwerveConstants.SDSMK4i(COTSFalconSwerveConstants.driveGearRatios.SDSMK4i_L2);

        



        
        /* Drivetrain Constants */
        public static final double trackWidth = Units.inchesToMeters(13.375); //TODO: This must be tuned to specific robot
        public static final double wheelBase = Units.inchesToMeters(25.5); //TODO: This must be tuned to specific robot
        public static final double wheelDiameter = Units.inchesToMeters(4);
        public static final double wheelCircumference = wheelDiameter *Math.PI;

        /* Swerve Kinematics 
         * No need to ever change this unless you are not doing a traditional rectangular/square 4 module swerve */
         public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0));

        /* Module Gear Ratios */
        public static final double driveGearRatio = 7.8/1;//1/7.8; // we changed this because nick is impatient
        public static final double angleGearRatio = 10.29; // and this

        /* Motor Inverts */
        public static final boolean angleMotorInvert = true;
        public static final boolean driveMotorInvert = false;

        /* Angle Encoder Invert */
        public static final boolean canCoderInvert = false;

        /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;

        /* These values are used by the drive falcon to ramp in open loop and closed loop driving.
         * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc */
        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        /* Angle Motor PID Values */
        public static final double angleKP = .6;
        public static final double angleKI = 0.0;
        public static final double angleKD = 12.0;
        public static final double angleKF = 0.0;

        /* Drive Motor PID Values */
        public static final double driveKP = 0.0013145; //TODO: This must be tuned to specific robot
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0;
        public static final double driveKF = 0.0;

        /* Drive Motor Characterization Values 
         * Divide SYSID values by 12 to convert from volts to percent output for CTRE */
        public static final double driveKS = (0.24801/12); //TODO: This must be tuned to specific robot
        public static final double driveKV = (0.014282 / 12);
        public static final double driveKA = (0.0013718 / 12);

        /* Swerve Profiling Values */
        /** Meters per Second */
        public static final double maxSpeed = .01; //TODO: This must be tuned to specific robot
        /** Radians per Second */
        public static final double maxAngularVelocity = .01; //TODO: This must be tuned to specific robot

        /* Neutral Modes */
        public static final NeutralMode angleNeutralMode = NeutralMode.Brake;
        public static final NeutralMode driveNeutralMode = NeutralMode.Brake;

        
        

        /* Module Specific Constants */
        /* Front Right Module - Module 0 */
        public static final class Mod3 { //TODO: This must be tuned to specific robot
            public static final int driveMotorID = 2;
            public static final int angleMotorID = 7;
            public static final int canCoderID = 12;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(88.9453125);//31.552734374999996 //42.890625);//30.146484375000004);//221.8359375
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Front Left Module - Module 1 */
        public static final class Mod1 { //TODO: This must be tuned to specific robot
            public static final int driveMotorID = 8;
            public static final int angleMotorID = 5;
            public static final int canCoderID = 11;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(81.826171875);//42.89062//31.46484375);//31.64062);//43.330078125);//218.583984375
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }
        
        /* Back Right Module - Module 2 */
        public static final class Mod2 { //TODO: This must be tuned to specific robot
            public static final int driveMotorID = 6;
            public static final int angleMotorID = 4;
            public static final int canCoderID = 10;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(141.85546875);//250.751953125//250.048828125);//200.56640);//15.556640625000002
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }

        /* Back Left Module - Module 3 */
        public static final class Mod0 { //TODO: This must be tuned to specific robot
            public static final int driveMotorID = 3;
            public static final int angleMotorID = 1;
            public static final int canCoderID = 9;
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(181.0546875);//199.16015625//16.347656);//206.54296875//253.38867);//67.939453125
            public static final SwerveModuleConstants constants = 
                new SwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, angleOffset);
        }
    }

    public static final class AutoConstants { //TODO: The below constants are used in the example auto, and must be tuned to specific robot
        public static final double kMaxSpeedMetersPerSecond = 1.5;
        public static final double kMaxAccelerationMetersPerSecondSquared = .5;
        public static final double kMaxAngularSpeedRadiansPerSecond = .05;
        public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;
    
        public static final double kPXController = 1;//1 for all
        public static final double kPYController = 1;
    public static final double kPThetaController = 1;
    
        /* Constraint for the motion profilied robot angle controller */
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
            new TrapezoidProfile.Constraints(
                kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
    }
	/**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon FX supports multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 30;

	/**
	 * Gains used in Motion Magic, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
    static final Gains kGains = new Gains(0.0, 0.0, 0.0, 0.2, 0, 1.0);
    
    /* Elevator Values */
    public static final int HIGH_ELEVATOR_VALUE = 43000; 
    public static final int MID_ELEVATOR_VALUE = 25000; 
    public static final int LOW_ELEVATOR_VALUE = 0; 
    public static final double CONE_SPEED = -0.4; 
    public static final double CUBE_SPEED = 0.4;
    /*Hunt Constants */
    public static final double CubeTagpitch = 13.5;
    public static final double CubeTagYaw = 3;
    public static final double intakeTagpitch = 0;
    public static final double intakeTagYaw = 0;

    public static class VisionConstants {

        /** Physical location of the apriltag camera on the robot, relative to the center of the robot. */
        public static final Transform3d APRILTAG_CAMERA_TO_ROBOT =
            new Transform3d(new Translation3d(-0.06, 0.250, -0.2127), new Rotation3d(0.0, degreesToRadians(15.0), 0.0));
    
        /** Physical location of the shooter camera on the robot, relative to the center of the robot. */
        public static final Transform3d LOW_LIMELIGHT_TO_ROBOT = new Transform3d(
            new Translation3d(-0.083, 0.254, -0.537),
            new Rotation3d(0.0, degreesToRadians(-9.8), degreesToRadians(-1.0)));
    
        public static final String LOW_LIMELIGHT_NAME = "limelight";
        
        /** Physical location of the high camera on the robot, relative to the center of the robot. */
        public static final Transform3d HIGH_LIMELIGHT_TO_ROBOT = new Transform3d(
            new Translation3d(-0.11, -0.015, -0.895),
            new Rotation3d(degreesToRadians(-90.0), degreesToRadians(34.6), 0.0));
    
        public static final String HIGH_LIMELIGHT_NAME = "limelight-high";
        
        public static final double FIELD_LENGTH_METERS = 16.54175;
        public static final double FIELD_WIDTH_METERS = 8.0137;
    
        // Pose on the opposite side of the field. Use with `relativeTo` to flip a pose to the opposite alliance
        public static final Pose2d FLIPPING_POSE = new Pose2d(
            new Translation2d(FIELD_LENGTH_METERS, FIELD_WIDTH_METERS),
            new Rotation2d(Math.PI));
    
        /** Minimum target ambiguity. Targets with higher ambiguity will be discarded */
        public static final double APRILTAG_AMBIGUITY_THRESHOLD = 0.2;


      }
    

}
