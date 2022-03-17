// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DrivetrainCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class ShiftToDrive extends CommandBase {

  Drivetrain drivetrain;
  double initialPosition;
  double startTime, currentTime;

  /** Creates a new StartClimb. */
  public ShiftToDrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = RobotContainer.mDrivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.setServos(0.47, 0.16);
    initialPosition = drivetrain.getDistanceFeet();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // startTime = Timer.getFPGATimestamp();
    // drivetrain.setServos(0.455, 0.145);
    // if(Timer.getFPGATimestamp() - startTime >= 0.2){
    //   drivetrain.setDrivetrain(-0.15, -0.15);
    //   System.out.printf("Init: %f   Cur: %f\n", initialPosition,
    // drivetrain.getTransverseShaftEncoderPosition());
    //   drivetrain.setServos(0.47, 0.16);
    // }

    drivetrain.setServos(0.455, 0.145);
    Timer.delay(0.2);
    drivetrain.setDrivetrain(-0.15, -0.15);
    // System.out.printf("Init: %f   Cur: %f\n", initialPosition,
    // drivetrain.getTransverseShaftEncoderPosition());
    drivetrain.setServos(0.47, 0.16);
    Timer.delay(0.2);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(drivetrain.getDistanceFeet() - initialPosition) >= 3.5;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.setDrivetrain(0, 0);
  }

  // Returns true when the command should end.
}
