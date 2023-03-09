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
    private final Joystick driverJoystick = new Joystick(0);
    private final Joystick operatorController = new Joystick(1);

    /* Axis */
    private final int translationAxis = 1;
    private final int strafeAxis = 0;
    private final int rotationAxis = 2;
    private final int slideraxis = 3;

    /* Substystems */
    private final Lights lights = new Lights(9);
    private final Swerve s_Swerve = new Swerve();
    private final Intake s_Intake = new Intake(Constants.intakeMotorCAN, Constants.armPort_2,Constants.armPort_3);
    private final Arm s_Arm = new Arm(Constants.armPort_0, Constants.armPort_1);
    private final Vision s_Vision = new Vision("OV5647");
    private final Elevator s_Elevator = new Elevator(24,25);
    
    /* Commands */
    private final BalanceOnBeamCommand3 s_BalanceOnBeamCommand = new BalanceOnBeamCommand3(s_Swerve);
    private final DriveTilt s_DriveTilt = new DriveTilt(s_Swerve);
    private final Backtilt s_Backtilt = new Backtilt(s_Swerve);
    private final OffBalance s_OffBalance = new OffBalance(s_Swerve);
    private final BalanceOnBeamCommand2 s_BalanceOnBeamCommand2 = new BalanceOnBeamCommand2(s_Swerve);
    //private final Hunt s_Hunt = new Hunt(s_Swerve,s_Vision);
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        
         // Configure the button bindings
        configureButtonBindings();

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driverJoystick.getRawAxis(translationAxis), 
                () -> -driverJoystick.getRawAxis(strafeAxis), 
                () -> driverJoystick.getRawAxis(rotationAxis), 
                () -> driverButton5.getAsBoolean(),
                () -> driverJoystick.getRawAxis(slideraxis)     
            )
        );

        s_Vision.setDefaultCommand( new InstantCommand(() -> s_Vision.start(), s_Vision));

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        
        /* Driver Buttons */
        private final JoystickButton driverThumbButton = new JoystickButton(driverJoystick, 1);
        private final JoystickButton driverTrigger = new JoystickButton(driverJoystick, 2);
        private final JoystickButton driverButton3 = new JoystickButton(driverJoystick, 3);
        private final JoystickButton driverButton4 = new JoystickButton(driverJoystick, 4);
        private final JoystickButton driverButton5 = new JoystickButton(driverJoystick, 5);
        private final JoystickButton driverButton6 = new JoystickButton(driverJoystick, 6);
        private final JoystickButton driverButton7 = new JoystickButton(driverJoystick, 7);
        private final JoystickButton driverButton8 = new JoystickButton(driverJoystick, 8);
        private final JoystickButton driverButton9 = new JoystickButton(driverJoystick, 9);

    
        /* Operator Buttons */
        private final JoystickButton operatorControllerLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
        private final JoystickButton operatorControllerRightBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
        private final JoystickButton operatorControllerLStick = new JoystickButton(operatorController, XboxController.Button.kLeftStick.value);
        private final JoystickButton operatorControllerRStick= new JoystickButton(operatorController, XboxController.Button.kRightStick.value);
        private final JoystickButton operatorControllerX = new JoystickButton(operatorController, XboxController.Button.kX.value);
        private final JoystickButton operatorControllerY = new JoystickButton(operatorController, XboxController.Button.kY.value);
        private final JoystickButton operatorControllerA = new JoystickButton(operatorController, XboxController.Button.kA.value);
        private final JoystickButton operatorControllerB = new JoystickButton(operatorController, XboxController.Button.kB.value);
        private final JoystickButton operatorStart = new JoystickButton(operatorController, XboxController.Button.kStart.value);
        private final JoystickButton operatorBack = new JoystickButton(operatorController, XboxController.Button.kBack.value);
    

        /* Button Bindings */

        driverButton4.onTrue(new InstantCommand(() -> s_Swerve.driverButton4()));
        driverButton3.onTrue(new Hunt(s_Swerve,s_Vision));
        //driverTrigger.onTrue(new InstantCommand(() -> s_Intake.stop()));
        //operatorController.onTrue(new InstantCommand(() -> s_Intake.spin(-0.5)));
        operatorControllerX.onTrue(new InstantCommand(() -> s_Intake.spin((0.5))));
        operatorControllerX.onFalse(new InstantCommand(() -> s_Intake.stop()));
        operatorControllerB.onTrue(new InstantCommand(() -> s_Intake.spin((-0.5))));
        operatorControllerB.onFalse(new InstantCommand(() -> s_Intake.stop()));
        //operatorControllerRightBumper.onFalse(new InstantCommand(() -> s_Intake.stop()));
        //operatorControllerA.onTrue(new InstantCommand(() -> s_Arm.down()));
        //operatorControllerY.onTrue(new InstantCommand(() -> s_Arm.up()));
        operatorControllerA.onTrue(new InstantCommand(() -> s_Elevator.manualdown()));
        operatorControllerA.onFalse(new InstantCommand(() -> s_Elevator.stop()));
        operatorControllerY.onTrue(new InstantCommand(()-> s_Elevator.manualup()));
        operatorControllerY.onFalse(new InstantCommand(() -> s_Elevator.stop()));

        operatorControllerLStick.onTrue(new InstantCommand(() -> s_Arm.down()));
        operatorControllerRStick.onTrue(new InstantCommand(() -> s_Arm.up()));

        driverThumbButton.onTrue(new InchWorm(s_Swerve));
        //operatorStart.onTrue(new InstantCommand(() -> s_Arm.compressoff()));
        //operatorBack.onTrue(new InstantCommand(() -> s_Arm.off()));
        operatorBack.onTrue(new InstantCommand(() -> s_Intake.up()));
        operatorStart.onTrue(new InstantCommand(() -> s_Intake.down()));

        new InstantCommand(() ->s_Vision.start());
        driverTrigger.onTrue( new InstantCommand(() ->s_Swerve.testpitch()));
        driverButton9.onTrue( new InstantCommand(()-> lights.mode()));
        //driverButton9.onTrue( new InstantCommand(()-> elev.periodic()));
        driverButton7.onTrue( new InstantCommand(() -> s_Swerve.resetModulesToAbsolute()));
        

        
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



