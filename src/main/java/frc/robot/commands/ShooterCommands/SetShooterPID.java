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
}
