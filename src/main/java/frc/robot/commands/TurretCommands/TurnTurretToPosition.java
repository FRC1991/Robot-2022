// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.TurretCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;
import java.util.function.Supplier;

public class TurnTurretToPosition extends CommandBase {

  Turret turret;
  Supplier<Double> targetPosition, speed;
  double initialPosition, currentPosition;

  /** Creates a new TurnTurretToPosition. */
  public TurnTurretToPosition(
      Supplier<Double> targetPositionSupplier, Supplier<Double> speedSupplier) {
    // Use addRequirements() here to declare subsystem dependencies.
    turret = RobotContainer.mTurret;
    targetPosition = targetPositionSupplier;
    addRequirements(turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialPosition = turret.getTurretPosition();
    currentPosition = initialPosition;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentPosition = turret.getTurretPosition();
    if (targetPosition.get() > 0) {
      turret.setTurret(speed.get() * Math.abs(targetPosition.get() - currentPosition));
    } else {
      turret.setTurret(-speed.get() * Math.abs(targetPosition.get() - currentPosition));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stopTurret();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(targetPosition.get() - currentPosition) < 0.1;
  }
}
