package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class FeedBallToShooter extends CommandBase {
  Intake intake;

  public FeedBallToShooter() {
    intake = RobotContainer.mIntake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.setIntakeMotor1(-0.5);
    intake.setIntakeMotor2(-0.8);
  }

  @Override
  public boolean isFinished() {
    // return !intake.isBallIn();
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeMotor1(0);
    intake.setIntakeMotor2(0);
  }
}
