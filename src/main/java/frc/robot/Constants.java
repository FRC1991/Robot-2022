// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.EntryListenerFlags;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int leftMotor1 = 3;
  public static final int leftMotor2 = 4;
  public static final int rightMotor1 = 1;
  public static final int rightMotor2 = 2;
  public static final int turretMotor = 6;
  public static final double GTADriveMultiplier = 0.4;
  public static final double tankDriveMultiplier = 2;
  public static final double kPForVision = 0.89;
  public static final int proximitySensorDIOIndex = 2;
  public static final double globalDeadband = 0.1;
  public static int defaultFlags =
      EntryListenerFlags.kNew
          | EntryListenerFlags.kUpdate
          | EntryListenerFlags.kLocal
          | EntryListenerFlags.kImmediate;
}
