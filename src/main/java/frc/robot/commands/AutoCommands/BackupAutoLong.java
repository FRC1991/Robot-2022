package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.DrivetrainCommands.BallChase;
import frc.robot.commands.DrivetrainCommands.DriveDistance;
import java.util.function.Supplier;

public class BackupAutoLong extends SequentialCommandGroup {
  public BackupAutoLong(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier) {
    addCommands(
        new DriveDistance(5.4, -0.9), new BallChase(() -> (RobotContainer.ballXError))
        // new AimTurret(xSteerSupplier, yDistanceSupplier)
        // new ShootBall(3750, 2500)
        );
  }
}
