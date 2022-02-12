package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import java.util.function.Supplier;

public class SetShooterPID extends CommandBase {

  private Shooter mShooter = RobotContainer.mShooter;
  private Supplier<Double> rpmFlywheel1, rpmFlywheel2;
  private NetworkTableEntry measuredRPMFlywheel1Entry, measuredRPMFlywheel2Entry;

  public SetShooterPID(Supplier<Double> rpmSupplier1, Supplier<Double> rpmSupplier2) {
    mShooter = RobotContainer.mShooter;
    rpmFlywheel1 = rpmSupplier1;
    rpmFlywheel2 = rpmSupplier2;
    measuredRPMFlywheel1Entry =
        Shuffleboard.getTab("Main")
            .add("Flywheel 1 Measured RPM", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
    measuredRPMFlywheel2Entry =
        Shuffleboard.getTab("Main")
            .add("Flywheel 2 Measured RPM", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();
    addRequirements(mShooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mShooter.setShooterPID(rpmFlywheel1.get(), rpmFlywheel2.get());
    measuredRPMFlywheel1Entry.setNumber(mShooter.getVelocity());
    measuredRPMFlywheel2Entry.setNumber(mShooter.getVelovityFlywheel2());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    mShooter.setFlywheel1(0);
    mShooter.setFlywheel2(0);
  }
}
