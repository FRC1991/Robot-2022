package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import java.util.function.Supplier;

public class BackupAutoShort extends SequentialCommandGroup {
  public BackupAutoShort(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
    addCommands(
      new SetShooterPID(() -> ((2100.) + (Math.abs(RobotContainer.yDistance) * 41.3)), () -> (2000.)).withTimeout(3),
      new FeedBallToShooter().withTimeout(0.5),
      new DriveDistance(7, -0.9)
        // new BallChase(() -> (RobotContainer.ballXError), () -> (RobotContainer.isChasingBall))
        // new AimTurret(xSteerSupplier, yDistanceSupplier)
        );
  }
}
