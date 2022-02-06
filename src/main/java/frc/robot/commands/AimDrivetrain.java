package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import java.util.function.Supplier;

public class AimDrivetrain extends CommandBase {

  // TODO: Integerate target detection, ranging based on yDistance
  // TODO: Convert to turret code once it's bulit

  private Drivetrain drivetrain;
  private OperatingInterface oInterface = RobotContainer.oInterface;
  private final double minCommand = 0.25;
  private final double steeringScale = Constants.kPForVision;
  private double steeringAdjust = 0;
  private Supplier<Double> xSteer, yDistance;
  // private Supplier<Boolean> isTargetFound;

  public AimDrivetrain(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
    drivetrain = RobotContainer.mDrivetrain;
    addRequirements(drivetrain);
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
    if ((xSteer.get() > 1.0) || (xSteer.get() < -1.0)) {
      steeringAdjust = xSteer.get() * 0.015;
      steeringAdjust = steeringAdjust + minCommand;
      steeringAdjust = steeringAdjust * steeringScale;
    } else {
      steeringAdjust = 0;
    }
    drivetrain.arcadeDrive(0, steeringAdjust);
  }

  @Override
  public boolean isFinished() {
    // if target is within 1 degree, finish command
    return xSteer.get() < 1.0;
  }

  @Override
  public void end(boolean interrupted) {
    // let driver know they have control again
    oInterface.doubleVibrate();
  }
}
