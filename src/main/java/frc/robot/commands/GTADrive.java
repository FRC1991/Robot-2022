package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class GTADrive extends CommandBase {

    private final Drivetrain drivetrain;
    private final double MULTIPLIER = 0.3;
    private final  Supplier<Double> forwardSpeed, backwardSpeed, rotation;
    private final Supplier<Boolean> isQuickTurn;
    
    public GTADrive(Supplier<Double> forwardSpeedSupplier, Supplier<Double> backwardSpeedSupplier, Supplier<Double> rotationSupplier, Supplier<Boolean> isQuickTurnSupplier) {
        drivetrain = RobotContainer.mDrivetrain;
        forwardSpeed = forwardSpeedSupplier;
        backwardSpeed = backwardSpeedSupplier;
        rotation = rotationSupplier;
        isQuickTurn = isQuickTurnSupplier;
        addRequirements(drivetrain);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        drivetrain.setGTADriveV2(forwardSpeed.get(), backwardSpeed.get(), rotation.get(), isQuickTurn.get(), MULTIPLIER);
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
