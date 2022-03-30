package frc.robot.commands.DrivetrainCommands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatingInterface.OperatingInterface;
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
  private Supplier<Double> xSteer, speed;

  public BallChase(Supplier<Double> xSteerSupplier) {
    drivetrain = RobotContainer.mDrivetrain;
    intake = RobotContainer.mIntake;
    addRequirements(drivetrain);
    xSteer = xSteerSupplier;
    speed = () -> (0.1);
  }

  public BallChase(Supplier<Double> xSteerSupplier, Supplier<Double> speed) {
    drivetrain = RobotContainer.mDrivetrain;
    intake = RobotContainer.mIntake;
    addRequirements(drivetrain);
    xSteer = xSteerSupplier;
    this.speed = speed;
  }

  @Override
  public void initialize() {
    // oInterface.singleVibrateDrive();
    oInterface.driveJoystick.setRumble(RumbleType.kRightRumble, 1);
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
    drivetrain.arcadeDrive(-speed.get(), -steeringAdjust);
    // System.out.println("Ball Chase Output to Drive " + steeringAdjust);

    intake.setIntakeMotor1(-0.5);
    intake.setIntakeMotor2(0.5);
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
    intake.setIntakeMotor2(0);
    // oInterface.doubleVibrateDrive();
    oInterface.driveJoystick.setRumble(RumbleType.kRightRumble, 0);
    NetworkTableInstance.getDefault()
        .getTable("Shuffleboard")
        .getSubTable("Main")
        .getEntry("Is Chasing Ball")
        .setBoolean(false);
  }
}
