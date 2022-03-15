package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import java.util.function.Supplier;

public class BallChase extends CommandBase {

  private Drivetrain drivetrain;
  private Intake intake;
  private OperatingInterface oInterface = RobotContainer.oInterface;
  private final double steeringScale = Constants.kPForVision;
  private double steeringAdjust = 0;
  private Supplier<Double> xSteer;
  private Supplier<Boolean> isTargetFound;

  // TODO: Integerate target detection

  public BallChase(Supplier<Double> xSteerSupplier, Supplier<Boolean> isTargetFoundSupplier) {
    drivetrain = RobotContainer.mDrivetrain;
    intake = RobotContainer.mIntake;
    isTargetFound = isTargetFoundSupplier;
    addRequirements(drivetrain);
    xSteer = xSteerSupplier;
  }

  @Override
  public void initialize() {
    oInterface.singleVibrateDrive();
    NetworkTableInstance.getDefault()
        .getTable("Shuffleboard")
        .getSubTable("Main")
        .getEntry("Is Chasing Ball")
        .setBoolean(true);
  }

  @Override
  public void execute() {
    // if target is off by more than 1 degree, adjust steering, otherwise, do nothing
    // note that this is a very rough approximation, and may need to be adjusted
    // multiplying by 0.015 to normalize the degree value to between -1 and 1
    System.out.println("Ball Chase Input From LL: " + xSteer.get());
    if (xSteer.get() > 0.2) {
      steeringAdjust = xSteer.get() * 0.015;
      steeringAdjust = steeringAdjust * steeringScale;
    } else if (xSteer.get() < -0.2) {
      steeringAdjust = xSteer.get() * 0.015;
      steeringAdjust = steeringAdjust * steeringScale;
    } else {
      steeringAdjust = 0;
    }
    drivetrain.arcadeDrive(0, -steeringAdjust);
    System.out.println("Ball Chase Output to Drive " + steeringAdjust);

    intake.setIntakeMotor1(-0.5);
  }

  @Override
  public boolean isFinished() {
    // if ball is captured, finish command
    return intake.isBallIn();
  }

  @Override
  public void end(boolean interrupted) {
    // let driver know they have control again and update network tables
    intake.setIntakeMotor1(0);
    oInterface.doubleVibrateDrive();
    NetworkTableInstance.getDefault()
        .getTable("Shuffleboard")
        .getSubTable("Main")
        .getEntry("Is Chasing Ball")
        .setBoolean(false);
  }
}
