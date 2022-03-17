package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class RunIntakeForBall extends CommandBase {

  Intake intake;

  public RunIntakeForBall() {
    intake = RobotContainer.mIntake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.setIntakeMotor1(0.8);
    System.out.println(RobotContainer.oInterface.getAuxLeftStickDownButton().get());
  }

  @Override
  public boolean isFinished() {
    return intake.isBallIn();
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeMotor1(0);
  }
}
