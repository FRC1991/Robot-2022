// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DrivetrainCommands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class TurnUntilTargetFound extends CommandBase {

  private final Drivetrain mDrivetrain;
  private NetworkTableEntry isTargetFoundEntry;
  private double turnSpeed;

  /** Creates a new TurnUntilTargetFound. */
  public TurnUntilTargetFound(double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    mDrivetrain = RobotContainer.mDrivetrain;
    turnSpeed = speed;
    isTargetFoundEntry =
        NetworkTableInstance.getDefault().getTable("limelight-shooter").getEntry("tv");
    addRequirements(mDrivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDrivetrain.arcadeDrive(0, turnSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDrivetrain.arcadeDrive(0, turnSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mDrivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isTargetFoundEntry.getDouble(0) == 1;
  }
}
