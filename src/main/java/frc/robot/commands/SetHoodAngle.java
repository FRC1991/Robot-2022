package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;
import java.util.function.Supplier;

public class SetHoodAngle extends CommandBase {

  Turret turret;
  double initialPosition;
  Supplier<Double> hoodAngle;

  public SetHoodAngle(Supplier<Double> hoodAngleSupplier) {
    turret = RobotContainer.mTurret;
    hoodAngle = hoodAngleSupplier;
    addRequirements(turret);
  }

  @Override
  public void initialize() {
    initialPosition = turret.getHoodPosition();
  }

  @Override
  public void execute() {
    if (hoodAngle.get() > initialPosition) {
      turret.setHood(-0.5);
    } else {
      turret.setHood(0.5);
    }
  }

  @Override
  public boolean isFinished() {
    return Math.abs(hoodAngle.get() - turret.getHoodPosition()) >= 0.5;
  }

  @Override
  public void end(boolean interrupted) {
    turret.setHood(0);
  }
}
