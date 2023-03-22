package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopSwerve extends CommandBase {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private DoubleSupplier speedMult;
    private SlewRateLimiter rampx; 
    private SlewRateLimiter rampy;
    private SlewRateLimiter rampz;

    public TeleopSwerve(double smoothx, double smoothy, double smoothz ,Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup, DoubleSupplier speedMult) {
        this.rampx = new SlewRateLimiter(smoothx);
        this.rampy = new SlewRateLimiter(smoothy);
        this.rampz = new SlewRateLimiter(smoothz);
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);
        this.speedMult= speedMult;
        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
    }


    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double speedMultVal = MathUtil.applyDeadband(speedMult.getAsDouble(), Constants.stickDeadband);
        speedMultVal += 1;
        speedMultVal /= 2;
        double translationVal = MathUtil.applyDeadband (speedMultVal*translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(speedMultVal*strafeSup.getAsDouble(), Constants.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(speedMultVal*rotationSup.getAsDouble(), Constants.stickDeadband);

        /* Drive */
        s_Swerve.drive(
            new Translation2d(rampy.calculate(translationVal), rampx.calculate(strafeVal)).times(Constants.Swerve.maxSpeed), 
            rampz.calculate(rotationVal) * Constants.Swerve.maxAngularVelocity, 
            true,//robotCentricSup.getAsBoolean(), 
            true
        );
    }
}