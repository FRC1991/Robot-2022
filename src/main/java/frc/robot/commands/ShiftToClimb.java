// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class ShiftToClimb extends CommandBase {

  Drivetrain drivetrain;
  double initialPosition;
  OperatingInterface oInterface;
  
  /** Creates a new StartClimb. */
  public ShiftToClimb() {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = RobotContainer.mDrivetrain;
    oInterface = RobotContainer.oInterface;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setServos(0.39, 0.225);
    initialPosition = drivetrain.getTransverseShaftEncoderPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.setServos(0.375, 0.21);
    Timer.delay(0.2);
    drivetrain.setDrivetrain(-0.15, -0.15);
    System.out.printf("Init: %f   Cur: %f\n", initialPosition, drivetrain.getTransverseShaftEncoderPosition());
    drivetrain.setServos(0.39, 0.225);
    Timer.delay(0.2);

  }

  @Override
  public boolean isFinished() {
    return Math.abs(drivetrain.getTransverseShaftEncoderPosition() - initialPosition) >= 1;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setDrivetrain(0, 0);
  }

  // Returns true when the command should end.
}
