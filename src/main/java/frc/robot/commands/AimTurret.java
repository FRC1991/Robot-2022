package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;
import java.util.function.Supplier;

public class AimTurret extends CommandBase {

  // TODO: Integerate target detection, ranging based on yDistance
  // TODO: Convert to turret code once it's bulit

  private Turret turret;
  private OperatingInterface oInterface = RobotContainer.oInterface;
  private final double minCommand = 0.25;
  private final double steeringScale = Constants.kPForVision;
  private double steeringAdjust = 0;
  private Supplier<Double> xSteer, yDistance;
  // private Supplier<Boolean> isTargetFound;

  public AimTurret(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
    turret = RobotContainer.mTurret;
    addRequirements(turret);
    xSteer = xSteerSupplier;
    yDistance = yDistanceSupplier;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    // if target is off by more than 1 degree, adjust steering, otherwise, do nothing
    // note that this is a very rough approximation, and may need to be adjusted
    // multiplying by 0.015 to normalize the degree value to between -1 and 1
    if ((xSteer.get() > 0.2) || (xSteer.get() < -0.2)) {
      steeringAdjust = xSteer.get() * 0.015;
      steeringAdjust = steeringAdjust + minCommand;
      steeringAdjust = steeringAdjust * steeringScale;
    } else {
      steeringAdjust = 0;
    }
    turret.setTurret(steeringAdjust);
  }

  @Override
  public boolean isFinished() {
    // if target is within 1 degree, finish command
    return xSteer.get() < 0.2;
  }

  @Override
  public void end(boolean interrupted) {
    // let driver know they have control again
    oInterface.doubleVibrateDrive();
  }
}
