package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class RunIntakeForBall extends CommandBase {

  Intake intake;
  public boolean isFirstBallInAlready;

  public RunIntakeForBall() {
    intake = RobotContainer.mIntake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
    isFirstBallInAlready = intake.isBallPresentInner();
  }

  @Override
  public void execute() {
    intake.setIntakeMotor1(-0.8);
  }

  @Override
  public boolean isFinished() {
    if (isFirstBallInAlready) return intake.isBallPresentOuter();
    else return intake.isBallPresentInner();
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeMotor1(0);
    intake.setIntakeMotor2(0);
  }
}
