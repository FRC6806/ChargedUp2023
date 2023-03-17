package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.autos.*;
import frc.robot.autos.AutoScore;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
    private final JoystickButton driverButton5 = new JoystickButton(driverJoystick, 5);

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
    private final AutoIntake1 s_AutoIntake1 = new AutoIntake1(s_Arm, s_Intake, s_Elevator);
    private final AutoIntake2 s_AutoIntake2 = new AutoIntake2(s_Arm, s_Intake, s_Elevator);


    private double intakeSpeed = 0;
    private int setPosition = 0;
    // private final Hunt s_Hunt = new Hunt(s_Swerve,s_Vision);
    private POVButton operatorDpadUp = new POVButton(operatorController, 0);


    // Autos

    private final Command m_midAuto = new AutoMidCube(s_Swerve, s_Arm, s_Intake, s_Elevator, .5,  25000); 
    private final Command m_highAuto = new AutoHighCube(s_Swerve, s_Arm, s_Intake, s_Elevator, .5,  41000); 
    // private final Command m_driveDistance = new DriveDistance(5, s_Swerve, .1);
    SendableChooser<Command> m_Chooser = new SendableChooser<>(); 

    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        
        // Configure the button bindings
        configureButtonBindings();
        
         
        
        s_Elevator.setDefaultCommand(new TeleopElevator(s_Elevator, () -> -0.25 * operatorController.getRawAxis(1)));

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                1, 
                s_Swerve, 
                () -> driverJoystick.getRawAxis(translationAxis), 
                () -> driverJoystick.getRawAxis(strafeAxis), 
                () -> driverJoystick.getRawAxis(rotationAxis), 
                () -> driverButton5.getAsBoolean(),
                () -> driverJoystick.getRawAxis(slideraxis)     
            )
        );

        s_Vision.setDefaultCommand( new InstantCommand(() -> s_Vision.start(), s_Vision) );

        s_Elevator.stop();

        m_Chooser.setDefaultOption("Mid Cube Auto", m_midAuto);
        m_Chooser.addOption("High Cube Auto", m_highAuto);
        SmartDashboard.putData(m_Chooser);

    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        SendableChooser<String> Mode = new SendableChooser<String>();
        Mode.addOption("High", "H");
        Mode.addOption("Med", "M");
        Mode.addOption("Low", "L");
        SmartDashboard.putData("Lift Mode", Mode);


        /* Driver Buttons */
        JoystickButton driverTrigger = new JoystickButton(driverJoystick, 1);
        JoystickButton driverButton2 = new JoystickButton(driverJoystick, 2);
        JoystickButton driverButton3 = new JoystickButton(driverJoystick, 3);
        JoystickButton driverButton4 = new JoystickButton(driverJoystick, 4);
       // JoystickButton driverButton5 = new JoystickButton(driverJoystick, 5);
        JoystickButton driverButton6 = new JoystickButton(driverJoystick, 6);
        JoystickButton driverButton7 = new JoystickButton(driverJoystick, 7);
        JoystickButton driverButton8 = new JoystickButton(driverJoystick, 8);
        JoystickButton driverButton9 = new JoystickButton(driverJoystick, 9);

    
        /* Operator Buttons */
        JoystickButton operatorControllerLeftBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
        JoystickButton operatorControllerRightBumper = new JoystickButton(operatorController, XboxController.Button.kLeftBumper.value);
        JoystickButton operatorControllerLStick = new JoystickButton(operatorController, XboxController.Button.kLeftStick.value);
        JoystickButton operatorControllerRStick= new JoystickButton(operatorController, XboxController.Button.kRightStick.value);
        JoystickButton operatorControllerX = new JoystickButton(operatorController, XboxController.Button.kX.value);
        JoystickButton operatorControllerY = new JoystickButton(operatorController, XboxController.Button.kY.value);
        JoystickButton operatorControllerA = new JoystickButton(operatorController, XboxController.Button.kA.value);
        JoystickButton operatorControllerB = new JoystickButton(operatorController, XboxController.Button.kB.value);
        JoystickButton operatorStart = new JoystickButton(operatorController, XboxController.Button.kStart.value);
        JoystickButton operatorBack = new JoystickButton(operatorController, XboxController.Button.kBack.value);
        POVButton operatorDpadUp = new POVButton(operatorController, 0);
        POVButton operatorDpadDown = new POVButton(operatorController, 180);
        POVButton operatorDpadRight = new POVButton(operatorController, 90);
        
        /* Button Bindings */       
        driverButton3.onTrue(new Hunt(s_Swerve , s_Vision));
        driverButton4.onTrue( new BalanceOnBeamCommand(s_Swerve));
        driverTrigger.onTrue(new AutoScore( s_Arm, s_Intake, s_Elevator,43000)); 
        driverButton6.onTrue(new InstantCommand(() -> s_Elevator.moveToPosition(0)));
        driverTrigger.onTrue(new AutoIntake1(s_Arm, s_Intake, s_Elevator));
        driverTrigger.toggleOnFalse(new AutoIntake2(s_Arm, s_Intake, s_Elevator));















        
        // driverButton8.onTrue(new InstantCommand(() -> s_Elevator.moveToPosition(45000)));
        // driverButton7.onTrue(new InstantCommand(() -> s_Elevator.moveToPosition(0)));
        driverButton9.onTrue(new DriveDistance(-4, s_Swerve, 0.2));

        operatorControllerX.onTrue(new InstantCommand(() -> s_Intake.spin((.5))));
        operatorControllerX.onFalse(new InstantCommand(() -> s_Intake.stop()));
        operatorControllerB.onTrue(new InstantCommand(() -> s_Intake.spin((-.5))));
        operatorControllerB.onFalse(new InstantCommand(() -> s_Intake.stop()));

        // operatorControllerA.onTrue(new InstantCommand(() -> s_Intake.setSpeed(Constants.CUBE_SPEED)));
        operatorControllerY.onTrue(new InstantCommand(()-> s_Intake.setSpeed(Constants.CONE_SPEED)));

        //operatorControllerA.onTrue(new InstantCommand(() -> s_Elevator.setPosition(Constants.HIGH_ELEVATOR_VALUE)));
        //operatorDpadUp.onTrue(new InstantCommand(()-> s_Elevator.moveToPosition(Constants.HIGH_ELEVATOR_VALUE)));
        operatorDpadUp.onTrue(new AutoScore(s_Arm, s_Intake, s_Elevator,Constants.HIGH_ELEVATOR_VALUE));
        //operatorDpadDown.onTrue(new InstantCommand(() -> s_Elevator.moveToPosition(Constants.LOW_ELEVATOR_VALUE)));
        operatorDpadDown.onTrue(new AutoScore(s_Arm, s_Intake, s_Elevator,Constants.LOW_ELEVATOR_VALUE));
        operatorDpadRight.onTrue(new AutoScore(s_Arm, s_Intake, s_Elevator,Constants.MID_ELEVATOR_VALUE));
        
        operatorControllerLStick.onTrue(new InstantCommand(() -> s_Arm.down()));
        operatorControllerRStick.onTrue(new InstantCommand(() -> s_Arm.up()));

        //driverThumbButton.onTrue();
        operatorBack.onTrue(new InstantCommand(() -> s_Intake.up()));
        operatorStart.onTrue(new InstantCommand(() -> s_Intake.down()));

        new InstantCommand(() ->s_Vision.start());
        // driverTrigger.onTrue( new InstantCommand(() ->s_Swerve.testpitch()));
        // driverButton9.onTrue( new InstantCommand(() -> lights.mode()));
        // driverButton9.onTrue( new InstantCommand(()-> elev.periodic()));
        //driverButton7.onTrue( new InstantCommand(() -> s_Swerve.resetModulesToAbsolute()));
        

        
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run +in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
       // return new DriveDistance(5, s_Swerve, .1);//A1(s_DriveTilt,s_BalanceOnBeamCommand);//,s_OffBalance,s_Backtilt,s_BalanceOnBeamCommand2);
        //  return new DriveDistance(-14, s_Swerve, .2); 
        return new AutoMidCube(s_Swerve,s_Arm, s_Intake, s_Elevator, .5,  25000); // 25000
        
        //return new BalanceOnBeamCommand(s_Swerve);
        //return new DriveTilt(s_Swerve);
        //return m_Chooser.getSelected();
    }

    
    public void Tilt(){
        SmartDashboard.getNumber("tilt robot", s_Swerve.getPitch());
    }

    public boolean getDpadUp(){
        return operatorDpadUp.getAsBoolean();
    }
}