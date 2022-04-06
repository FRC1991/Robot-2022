// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DrivetrainCommands.BallChase;
import frc.robot.commands.DrivetrainCommands.DriveDistance;
import frc.robot.commands.DrivetrainCommands.TurnGyro;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.TurretCommands.AimTurret;
import frc.robot.commands.TurretCommands.SetHoodAngle;
import java.util.function.Supplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ComplexAuto extends SequentialCommandGroup {
  /** Creates a new AutoPath1. */
  public ComplexAuto(
      Supplier<Double> xSteerSupplier,
      Supplier<Double> yDistanceSupplier,
      Supplier<Double> targetXErrorSupplier) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new TwoBallAuto(xSteerSupplier, yDistanceSupplier, targetXErrorSupplier),
        new TurnGyro(110, -0.5),
        new BallChase(xSteerSupplier),
        new TurnGyro(25, 0.5),
        new AimTurret(targetXErrorSupplier).withTimeout(1.5),
        new SetHoodAngle(
            () -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistanceSupplier.get())))),
        new FeedBallToShooter().withTimeout(0.3),
        new BallChase(xSteerSupplier, () -> (1.)),
        new DriveDistance(30, 0.9),
        new AimTurret(targetXErrorSupplier).withTimeout(1.5),
        new SetHoodAngle(
            () -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistanceSupplier.get())))),
        new FeedBallToShooter().withTimeout(0.3));
  }
}
