package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class RotateGyro extends CommandBase {

  private double initialAngle, currentAngle, setpointAngle, turnSpeed = 0;
  private final Drivetrain mDrivetrain;

  public RotateGyro(double angle, double speed) {
    setpointAngle = angle;
    mDrivetrain = RobotContainer.mDrivetrain;
    setpointAngle = angle;
    turnSpeed = speed;
    addRequirements(mDrivetrain);
  }

  @Override
  public void initialize() {
    initialAngle = mDrivetrain.getHeading();
  }

  @Override
  public void execute() {
    // TODO: make drivetrain turn
    currentAngle = mDrivetrain.getHeading();
    mDrivetrain.arcadeDrive(0, turnSpeed);
  }

  @Override
  public boolean isFinished() {
    return (Math.abs((initialAngle - currentAngle)) >= setpointAngle);
  }

  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
    mDrivetrain.arcadeDrive(0, 0);
  }
}
