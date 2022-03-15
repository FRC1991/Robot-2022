// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.function.Supplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootBallForReal extends SequentialCommandGroup {
  /** Creates a new ShootBallForReal. */
  public ShootBallForReal(
      Supplier<Double> mainFlywheelSpeed, Supplier<Double> secondaryFlywheelSpeed) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ShootBall(mainFlywheelSpeed, secondaryFlywheelSpeed));
  }
}
