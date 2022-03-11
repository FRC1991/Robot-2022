package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

public class FlipTurret extends CommandBase {

  public enum FlipType {
    positiveFlip,
    negativeFlip
  }

  FlipType flipType;
  Turret turret;
  double initialPosition, currentPosition;

  public FlipTurret(FlipType flipType) {
    this.flipType = flipType;
    turret = RobotContainer.mTurret;
    addRequirements(turret);
  }

  @Override
  public void initialize() {
    initialPosition = turret.getTurretPosition();
  }

  @Override
  public void execute() {
    switch (flipType) {
      case positiveFlip:
        turret.setTurret(1);
        break;
      case negativeFlip:
        turret.setTurret(-1);
        break;
      default:
        break;
    }
    currentPosition = turret.getTurretPosition();
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    // 60 revolutions of the neo 550 is one rotation of the entire turret assembly
    return Math.abs(initialPosition - currentPosition) >= 60;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
  }
}
