package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class BallChase extends CommandBase {
    
    private Drivetrain drivetrain;
    private Intake intake;
    private OperatingInterface oInterface = RobotContainer.oInterface;
    private final double minCommand = 0.25;
    private final double steeringScale = Constants.kPForVision;
    private double steeringAdjust = 0;
    private Supplier<Double> xSteer;
    // private Supplier<Boolean> isTargetFound;
    
    public BallChase(Supplier<Double> xSteerSupplier, Supplier<Boolean> isTargetFoundSupplier) {
        drivetrain = RobotContainer.mDrivetrain;
        intake = RobotContainer.mIntake;
        // isTargetFound = isTargetFoundSupplier;
        addRequirements(drivetrain);
        xSteer = xSteerSupplier;
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        if(xSteer.get() > 1.0){
            steeringAdjust = xSteer.get() * 0.015;
            steeringAdjust = steeringAdjust+minCommand;
            steeringAdjust = steeringAdjust*steeringScale;
        }
        else if(xSteer.get() < -1.0){
            steeringAdjust = xSteer.get() * 0.015;
            steeringAdjust = steeringAdjust - minCommand;
            steeringAdjust = steeringAdjust * steeringScale;
        }
        else{
            steeringAdjust = 0;
        }
        drivetrain.arcadeDrive(0.8, steeringAdjust);
    }

    @Override
    public boolean isFinished() {
        // return isTargetFound.get();
        return intake.isBallIn();
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted){
            oInterface.doubleVibrate();
        }
    }
}
