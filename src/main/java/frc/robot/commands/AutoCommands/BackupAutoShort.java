package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.DrivetrainCommands.DriveDistance;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.ShooterCommands.SetShooterPID;
import java.util.function.Supplier;

public class BackupAutoShort extends SequentialCommandGroup {
  public BackupAutoShort(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
    addCommands(
        new SetShooterPID(
                () -> ((2100.) + (Math.abs(RobotContainer.yDistance) * 41.3)), () -> (2000.))
            .withTimeout(3),
        new FeedBallToShooter().withTimeout(0.5),
        new DriveDistance(7, -0.9)
        // new BallChase(() -> (RobotContainer.ballXError), () -> (RobotContainer.isChasingBall))
        // new AimTurret(xSteerSupplier, yDistanceSupplier)
        );
  }
}
