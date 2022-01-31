package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class BallChase extends CommandBase {
    
    private Drivetrain drivetrain;
    private Supplier<Double> xSteer;
    
    public BallChase(Supplier<Double> xSteerSupplier) {
        drivetrain = RobotContainer.mDrivetrain;
        addRequirements(drivetrain);
        xSteer = xSteerSupplier;
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(0.65, xSteer.get()*2);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
