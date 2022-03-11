package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class BackupAutoShort extends SequentialCommandGroup {
  public BackupAutoShort() {
    addCommands(
      new DriveDistance(2.5, 0.6),
      new BallChase(()->(RobotContainer.ballXError), ()->(RobotContainer.isChasingBall))
      //INSERT SHOOT AND AIM COMMAND HERE
    );
  }
}
