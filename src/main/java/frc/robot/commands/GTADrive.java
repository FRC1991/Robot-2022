package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import java.util.function.Supplier;

public class GTADrive extends CommandBase {

  private final Drivetrain drivetrain;
  private final Supplier<Double> forwardSpeed, backwardSpeed, rotation, multiplier;
  private final Supplier<Boolean> isQuickTurn;

  public GTADrive(
      Supplier<Double> forwardSpeedSupplier,
      Supplier<Double> backwardSpeedSupplier,
      Supplier<Double> rotationSupplier,
      Supplier<Boolean> isQuickTurnSupplier,
      Supplier<Double> multiplierSupplier) {
    drivetrain = RobotContainer.mDrivetrain;
    forwardSpeed = forwardSpeedSupplier;
    backwardSpeed = backwardSpeedSupplier;
    rotation = rotationSupplier;
    multiplier = multiplierSupplier;
    isQuickTurn = isQuickTurnSupplier;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    drivetrain.setGTADrive(
        forwardSpeed.get(),
        backwardSpeed.get(),
        rotation.get(),
        isQuickTurn.get(),
        multiplier.get());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {}
}
