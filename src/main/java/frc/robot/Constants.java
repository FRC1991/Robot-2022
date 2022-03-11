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
  public static final int leftMotor1 = 4;
  public static final int leftMotor2 = 5;
  public static final int leftMotor3 = 6;
  public static final int rightMotor1 = 1;
  public static final int rightMotor2 = 2;
  public static final int rightMotor3 = 3;
  public static final int turretMotor = 7;
  public static final int hoodMotor = 12;
  public static final int intakeMotor1 = 13;
  public static final int intakeMotor2 = 14;
  public static final int mainFlywheelMotor1 = 9;
  public static final int mainFlywheelMotor2 = 10;
  public static final int secondaryFlywheel = 11;
  public static final int elevatorRaiseMotor = 8;
  public static final double GTADriveMultiplier = 0.8;
  public static final double tankDriveMultiplier = 2;
  public static final double kPForVision = 2.3;
  public static final int proximitySensorDIOIndex = 0;
  public static final double globalDeadband = 0.1;
  public static final double kPFlywheel1 = 0.00026666;
  public static final double kIFlywheel1 = 0.000000632;
  public static final double kdFlywheel1 = 0.00004;
  public static final double kPFlywheel2 = 0.000013333;
  public static final double kIFlywheel2 = 0.000000266;
  public static final double kdFlywheel2 = 0;
  public static final double kMaxOutput = 1;
  public static final double kMinOutput = -1;
  public static int defaultFlags =
      EntryListenerFlags.kNew
          | EntryListenerFlags.kUpdate
          | EntryListenerFlags.kLocal
          | EntryListenerFlags.kImmediate;
}
