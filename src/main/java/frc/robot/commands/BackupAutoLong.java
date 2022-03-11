package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class BackupAutoLong extends SequentialCommandGroup {
  public BackupAutoLong() {
    addCommands(
        new DriveDistance(5.4, 0.6),
        new BallChase(()->(RobotContainer.ballXError), ()->(RobotContainer.isChasingBall))
        //INSERT SHOOT AND AIM COMMAND HERE
        );
  }
}
