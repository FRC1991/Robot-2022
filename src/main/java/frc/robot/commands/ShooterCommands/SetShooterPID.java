package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import java.util.function.Supplier;

public class SetShooterPID extends CommandBase {

  private Shooter mShooter = RobotContainer.mShooter;
  private Supplier<Double> rpmFlywheel1, rpmFlywheel2;

  public SetShooterPID(Supplier<Double> rpmSupplier1, Supplier<Double> rpmSupplier2) {
    mShooter = RobotContainer.mShooter;
    rpmFlywheel1 = rpmSupplier1;
    rpmFlywheel2 = rpmSupplier2;
    addRequirements(mShooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mShooter.setShooterPID(rpmFlywheel1.get(), rpmFlywheel2.get());
    RobotContainer.measuredRPMFlywheel1Entry.setNumber(mShooter.getMainFlywheel1Velocity());
    RobotContainer.measuredRPMFlywheel2Entry.setNumber(mShooter.getSecondaryFlywheelVelocity());
    System.out.println(
        "Flywheel 1 RPM: " + rpmFlywheel1.get() + "\nFLywheel 2 RPM: " + rpmFlywheel2.get());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    // mShooter.setMainFlywheel1(0);
    // mShooter.setMainFlywheel2(0);
    // mShooter.setSecondaryFlywheel(0);
  }

  public static double rangeRPM1WithLL(Supplier<Double> yDistanceSupplier) {
    return 1983
        + 44.5 * yDistanceSupplier.get()
        - 2.69 * Math.pow(yDistanceSupplier.get(), 2)
        + 0.0847 * Math.pow(yDistanceSupplier.get(), 3)
        - 0.000721 * Math.pow(yDistanceSupplier.get(), 4);
  }

  public static double rangeRPM2WithLL(Supplier<Double> yDistanceSupplier) {
    return 2000
        - 23 * yDistanceSupplier.get()
        + 8.11 * Math.pow(yDistanceSupplier.get(), 2)
        - 1.06 * Math.pow(yDistanceSupplier.get(), 3)
        + 0.0648 * Math.pow(yDistanceSupplier.get(), 4)
        - 0.00187 * Math.pow(yDistanceSupplier.get(), 5)
        + 0.0000207 * Math.pow(yDistanceSupplier.get(), 6);
  }

  public static double rangeRPM1WithLLMiniTabUncodedUnits(Supplier<Double> yDistanceSupplier) {
    return 2047.6
        + 10.04 * Math.abs(yDistanceSupplier.get())
        + 0.3136 * Math.pow(Math.abs(yDistanceSupplier.get()), 2);
  }

  public static double rangeRPM2WithLMiniTabUncodedUnits(Supplier<Double> yDistanceSupplier) {
    return 2146.8
        - 40.33 * Math.abs(yDistanceSupplier.get())
        + 1.476 * Math.pow(Math.abs(yDistanceSupplier.get()), 2);
  }
}
