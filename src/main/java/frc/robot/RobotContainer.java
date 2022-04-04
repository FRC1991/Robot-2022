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
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.OperatingInterface.OperatingInterface;
import frc.robot.commands.AutoCommands.ComplexAuto;
import frc.robot.commands.AutoCommands.TwoBallAuto;
import frc.robot.commands.DrivetrainCommands.BallChase;
import frc.robot.commands.DrivetrainCommands.GTADrive;
import frc.robot.commands.DrivetrainCommands.TurnGyro;
import frc.robot.commands.IntakeCommands.FeedBallToShooter;
import frc.robot.commands.IntakeCommands.RunIntakeForBall;
import frc.robot.commands.IntakeCommands.RunIntakeOutForBall;
import frc.robot.commands.ShooterCommands.SetShooterPID;
import frc.robot.commands.TurretCommands.AimTurret;
import frc.robot.commands.TurretCommands.SetHoodAngle;
import frc.robot.subsystems.Climber;
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
  public static Climber mClimber = new Climber();
  public static double ballXError,
      targetXSteer,
      yDistance,
      maxSpeed,
      shooterRPMFlywheel1,
      shooterRPMFlywheel2,
      hoodAngle,
      manualRPMAdjust = 0;
  public static boolean isBallFound, isChasingBall, isTargetFound = false;
  NetworkTableEntry isBallFoundEntry,
      maxSpeedEntry,
      isChasingBallEntry,
      shooterRPMFlywheel1Entry,
      shooterRPMFlywheel2Entry,
      hoodAngleEntry,
      isTargetFoundEntry,
      isBallInEntry;
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
  BallChase triggerAccelBalChaseCommand =
      new BallChase(() -> (ballXError), oInterface::getDriveLeftTriggerAxis);
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
    isBallInEntry = Shuffleboard.getTab("Main").add("Ball In", false).getEntry();
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

    hoodAngleEntry =
        Shuffleboard.getTab("Main")
            .add("Hood Angle", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 55))
            .getEntry();
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
    // HttpCamera limelightBallCamera =
    //     new HttpCamera("limelight-balls-http", "http://10.19.91.69:5800");
    // Shuffleboard.getTab("Main").add(limelightBallCamera);

    autonomousChooser = new SendableChooser<Command>();
    autonomousChooser.setDefaultOption(
        "Two Ball Auto",
        new TwoBallAuto(() -> (ballXError), () -> (yDistance), () -> (targetXSteer)));
    autonomousChooser.addOption(
        "Complex Auto",
        new ComplexAuto(() -> (ballXError), () -> (yDistance), () -> (targetXSteer)));
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

    if (Robot.isRedAlliance) {
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

    hoodAngleEntry.addListener(
        (notification) -> {
          hoodAngle = notification.getEntry().getValue().getDouble();
        },
        Constants.defaultFlags);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Driver Driving Bindings
    mDrivetrain.setDefaultCommand(standardGTADriveCommand);

    // Driver Shifting Bindings (not needed for now)
    // oInterface.getDriveSelectButton().whenPressed(new ShiftToClimb().withTimeout(4));
    // oInterface.getDriveStartButton().whenPressed(new ShiftToDrive().withTimeout(4));

    // Driver Ball Chasing Bindings
    oInterface.getDriveAButton().whenPressed(triggerAccelBalChaseCommand);
    oInterface.getDriveBButton().whenPressed(standardGTADriveCommand);

    // Aux Manual Turret Control Bindings
    mTurret.setDefaultCommand(
        new RunCommand(
            () -> {
              mTurret.setTurret(oInterface.getAuxRightXAxis() * 0.2);
              // mTurret.setHood(oInterface.getAuxRightYAxis() * 0.2);
            },
            mTurret));

    // Aux Ball Chasing Bindings
    oInterface.getAuxAButton().whenPressed(standardBallChaseCommand);
    oInterface.getAuxBButton().whenPressed(standardGTADriveCommand);

    // Aux Intake Bindings
    oInterface.getAuxLeftStickDownButton().whileActiveOnce(new RunIntakeForBall());
    oInterface.getAuxLeftStickUpButton().whileActiveOnce(new RunIntakeOutForBall());

    // Aux Shooting Bindings
    oInterface
        .getAuxRightTriggerButton()
        .whenPressed(
            new SequentialCommandGroup(
                new PrintCommand(
                    Double.toString(SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance)))),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance)))),
                new FeedBallToShooter().withTimeout(0.3)));
    oInterface.getAuxLeftTriggerButton().whileActiveOnce(new AimTurret(() -> (targetXSteer)));
    oInterface
        .getAuxDPadUp()
        .whenPressed(
            () -> {
              manualRPMAdjust += 10;
            });
    oInterface
        .getAuxDPadDown()
        .whenPressed(
            () -> {
              manualRPMAdjust -= 10;
            });

    // Limelight Shooter Ranging
    mShooter.setDefaultCommand(
        new SetShooterPID(
            () -> (SetShooterPID.rangeRPM1WithLL(() -> (yDistance)*1.15)),
            () -> (SetShooterPID.rangeRPM2WithLL(() -> (yDistance)))));

    // DOE Bindings
    // mShooter.setDefaultCommand(dashboardBasedShooterRPMCommand);
    oInterface.getAuxXButton().whenPressed(new SetHoodAngle(() -> (hoodAngle)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // return new TwoBallAuto(()->(ballXError), ()->(yDistance), ()->(targetXSteer));
    return new ComplexAuto(() -> (ballXError), () -> (yDistance), () -> (targetXSteer));
    // return new TurnGyro(80, 0.5);
  }
}
