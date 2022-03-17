// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoPath1 extends SequentialCommandGroup {
  /** Creates a new AutoPath1. */
  public AutoPath1() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new DriveDistance(2.66, 0.5),
        new BallChase(() -> (RobotContainer.ballXError)),
        // INSERT SHOOT AND AIM COMMAND HERE
        new TurnGyro(56, 0.5),
        new DriveDistance(7.75, 0.5),
        new BallChase(() -> (RobotContainer.ballXError)),
        // INSERT SHOOT AND AIM COMMAND HERE
        new DriveDistance(5.33, 0.5),
        new TurnGyro(117, 0.5),
        new DriveDistance(9.25, 0.5),
        new BallChase(() -> (RobotContainer.ballXError))
        // INSERT SHOOT AND AIM COMMAND HERE
        );
  }
}
