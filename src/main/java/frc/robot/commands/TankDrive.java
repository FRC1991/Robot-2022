package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import java.util.function.Supplier;

public class TankDrive extends CommandBase {

  private Drivetrain drivetrain;
  private final double MULTIPLIER = Constants.tankDriveMultiplier;
  private final Supplier<Double> leftSpeed, rightSpeed;

  public TankDrive(Supplier<Double> leftSupplier, Supplier<Double> rightSupplier) {
    drivetrain = RobotContainer.mDrivetrain;
    leftSpeed = leftSupplier;
    rightSpeed = rightSupplier;
    addRequirements(RobotContainer.mDrivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    drivetrain.setDrivetrain(leftSpeed.get(), rightSpeed.get(), MULTIPLIER);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {}
}
