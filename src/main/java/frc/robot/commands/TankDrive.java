package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase {
    
    private Drivetrain drivetrain;
    private final double MULTIPLIER = 0.6;
    private final Supplier<Double> leftSpeed, rightSpeed;

    public TankDrive(Supplier<Double> leftSupplier, Supplier<Double> rightSupplier) {
        drivetrain = RobotContainer.mDrivetrain;
        leftSpeed = leftSupplier;
        rightSpeed = rightSupplier;
        addRequirements(RobotContainer.mDrivetrain);
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.setDrivetrain(leftSpeed.get(), rightSpeed.get(), MULTIPLIER);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

}
