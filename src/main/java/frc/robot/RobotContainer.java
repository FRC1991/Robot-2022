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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.OperatingInterface.OperatingInterface;
import frc.robot.commands.AutoCommands.ComplexAuto;
import frc.robot.commands.AutoCommands.TwoBallAuto;
import frc.robot.commands.ClimberCommands.RunClimber;
import frc.robot.commands.DrivetrainCommands.BallChase;
import frc.robot.commands.DrivetrainCommands.GTADrive;
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

  // #region ====================Subsystems======================
  public static Drivetrain mDrivetrain = new Drivetrain();
  public static OperatingInterface oInterface = new OperatingInterface();
  public static Intake mIntake = new Intake();
  public static Shooter mShooter = new Shooter();
  public static Turret mTurret = new Turret();
  public static Climber mClimber = new Climber();
  // #endregion

  // #region ===================Global Vars======================
  public static double ballXError,
      targetXSteer,
      yDistance,
      maxSpeed,
      shooterRPMFlywheel1,
      shooterRPMFlywheel2,
      hoodAngle = 0;
  public static double manualRPMAdjust = 0.0;
  public static boolean isChasingBall, isTargetFound, isDefenseMode = false;
  NetworkTableEntry maxSpeedEntry,
      isChasingBallEntry,
      shooterRPMFlywheel1Entry,
      shooterRPMFlywheel2Entry,
      hoodAngleEntry,
      isTargetFoundEntry,
      isBallInEntry,
      isDefenseModeEntry;
  public static NetworkTableEntry measuredRPMFlywheel1Entry, measuredRPMFlywheel2Entry;
  SendableChooser<Command> autonomousChooser;
  // #endregion

  // #region ==================Make Commands=====================
  GTADrive standardGTADriveCommand =
      new GTADrive(
          oInterface::getDriveRightTriggerAxis,
          oInterface::getDriveLeftTriggerAxis,
          oInterface::getDriveLeftXAxis,
          oInterface.getDriveRightBumper()::get,
          () -> (maxSpeed));
  BallChase standardBallChaseCommand = new BallChase(() -> (ballXError));
  BallChase triggerAccelBalChaseCommand =
      new BallChase(() -> (ballXError), oInterface::getDriveLeftTriggerAxis);
  SetShooterPID dashboardBasedShooterRPMCommand =
      new SetShooterPID(() -> (shooterRPMFlywheel1), () -> (shooterRPMFlywheel2));

  int yDistanceListener;

  // #endregion

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands. Try to keep this as
   * clean as possible, extract most of your code into functions.
   */
  public RobotContainer() {
    dashboardInit();
    NTListenerInit();
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

    //#region =================Vision Entries===================
    isChasingBallEntry = Shuffleboard.getTab("Main").add("Chasing Ball", isChasingBall).getEntry();
    isBallInEntry = Shuffleboard.getTab("Main").add("Ball In", false).getEntry();
    isTargetFoundEntry =
        Shuffleboard.getTab("Main").add("Shot Target Found", isTargetFound).getEntry();

    maxSpeedEntry =
        Shuffleboard.getTab("Main")
            .add("Max Speed", Constants.GTADriveMultiplier)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1))
            .getEntry();
    //#endregion

    //#region =================Diagnostic Entries===================
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
            .withProperties(Map.of("min", 0, "max", 6000))
            .getEntry();

    hoodAngleEntry =
        Shuffleboard.getTab("Main")
            .add("Hood Angle", 0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 55))
            .getEntry();

    //#endregion
    
    //#region ================Shooter RPM Entries===================
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
    //#endregion

    //#region ==================Autonomous Chooser===================
    isDefenseModeEntry =
        Shuffleboard.getTab("Main")
            .add("Defense Mode", false)
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .getEntry();
    //#endregion

    // HttpCamera limelightBallCamera =
    //     new HttpCamera("limelight-balls-http", "http://10.19.91.69:5800");
    // Shuffleboard.getTab("Main").add(limelightBallCamera);


    //#region ========================Shuffleboard=======================
    autonomousChooser = new SendableChooser<Command>();
    autonomousChooser.setDefaultOption(
        "Two Ball Auto",
        new TwoBallAuto(() -> (ballXError), () -> (yDistance), () -> (targetXSteer)));
    autonomousChooser.addOption(
        "Complex Auto",
        new ComplexAuto(() -> (ballXError), () -> (yDistance), () -> (targetXSteer)));
    Shuffleboard.getTab("Main").add(autonomousChooser);
    //#endregion
  }

  /*
   * Mostly NT setup for now, could change later
   */
  private void NTListenerInit() {

    //#region ====================Network Tables Setup===================
    NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
    NetworkTable ballNt = ntInst.getTable("limelight-balls");
    NetworkTable shooterNt = ntInst.getTable("limelight-shooter");
    //#endregion

    //#region ====================Network Tables Listeners===================
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

    // update shooter target information
    shooterNt.addEntryListener(
        "tx",
        (table, key, entry, value, flags) -> {
          targetXSteer = value.getDouble();
        },
        Constants.defaultFlags);

    yDistanceListener =
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

    isDefenseModeEntry.addListener(
        (notification) -> {
          isDefenseMode = notification.getEntry().getValue().getBoolean();
          if (isDefenseMode) {
            NetworkTableEntry currentPipeline =
                NetworkTableInstance.getDefault().getTable("limelight-balls").getEntry("pipeline");
            if (currentPipeline.getDouble(2) == 0) {
              currentPipeline.setNumber(1);
            } else if (currentPipeline.getDouble(2) == 1) {
              currentPipeline.setNumber(0);
            }
            staticRPMAndHoodAngle(36.0);
          } else {
            NetworkTableInstance.getDefault()
                .getTable("limelight-shooter")
                .getEntry("ty")
                .addListener(
                    (thisNotif) -> {
                      yDistance = thisNotif.getEntry().getValue().getDouble();
                    },
                    Constants.defaultFlags);
          }
        },
        Constants.defaultFlags);
    //#endregion
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    //#region ====================Driver Driving Bindings=====================
    mDrivetrain.setDefaultCommand(standardGTADriveCommand);
    //#endregion

    //#region ============Driver Shifting Bindings (not needed for now)===========
    // oInterface.getDriveSelectButton().whenPressed(new ShiftToClimb().withTimeout(4));
    // oInterface.getDriveStartButton().whenPressed(new ShiftToDrive().withTimeout(4));
    //#endregion

    //#region ====================Driver Ball Chasing Bindings====================
    oInterface.getDriveAButton().whenPressed(triggerAccelBalChaseCommand);
    oInterface.getDriveBButton().whenPressed(standardGTADriveCommand);
    //#endregion

    //#region =======================Driver Climbing Bindings=======================  
    oInterface.getDriveLeftBumper().whileHeld(new RunClimber(() -> (-1.0)));
    oInterface.getDriveRightBumper().whileHeld(new RunClimber(() -> (1.0)));
    //#endregion

    //#region ======================Driver Speed Bindings===========================
    oInterface
        .getDriveRightStickDownButton()
        .whenPressed(
            new InstantCommand(
                () -> {
                  NetworkTableInstance.getDefault()
                      .getTable("Shuffleboard")
                      .getSubTable("Main")
                      .getEntry("Max Speed")
                      .setNumber(1.0);
                }));

    oInterface
        .getDriveXButton()
        .whenPressed(
            new InstantCommand(
                () -> {
                  NetworkTableInstance.getDefault()
                      .getTable("Shuffleboard")
                      .getSubTable("Main")
                      .getEntry("Max Speed")
                      .setNumber(0.36);
                }));
    //#endregion

    //#region ===================Driver Shooter Manual Ranging Bindings====================
    oInterface
        .getDriveYButton()
        .whenPressed(
            new SequentialCommandGroup(
                new InstantCommand(
                    () -> {
                      NetworkTableInstance.getDefault()
                          .getTable("limelight-shooter")
                          .getEntry("ty")
                          .addListener(
                              (notification) -> {
                                yDistance = notification.getEntry().getValue().getDouble();
                              },
                              Constants.defaultFlags);
                    }),
                new SetShooterPID(
                    () ->
                        (SetShooterPID.rangeRPM1WithLL(() -> (Math.abs(yDistance)))
                            + manualRPMAdjust),
                    () ->
                        (SetShooterPID.rangeRPM2WithLL(() -> (Math.abs(yDistance)))
                            + manualRPMAdjust))));

    oInterface
        .getDriveDPadUp()
        .whenPressed(
            new SequentialCommandGroup(
                new InstantCommand(
                    () -> {
                      staticRPMAndHoodAngle(13.4);
                    }),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance))))));

    oInterface
        .getDriveDPadDown()
        .whenPressed(
            new SequentialCommandGroup(
                new InstantCommand(
                    () -> {
                      staticRPMAndHoodAngle(0.);
                    }),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance))))));

    oInterface
        .getDriveDPadLeft()
        .whenPressed(
            new SequentialCommandGroup(
                new InstantCommand(
                    () -> {
                      staticRPMAndHoodAngle(21.5);
                    }),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance))))));

    oInterface
        .getDriveDPadRight()
        .whenPressed(
            new SequentialCommandGroup(
                new InstantCommand(
                    () -> {
                      staticRPMAndHoodAngle(30.);
                    }),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance))))));
    //#endregion

    //#region ======================Aux Manual Turret Control Bindings======================
    mTurret.setDefaultCommand(
        new RunCommand(
            () -> {
              mTurret.setTurret(oInterface.getAuxRightXAxis() * 0.4);
              // mTurret.setHood(oInterface.getAuxRightYAxis() * 0.2);
            },
            mTurret));
    //#endregion

    //#region ======================Aux Ball Chasing Bindings======================
    oInterface.getAuxAButton().whenPressed(standardBallChaseCommand);
    oInterface.getAuxBButton().whenPressed(standardGTADriveCommand);
    //#endregion

    //#region ======================Aux Intake Bindings======================
    oInterface.getAuxLeftStickDownButton().whileActiveOnce(new RunIntakeForBall());
    oInterface.getAuxLeftStickUpButton().whileActiveOnce(new RunIntakeOutForBall());
    //#endregion

    //#region ======================Aux Shooting Bindings======================
    oInterface
        .getAuxRightTriggerButton()
        .whenPressed(
            new SequentialCommandGroup(
                // new PrintCommand(
                //     Double.toString(SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance)))),
                new PrintCommand(
                    "Shot Fired With RPM:"
                        + Double.toString(
                            SetShooterPID.rangeRPM1WithLL(
                                () -> (Math.abs(yDistance) + manualRPMAdjust)))),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance)))),
                new FeedBallToShooter().withTimeout(0.3)));
    //#endregion

    //#region ======================Aux Aiming Bindings======================
    oInterface
        .getAuxLeftTriggerButton()
        .whileActiveOnce(
            new ParallelCommandGroup(
                new AimTurret(() -> (targetXSteer)),
                new SetHoodAngle(() -> (SetHoodAngle.rangeHoodAngleWithLL(Math.abs(yDistance))))));
    //#endregion

    //#region ===================Aux Manual RPM Adjust Binding====================
    oInterface
        .getAuxDPadUp()
        .whenPressed(
            new InstantCommand(
                () -> {
                  manualRPMAdjust = manualRPMAdjust + 50;
                  System.out.println(manualRPMAdjust);
                }));

    oInterface
        .getAuxDPadDown()
        .whenPressed(
            new InstantCommand(
                () -> {
                  manualRPMAdjust = manualRPMAdjust - 50;
                  System.out.println(manualRPMAdjust);
                }));
    //#endregion

    //#region ======================Limelight Shooter Ranging======================
    mShooter.setDefaultCommand(
        new SetShooterPID(
            () -> (SetShooterPID.rangeRPM1WithLL(() -> (Math.abs(yDistance))) + manualRPMAdjust),
            () -> (SetShooterPID.rangeRPM2WithLL(() -> (Math.abs(yDistance))))));
    //#endregion


    //#region ======================Aux Defense Mode Binding======================
    oInterface
        .getAuxStartButton()
        .whenPressed(
            new InstantCommand(
                () -> {
                  NetworkTableInstance.getDefault()
                      .getTable("Shuffleboard")
                      .getSubTable("Main")
                      .getEntry("Defense Mode")
                      .setBoolean(true);
                }));
    //#endregion

    //#region ===========================DOE Bindings===========================
    // mShooter.setDefaultCommand(dashboardBasedShooterRPMCommand);
    // oInterface.getAuxXButton().whenPressed(new SetHoodAngle(() -> (hoodAngle)));
    //#endregion
  }

  private void staticRPMAndHoodAngle(double staticDistance) {
    yDistance = staticDistance;
    NetworkTableInstance.getDefault()
        .getTable("limelight-shooter")
        .getEntry("ty")
        .removeListener(yDistanceListener);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // return new TwoBallAuto(()->(ballXError), ()->(yDistance), ()->(targetXSteer));
    // return new ComplexAuto(() -> (ballXError), () -> (yDistance), () -> (targetXSteer));
    // return new TurnGyro(80, 0.5);
    return autonomousChooser.getSelected();
  }
}
