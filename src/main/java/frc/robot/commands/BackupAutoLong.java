package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import java.util.function.Supplier;

public class BackupAutoLong extends SequentialCommandGroup {
  public BackupAutoLong(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
    addCommands(
        new DriveDistance(5.4, -0.9),
        new BallChase(() -> (RobotContainer.ballXError))
        // new AimTurret(xSteerSupplier, yDistanceSupplier)
        // new ShootBall(3750, 2500)
        );
  }
}
