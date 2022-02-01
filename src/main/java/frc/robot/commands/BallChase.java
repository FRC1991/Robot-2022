package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class BallChase extends CommandBase {
    
    private Drivetrain drivetrain;
    private final double minCommand = 0.25;
    private final double steeringScale = 0.85;
    private double steeringAdjust = 0;
    private Supplier<Double> xSteer;
    private Supplier<Boolean> isTargetFound;
    
    public BallChase(Supplier<Double> xSteerSupplier, Supplier<Boolean> isTargetFoundSupplier) {
        drivetrain = RobotContainer.mDrivetrain;
        isTargetFound = isTargetFoundSupplier;
        addRequirements(drivetrain);
        xSteer = xSteerSupplier;
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        if(xSteer.get()>1.0){
            steeringAdjust = xSteer.get()*0.015;
            steeringAdjust = steeringAdjust+0.25;
            steeringAdjust = steeringAdjust*steeringScale;
            drivetrain.arcadeDrive(0, steeringAdjust);
        }
        else if(xSteer.get()<-1.0){
            steeringAdjust = xSteer.get()*0.015;
            steeringAdjust = steeringAdjust-0.25;
            steeringAdjust = steeringAdjust*steeringScale;
            drivetrain.arcadeDrive(0, steeringAdjust);
        }
        else{
            steeringAdjust = 0;
        }
        drivetrain.arcadeDrive(0, steeringAdjust);
    }

    @Override
    public boolean isFinished() {
        // return isTargetFound.get();
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
