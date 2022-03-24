package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DrivetrainCommands.BallChase;
import frc.robot.commands.DrivetrainCommands.DriveDistance;
import frc.robot.commands.DrivetrainCommands.DriveDistanceAndCapture;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.IntakeCommands.RunIntakeForBall;
import frc.robot.commands.ShooterCommands.SetShooterPID;
import frc.robot.commands.TurretCommands.AimTurret;

import java.util.function.Supplier;

public class TwoBallAuto extends SequentialCommandGroup {
  public TwoBallAuto(Supplier<Double> xSteerSupplier, Supplier<Double> yDistanceSupplier, Supplier<Double> targetXErrorSupplier) {
    addCommands(
      new SetShooterPID(
            () -> ((0.0146*Math.pow(Math.abs(yDistanceSupplier.get()),3)-(0.2013*Math.pow(Math.abs(yDistanceSupplier.get()),2))+(27.232*Math.abs(yDistanceSupplier.get()))+1972.8)),
            () -> (2000.)
            ).withTimeout(2.3),
      new FeedBallToShooter().withTimeout(0.5),
      // new DriveDistance(7, -0.9),
      // new RunIntakeForBall(),
      new DriveDistanceAndCapture(10, -0.9),
      new SetShooterPID(
            () -> ((0.0146*Math.pow(Math.abs(yDistanceSupplier.get()),3)-(0.2013*Math.pow(Math.abs(yDistanceSupplier.get()),2))+(27.232*Math.abs(yDistanceSupplier.get()))+1972.8)),
            () -> (2000.)
            ).withTimeout(1),
      new AimTurret(targetXErrorSupplier).withTimeout(1),
      new FeedBallToShooter().withTimeout(0.5)
      );
  }
}
