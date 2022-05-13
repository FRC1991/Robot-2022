package frc.robot.commands.IntakeCommands;

import edu.wpi.first.networktables.NetworkTableInstance;
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
  public void initialize() {
    NetworkTableInstance.getDefault()
        .getTable("Shuffleboard")
        .getSubTable("Main")
        .getEntry("Ball In")
        .setBoolean(false);
  }

  @Override
  public void execute() {
    // intake.setIntakeMotor1(-0.8);
    intake.setIntakeMotor2(0.8);
  }

  @Override
  public boolean isFinished() {
    return intake.isBallPresentInner();
  }

  @Override
  public void end(boolean interrupted) {
    // intake.setIntakeMotor1(0);
    intake.setIntakeMotor2(0);
    NetworkTableInstance.getDefault()
        .getTable("Shuffleboard")
        .getSubTable("Main")
        .getEntry("Ball In")
        .setBoolean(true);
  }
}
