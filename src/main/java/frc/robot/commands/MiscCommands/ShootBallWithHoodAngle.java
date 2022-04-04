// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.MiscCommands;

import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.TurretCommands.SetHoodAngle;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootBallWithHoodAngle extends SequentialCommandGroup {
  /** Creates a new ShootBallWithHoodAngle. */
  public ShootBallWithHoodAngle(double hoodAngle) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new PrintCommand(Double.toString(hoodAngle)),
        new SetHoodAngle(() -> (hoodAngle)).withTimeout(2),
        new FeedBallToShooter().withTimeout(0.3));
  }
}
