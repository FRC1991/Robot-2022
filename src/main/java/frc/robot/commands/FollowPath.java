package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FollowPath extends SequentialCommandGroup {
  public FollowPath() {
    addCommands(
        new DriveDistance(3, 0.5),
        new TurnGyro(90, -0.5),
        new DriveDistance(3, 0.5),
        new TurnGyro(90, -0.5),
        new DriveDistance(3, 0.5),
        new TurnGyro(90, 0.5),
        new DriveDistance(3, 0.5),
        new TurnGyro(90, 0.5),
        new DriveDistance(3, 0.5));
  }
}
