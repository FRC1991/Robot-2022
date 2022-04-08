// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ClimberCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;
import java.util.function.Supplier;

public class RunClimber extends CommandBase {

  Climber climber;
  Supplier<Double> climbSpeed;

  /** Creates a new RunClimber. */
  public RunClimber(Supplier<Double> climbSpeedSupplier) {
    // Use addRequirements() here to declare subsystem dependencies.
    climber = RobotContainer.mClimber;
    climbSpeed = climbSpeedSupplier;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // NetworkTableInstance.getDefault()
    //     .getTable("Shuffleboard")
    //     .getSubTable("Main")
    //     .getEntry("Max Speed")
    //     .setNumber(0.37);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.setClimberMotor(climbSpeed.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.setClimberMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
