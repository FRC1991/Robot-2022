// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootBall extends SequentialCommandGroup {
  /** Creates a new ShootBall. */
  public ShootBall() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new FeedBallToShooter()
      // new SetShooterPID(()->(0.), ()->(0.)).withTimeout(3),
      // new SetShooterPID(()->(0.), ()->(0.))
    );
  }
}
