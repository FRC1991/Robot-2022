package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class AimDrivetrain extends CommandBase{

    // TODO: Integerate target detection, ranging based on yDistance

    private Drivetrain drivetrain;
    private OperatingInterface oInterface = RobotContainer.oInterface;
    private final double minCommand = 0.25;
    private final double steeringScale = Constants.kPForVision;
    private double steeringAdjust = 0;
    private Supplier<Double> xSteer, yDistance;
    // private Supplier<Boolean> isTargetFound;
    
    public AimDrivetrain(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
        drivetrain = RobotContainer.mDrivetrain;
        addRequirements(drivetrain);
        xSteer = xSteerSupplier;
        yDistance = yDistanceSupplier;
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
        drivetrain.arcadeDrive(0, steeringAdjust);
    }

    @Override
    public boolean isFinished() {
        return xSteer.get()<1.0;
    }

    @Override
    public void end(boolean interrupted) {
        if(!interrupted){
            oInterface.doubleVibrate();
        }
    }
}
