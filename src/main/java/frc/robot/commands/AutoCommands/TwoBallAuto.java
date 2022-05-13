package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DrivetrainCommands.DriveAndWait;
import frc.robot.commands.DrivetrainCommands.DriveDistanceUntilCapture;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.TurretCommands.AimTurretNew;
import frc.robot.commands.TurretCommands.SetHoodAngle;
import java.util.function.Supplier;

public class TwoBallAuto extends SequentialCommandGroup {
  public TwoBallAuto(
      Supplier<Double> xSteerSupplier,
      Supplier<Double> yDistanceSupplier,
      Supplier<Double> targetXErrorSupplier) {
    addCommands(
        new DriveAndWait(2, 5.5, -0.35),
        new SetHoodAngle(
            () -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistanceSupplier.get())))),
        new FeedBallToShooter().withTimeout(0.3),
        new DriveDistanceUntilCapture(10, -0.9),
        new AimTurretNew(targetXErrorSupplier).withTimeout(1),
        new SetHoodAngle(
            () -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistanceSupplier.get())))),
        new FeedBallToShooter().withTimeout(0.3));
  }
}
