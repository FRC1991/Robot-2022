// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AimDrivetrain;
import frc.robot.commands.BallChase;
import frc.robot.commands.GTADrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import java.util.Map;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems, commands, and global variables are defined here

  public static Drivetrain mDrivetrain = new Drivetrain();
  public static OperatingInterface oInterface = new OperatingInterface();
  public static Intake mIntake = new Intake();
  public static double ballXError, drivetrainXSteer, yDistance, maxSpeed = 0;
  public static boolean isTargetFound, isChasingBall = false;
  NetworkTableEntry isBallFoundEntry, maxSpeedEntry, isChasingBallEntry;

  GTADrive standardGTADriveCommand =
      new GTADrive(
          oInterface::getRightTriggerAxis,
          oInterface::getLeftTriggerAxis,
          oInterface::getLeftXAxis,
          oInterface.getRightBumper()::get,
          () -> (maxSpeed));
  BallChase standardBallChaseCommand = new BallChase(() -> (ballXError), () -> (isTargetFound));

  /** The container for the robot. Contains subsystems, OI devices, and commands. 
   * Try to keep this as clean as possible, extract most of your code into functions.
  */
  public RobotContainer() {
    dashboardInit();
    visionInit();
    configureButtonBindings();
  }

  /** Add variables to shuffleboard
   * 
   * isChasingBallEntry determines if driver has control or not
   * isBallFoundEntry is secondary confirmation for ball detection
   * maxSpeedEntry is the limiter for GTADrive (falls back to value set in Constants.java)
  */
  private void dashboardInit() {
    isChasingBallEntry =
        Shuffleboard.getTab("Main").add("Is Chasing Ball", isChasingBall).getEntry();

    isBallFoundEntry = Shuffleboard.getTab("Main").add("Target Found", isTargetFound).getEntry();

    maxSpeedEntry =
        Shuffleboard.getTab("Main")
            .add("Max Speed", Constants.GTADriveMultiplier)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1))
            .getEntry();
  }

  /*
    * Mostly NT setup for now, could change later
    */
  private void visionInit() {
    // network tables setup
    NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
    NetworkTable fmsInfoNt = ntInst.getTable("FMSInfo");
    NetworkTable ballNt = ntInst.getTable("limelight-balls");
    NetworkTable shooterNt = ntInst.getTable("limelight-shooter");

    // add entry listeners to update variables in code from network tables

    // check what alliance color we're on and update limelight to track respective balls
    fmsInfoNt.addEntryListener(
        "IsRedAlliance",
        (table, key, entry, value, flags) -> {
          if (value.getBoolean()) {
            ballNt.getEntry("pipeline").setNumber(0);
          } else {
            ballNt.getEntry("pipeline").setNumber(1);
          }
        },
        Constants.defaultFlags);

    // update ball information
    ballNt.addEntryListener(
        "tx",
        (table, key, entry, value, flags) -> {
          ballXError = value.getDouble();
        },
        Constants.defaultFlags);

    ballNt.addEntryListener(
        "tv",
        (table, key, entry, value, flags) -> {
          isTargetFound = value.getDouble() == 1;
          isBallFoundEntry.setBoolean(isTargetFound);
        },
        Constants.defaultFlags);

    // update shooter target information
    shooterNt.addEntryListener(
        "tx",
        (table, key, entry, value, flags) -> {
          drivetrainXSteer = value.getDouble();
        },
        Constants.defaultFlags);

    shooterNt.addEntryListener(
        "ty",
        (table, key, entry, value, flags) -> {
          yDistance = value.getDouble();
        },
        Constants.defaultFlags);

    // update max speed from dashboard
    maxSpeedEntry.addListener(
        (notification) -> {
          maxSpeed = notification.getEntry().getValue().getDouble();
        },
        Constants.defaultFlags);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   * 
   * A button: Chase ball
   * B button: Cancel chase, returm to GTADrive
   * Left Bumper: start aiming drivetrain
   * TODO: Chainge left bumper to left trigger 
   */
  private void configureButtonBindings() {
    mDrivetrain.setDefaultCommand(standardGTADriveCommand);
    oInterface.getAButton().whenPressed(standardBallChaseCommand);
    oInterface.getBButton().whenPressed(standardGTADriveCommand);
    oInterface
        .getLeftBumper()
        .whenPressed(new AimDrivetrain(() -> (ballXError), () -> (yDistance)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * 
   * Will likely be some kind of Ramsette Command, but for now, leave it as is.
   * TODO: add Ramsette command
   * 
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
