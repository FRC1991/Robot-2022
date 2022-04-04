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
    return -0.0007 * Math.pow(Math.abs(yDistanceSupplier.get()), 5)
        + 0.0642 * Math.pow(Math.abs(yDistanceSupplier.get()), 4)
        - 2.0282 * Math.pow(Math.abs(yDistanceSupplier.get()), 3)
        + 25.57 * Math.pow(Math.abs(yDistanceSupplier.get()), 2)
        - 83.95 * Math.abs(yDistanceSupplier.get())
        + 1999;
  }

  public static double rangeRPM2WithLL(Supplier<Double> yDistanceSupplier) {
    return 0.0004 * Math.pow(Math.abs(yDistanceSupplier.get()), 5)
        - 0.0343 * Math.pow(Math.abs(yDistanceSupplier.get()), 4)
        + 0.935 * Math.pow(Math.abs(yDistanceSupplier.get()), 3)
        - 10.506 * Math.pow(Math.abs(yDistanceSupplier.get()), 2)
        + 40.337 * Math.abs(yDistanceSupplier.get())
        + 1998.7;
  }
}
