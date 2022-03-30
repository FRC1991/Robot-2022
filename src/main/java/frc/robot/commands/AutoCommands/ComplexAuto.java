// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.commands.DrivetrainCommands.BallChase;
import frc.robot.commands.DrivetrainCommands.DriveDistance;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.ShooterCommands.SetShooterPID;
import frc.robot.commands.TurretCommands.AimTurret;
import java.util.function.Supplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ComplexAuto extends SequentialCommandGroup {
  /** Creates a new AutoPath1. */
  public ComplexAuto(
      Supplier<Double> xSteerSupplier,
      Supplier<Double> yDistanceSupplier,
      Supplier<Double> targetXSteerSupplier) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new TwoBallAuto(xSteerSupplier, yDistanceSupplier, targetXSteerSupplier),
        // new TurnGyro(60, -0.5),
        new RunCommand(
                () -> {
                  RobotContainer.mDrivetrain.setDrivetrain(-0.7, 0.7);
                },
                RobotContainer.mDrivetrain)
            .withTimeout(0.4),
        new DriveDistance(4, -1),
        new BallChase(xSteerSupplier),
        new WaitCommand(0.5),
        new RunCommand(
                () -> {
                  RobotContainer.mDrivetrain.setDrivetrain(0.5, -0.5);
                },
                RobotContainer.mDrivetrain)
            .withTimeout(0.5),
        new DriveDistance(2, 1),
        new SetShooterPID(
                () -> (SetShooterPID.rangeWithLimelight(yDistanceSupplier)), () -> (2000.))
            .withTimeout(2),
        new AimTurret(targetXSteerSupplier).withTimeout(1),
        new FeedBallToShooter().withTimeout(0.5)
        // new BallChase(xSteerSupplier),
        // new WaitCommand(0.5),
        // new DriveDistance(5, 0.9),
        // new AimTurret(targetXSteerSupplier
        // ).withTimeout(1),
        //     new SetShooterPID(
        //       () ->
        // ((0.0146*Math.pow(Math.abs(yDistanceSupplier.get()),3)-(0.2013*Math.pow(Math.abs(yDistanceSupplier.get()),2))+(27.232*Math.abs(yDistanceSupplier.get()))+1972.8)),
        //       () -> (2000.)
        //       ).withTimeout(2),
        // new FeedBallToShooter().withTimeout(0.5)
        );
  }
}
