package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;
import java.util.function.Supplier;

public class SetShooterPID extends CommandBase {

  private Shooter mShooter = RobotContainer.mShooter;
  private Supplier<Double> rpm;

  public SetShooterPID(Supplier<Double> rpmSupplier) {
    mShooter = RobotContainer.mShooter;
    rpm = rpmSupplier;
    addRequirements(mShooter);
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    mShooter.setTurretPID(rpm.get());
    System.out.println(mShooter.getVelocity());
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
  }
}
