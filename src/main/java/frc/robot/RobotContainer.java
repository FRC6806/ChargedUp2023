package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick drivera = new Joystick(1);

    /* Drive Controls */
    // private final int translationAxis = XboxController.Axis.kLeftY.value;
    // private final int strafeAxis = XboxController.Axis.kLeftX.value;
    // private final int rotationAxis = XboxController.Axis.kRightX.value;
    private final int translationAxis = 1;
    private final int strafeAxis = 0;
    private final int rotationAxis = 2;
    private final int slideraxis = 3;


    /* Driver Buttons */
    private final JoystickButton driveraLeftBumper = new JoystickButton(drivera, XboxController.Button.kLeftBumper.value);
    private final JoystickButton driveraRightBumper = new JoystickButton(drivera, XboxController.Button.kLeftBumper.value);
    private final JoystickButton driveraLStick = new JoystickButton(drivera, XboxController.Button.kLeftStick.value);
    private final JoystickButton driveraRStick= new JoystickButton(drivera, XboxController.Button.kRightStick.value);
    private final JoystickButton driveraX = new JoystickButton(drivera, XboxController.Button.kX.value);
    private final JoystickButton driveraY = new JoystickButton(drivera, XboxController.Button.kY.value);
    private final JoystickButton driveraA = new JoystickButton(drivera, XboxController.Button.kA.value);
    private final JoystickButton driveraB = new JoystickButton(drivera, XboxController.Button.kB.value);
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton driverX = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton driverB = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton driverA = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton driverY = new JoystickButton(driver, XboxController.Button.kY.value); 
    private final JoystickButton num8 = new JoystickButton(driver, 8);
    private final JoystickButton num7 = new JoystickButton(driver, 7);
    private final JoystickButton num9 = new JoystickButton(driver, 9);
    private final JoystickButton driverLeftBumper = new JoystickButton(driver, XboxController.Button.kLeftBumper.value); 
    private final JoystickButton driverRightBumper = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton driverstart = new JoystickButton(drivera, XboxController.Button.kStart.value);
    private final JoystickButton driverback = new JoystickButton(drivera, XboxController.Button.kBack.value);
    private final JoystickButton levelButton = new JoystickButton(driver, 1);
    private final Lights lights = new Lights(9);
    private final Swerve s_Swerve = new Swerve();
    private final Intake s_Intake = new Intake(Constants.intakeMotorCAN, Constants.armPort_2,Constants.armPort_3);
    private final Arm s_Arm = new Arm(Constants.armPort_0, Constants.armPort_1);
    private final Vision s_Vision = new Vision("OV5647");
    private final BalanceOnBeamCommand3 s_BalanceOnBeamCommand = new BalanceOnBeamCommand3(s_Swerve);
    private final DriveTilt s_DriveTilt = new DriveTilt(s_Swerve);
    private final Backtilt s_Backtilt = new Backtilt(s_Swerve);
    private final OffBalance s_OffBalance = new OffBalance(s_Swerve);
    private final BalanceOnBeamCommand2 s_BalanceOnBeamCommand2 = new BalanceOnBeamCommand2(s_Swerve);
    private final Elevator s_Elevator = new Elevator(24,25);
    //private final Hunt s_Hunt = new Hunt(s_Swerve,s_Vision);
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean(),
                () -> driver.getRawAxis(slideraxis)
                
            )
        );

        s_Vision.setDefaultCommand( new InstantCommand(() -> s_Vision.start(), s_Vision));

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        driverX.onTrue(new Hunt(s_Swerve,s_Vision));
        //driverB.onTrue(new InstantCommand(() -> s_Intake.stop()));
        //driverA.onTrue(new InstantCommand(() -> s_Intake.spin(-0.5)));
        driveraX.onTrue(new InstantCommand(() -> s_Intake.spin((0.5))));
        driveraX.onFalse(new InstantCommand(() -> s_Intake.stop()));
        driveraB.onTrue(new InstantCommand(() -> s_Intake.spin((-0.5))));
        driveraB.onFalse(new InstantCommand(() -> s_Intake.stop()));
        //driveraRightBumper.onFalse(new InstantCommand(() -> s_Intake.stop()));
        //driveraA.onTrue(new InstantCommand(() -> s_Arm.down()));
        //driveraY.onTrue(new InstantCommand(() -> s_Arm.up()));
        driveraA.onTrue(new InstantCommand(() -> s_Elevator.manualdown()));
        driveraA.onFalse(new InstantCommand(() -> s_Elevator.stop()));
        driveraY.onTrue(new InstantCommand(()-> s_Elevator.manualup()));
        driveraY.onFalse(new InstantCommand(() -> s_Elevator.stop()));

        driveraLStick.onTrue(new InstantCommand(() -> s_Arm.down()));
        driveraRStick.onTrue(new InstantCommand(() -> s_Arm.up()));

        levelButton.onTrue(new InchWorm(s_Swerve));
        //driverstart.onTrue(new InstantCommand(() -> s_Arm.compressoff()));
        //driverback.onTrue(new InstantCommand(() -> s_Arm.off()));
        driverback.onTrue(new InstantCommand(() -> s_Intake.up()));
        driverstart.onTrue(new InstantCommand(() -> s_Intake.down()));

        new InstantCommand(() ->s_Vision.start());
        driverB.onTrue( new InstantCommand(() ->s_Swerve.testpitch()));
        num9.onTrue( new InstantCommand(()-> lights.mode()));
        //num9.onTrue( new InstantCommand(()-> elev.periodic()));
        num7.onTrue( new InstantCommand(() -> s_Swerve.resetModulesToAbsolute()));
        

        
    }


    /**s
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        //return new DriveDistance(5, s_Swerve, .1);//A1(s_DriveTilt,s_BalanceOnBeamCommand);//,s_OffBalance,s_Backtilt,s_BalanceOnBeamCommand2);
        return new InstantCommand(() -> s_Swerve.driveDistance(1, .2));
    
    }
    public void Tilt(){
        SmartDashboard.putNumber("tilt robot", s_Swerve.getPitch());
    }
    }



