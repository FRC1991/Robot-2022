// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import java.util.function.Supplier;

public class ShootBall extends ParallelRaceGroup {
  public ShootBall(Supplier<Double> mainFlywheelSpeed, Supplier<Double> secondaryFlywheelSpeed) {
    addCommands(
        new SetShooterPID(mainFlywheelSpeed, secondaryFlywheelSpeed).withTimeout(1.5),
        new FeedBallToShooter().withTimeout(0.5));
  }
}
