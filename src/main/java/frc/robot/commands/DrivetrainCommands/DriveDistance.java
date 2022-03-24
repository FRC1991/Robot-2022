package frc.robot.commands.DrivetrainCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {

  private double driveDistance, driveSpeed = 0;
  private final Drivetrain mDrivetrain;

  public DriveDistance(double distance, double speed) {
    driveDistance = distance;
    driveSpeed = speed;
    mDrivetrain = RobotContainer.mDrivetrain;
    addRequirements(mDrivetrain);
  }

  @Override
  public void initialize() {
    mDrivetrain.resetEncoders();
  }

  @Override
  public void execute() {
    mDrivetrain.arcadeDrive(driveSpeed, 0);
  }

  @Override
  public boolean isFinished() {
    return (Math.abs(mDrivetrain.getDistanceFeet()) >= driveDistance);
  }

  @Override
  public void end(boolean interrupted) {
    mDrivetrain.stopDrivetrain();
  }
}
