package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DrivetrainCommands.DriveDistanceUntilCapture;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.ShooterCommands.SetShooterPID;
import frc.robot.commands.TurretCommands.AimTurret;
import java.util.function.Supplier;

public class TwoBallAuto extends SequentialCommandGroup {
  public TwoBallAuto(
      Supplier<Double> xSteerSupplier,
      Supplier<Double> yDistanceSupplier,
      Supplier<Double> targetXErrorSupplier) {
    addCommands(
        new SetShooterPID(
                () -> (SetShooterPID.rangeWithLimelight(yDistanceSupplier)), () -> (2000.))
            .withTimeout(2.3),
        new FeedBallToShooter().withTimeout(0.5),
        new DriveDistanceUntilCapture(10, -0.9),
        new SetShooterPID(
                () -> (SetShooterPID.rangeWithLimelight(yDistanceSupplier)), () -> (2000.))
            .withTimeout(1),
        new AimTurret(targetXErrorSupplier).withTimeout(1),
        new FeedBallToShooter().withTimeout(0.5));
  }
}
