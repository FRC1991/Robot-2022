package frc.robot.commands.TurretCommands;

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
      turret.setHood(0.1);
    } else {
      turret.setHood(-0.1);
    }
  }

  @Override
  public boolean isFinished() {
    // System.out.println(Math.abs(hoodAngle.get() - turret.getHoodPosition()));
    return Math.abs(hoodAngle.get() - turret.getHoodPosition()) <= 0.5;
  }

  @Override
  public void end(boolean interrupted) {
    turret.setHood(0);
  }

  public static double rangeHoodAngleWithLL(double yDistance) {
    return -0.0072
        + 0.69 * yDistance
        - 0.238 * Math.pow(yDistance, 2)
        + 0.0262 * Math.pow(yDistance, 3)
        - 0.000917 * Math.pow(yDistance, 4)
        + 0.00000983 * Math.pow(yDistance, 5)
        + 0.0000000175 * Math.pow(yDistance, 6);
  }

  public static double rangeHoodAngleWithLLMiniTabUncodedUnits(double yDistance) {
    return -2.249 + 0.485 * yDistance + 0.01307 * Math.pow(yDistance, 2);
  }
}
