// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// does not work, not sure why, will not fix

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.AutoPath1;
import frc.robot.commands.BackupAutoLong;
import frc.robot.commands.BackupAutoShort;
import frc.robot.commands.BallChase;
import frc.robot.commands.FeedBallToShooter;
import frc.robot.commands.GTADrive;
import frc.robot.commands.RunIntakeForBall;
import frc.robot.commands.RunIntakeOutForBall;
import frc.robot.commands.SetShooterPID;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
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
  public static Shooter mShooter = new Shooter();
  public static Turret mTurret = new Turret();
  public static double ballXError,
      targetXSteer,
      yDistance,
      maxSpeed,
      shooterRPMFlywheel1,
      shooterRPMFlywheel2,
      hoodAngle = 0;
  public static boolean isBallFound, isChasingBall, isTargetFound = false;
  NetworkTableEntry isBallFoundEntry,
      maxSpeedEntry,
      isChasingBallEntry,
      shooterRPMFlywheel1Entry,
      shooterRPMFlywheel2Entry,
      hoodAngleEntry,
      isTargetFoundEntry;
  public static NetworkTableEntry measuredRPMFlywheel1Entry, measuredRPMFlywheel2Entry;

  SendableChooser<Command> autonomousChooser;

  GTADrive standardGTADriveCommand =
      new GTADrive(
          oInterface::getDriveRightTriggerAxis,
          oInterface::getDriveLeftTriggerAxis,
          oInterface::getDriveLeftXAxis,
          oInterface.getDriveRightBumper()::get,
          () -> (maxSpeed),
          oInterface.getDriveXButton()::get);
  BallChase standardBallChaseCommand = new BallChase(() -> (ballXError));
  SetShooterPID dashboardBasedShooterRPMCommand =
      new SetShooterPID(() -> (shooterRPMFlywheel1), () -> (shooterRPMFlywheel2));

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands. Try to keep this as
   * clean as possible, extract most of your code into functions.
   */
  public RobotContainer() {
    dashboardInit();
    visionInit();
    configureButtonBindings();
  }

  /**
   * Add variables to shuffleboard
   *
   * <p>isChasingBallEntry determines if driver has control or not isBallFoundEntry is secondary
   * confirmation for ball detection maxSpeedEntry is the limiter for GTADrive (falls back to value
   * set in Constants.java)super secret secret(lets hope it saves)
   */
  private void dashboardInit() {
    isChasingBallEntry = Shuffleboard.getTab("Main").add("Chasing Ball", isChasingBall).getEntry();
    isBallFoundEntry = Shuffleboard.getTab("Main").add("Ball Found", isBallFound).getEntry();

    isTargetFoundEntry =
        Shuffleboard.getTab("Main").add("Shot Target Found", isTargetFound).getEntry();

    maxSpeedEntry =
        Shuffleboard.getTab("Main")
            .add("Max Speed", Constants.GTADriveMultiplier)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1))
            .getEntry();
    shooterRPMFlywheel1Entry =
        Shuffleboard.getTab("Main")
            .add("Shooter Flywheel 1 RPM", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 6000))
            .getEntry();
    shooterRPMFlywheel2Entry =
        Shuffleboard.getTab("Main")
            .add("Shooter Flywheel 2 RPM", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 20000))
            .getEntry();

    // hoodAngleEntry =
    // Shuffleboard.getTab("Main")
    // .add("Hood Angle", 0)
    // .withWidget(BuiltInWidgets.kNumberSlider)
    // .withProperties(Map.of("min", 0, "max", 55))
    // .getEntry();
    measuredRPMFlywheel1Entry =
        Shuffleboard.getTab("Main")
            .add("Flywheel 1", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

    measuredRPMFlywheel2Entry =
        Shuffleboard.getTab("Main")
            .add("Flywheel 2", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

    autonomousChooser = new SendableChooser<Command>();
    autonomousChooser.addOption(
        "Backup Auto Short", new BackupAutoShort(() -> (targetXSteer), () -> (yDistance)));
    autonomousChooser.addOption(
        "Backup Auto Long", new BackupAutoLong(() -> (targetXSteer), () -> (yDistance)));
    autonomousChooser.addOption("Complex Auto", new AutoPath1());
    autonomousChooser.setDefaultOption(
        "Backup Auto Long", new BackupAutoLong(() -> (targetXSteer), () -> (yDistance)));
    Shuffleboard.getTab("Main").add(autonomousChooser);

    // HttpCamera limelightBallCamera =
    //     new HttpCamera("limelight-balls-http", "http://10.19.91.69:5800");
    // Shuffleboard.getTab("Main").add(limelightBallCamera);
  }

  /*
   * Mostly NT setup for now, could change later
   */
  private void visionInit() {
    // network tables setup
    NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
    NetworkTable ballNt = ntInst.getTable("limelight-balls");
    NetworkTable shooterNt = ntInst.getTable("limelight-shooter");

    // add entry listeners to update variables in code from network tables

    // check what alliance color we're on and update limelight to track respective balls

    if(Robot.isRedAlliance) {
      ballNt.getEntry("pipeline").setNumber(0);
    } else {
      ballNt.getEntry("pipeline").setNumber(1);
    }


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
          isBallFound = value.getDouble() == 1;
          isBallFoundEntry.setBoolean(isBallFound);
        },
        Constants.defaultFlags);



    // update shooter target information
    shooterNt.addEntryListener(
        "tx",
        (table, key, entry, value, flags) -> {
          targetXSteer = value.getDouble();
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

    // update shooter RPM from dashboard
    shooterRPMFlywheel1Entry.addListener(
        (notification) -> {
          shooterRPMFlywheel1 = notification.getEntry().getValue().getDouble();
        },
        Constants.defaultFlags);
    shooterRPMFlywheel2Entry.addListener(
        (notification) -> {
          shooterRPMFlywheel2 = notification.getEntry().getValue().getDouble();
        },
        Constants.defaultFlags);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   *
   */

  private void configureButtonBindings() {

    // Driver Driving Bindings
    mDrivetrain.setDefaultCommand(standardGTADriveCommand);

    // Driver Shifting Bindings (not needed for now)
    // oInterface.getDriveSelectButton().whenPressed(new ShiftToClimb().withTimeout(4));
    // oInterface.getDriveStartButton().whenPressed(new ShiftToDrive().withTimeout(4));

    // Driver Ball Chasing Bindings
    oInterface.getDriveAButton().whenPressed(standardBallChaseCommand);
    oInterface.getDriveBButton().whenPressed(standardGTADriveCommand);

    // Aux Manual Turret Control Bindings
    mTurret.setDefaultCommand(new RunCommand(() -> {
        mTurret.setTurret(oInterface.getAuxRightXAxis() * 0.2);
      },mTurret));
    
    // Aux Ball Chasing Bindings
    oInterface.getAuxAButton().whenPressed(standardBallChaseCommand);
    oInterface.getAuxBButton().whenPressed(standardGTADriveCommand);
    
    // Aux Intake Bindings
    oInterface.getAuxLeftStickDownButton().whileActiveContinuous(new RunIntakeForBall());
    oInterface.getAuxLeftStickUpButton().whileActiveContinuous(new RunIntakeOutForBall());

    // Aux Shooting Bindings
    oInterface.getAuxRightTriggerButton().whileActiveOnce(new FeedBallToShooter().withTimeout(0.5));
    
    // Limelight Shooter Ranging
    mShooter.setDefaultCommand(
        new SetShooterPID(() -> ((2100.) + (Math.abs(yDistance) * 44)), () -> ((2000.)- (Math.abs(yDistance) * 22))));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *  
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autonomousChooser.getSelected();
  }
}
