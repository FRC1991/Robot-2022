// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.AimDrivetrain;
import frc.robot.commands.BallChase;
import frc.robot.commands.GTADrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  public static Drivetrain mDrivetrain = new Drivetrain();
  public static OperatingInterface oInterface = new OperatingInterface();
  public static Intake mIntake = new Intake();
  // public static ColorSensor mColorSensor = new ColorSensor();
  // public static LiDAR mLiDAR = new LiDAR();
  public static double centerXSteer, drivetrainXSteer, yDistance, maxSpeed = 0;
  public static boolean isTargetFound, isChasingBall = false;
  NetworkTableEntry isBallFoundEntry, maxSpeedEntry, isChasingBallEntry; 
  
  GTADrive standardGTADriveCommand = new GTADrive(oInterface::getRightTriggerAxis, oInterface::getLeftTriggerAxis, oInterface::getLeftXAxis, oInterface.getRightBumper()::get, ()->(maxSpeed));
  BallChase standardBallChaseCommand = new BallChase(()->(centerXSteer), ()->(isTargetFound));

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    dashboardInit();
    visionInit();    
    configureButtonBindings();
  }
  
  private void dashboardInit() {

    /**
     * 
     */
    
    
    isChasingBallEntry = Shuffleboard.getTab("Main").add("Is Chasing Ball", isChasingBall).getEntry();
    isBallFoundEntry = Shuffleboard.getTab("Main").add("Target Found", isTargetFound).getEntry();    
    maxSpeedEntry = Shuffleboard.getTab("Main")
    .add("Max Speed", Constants.GTADriveMultiplier)
    .withWidget(BuiltInWidgets.kNumberSlider)
    .withProperties(Map.of("min", 0, "max", 1))
    .getEntry();
  }

  private void visionInit() {
    NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
    NetworkTable fmsInfoNt = ntInst.getTable("FMSInfo");
    NetworkTable ballNt = ntInst.getTable("limelight-balls");
    NetworkTable shooterNt = ntInst.getTable("limelight-shooter");

    fmsInfoNt.addEntryListener("IsRedAlliance", (table, key, entry, value, flags) -> {
      if(value.getBoolean()){
        ballNt.getEntry("pipeline").setNumber(0);
      }
      else{
        ballNt.getEntry("pipeline").setNumber(1);
      }
    }, Constants.defaultFlags);

    ballNt.addEntryListener("tx", (table, key, entry, value, flags) -> {
      centerXSteer = value.getDouble();
    }, Constants.defaultFlags);

    ballNt.addEntryListener("tv", (table, key, entry, value, flags) -> {
      isTargetFound = value.getDouble() == 1;
      isBallFoundEntry.setBoolean(isTargetFound);
      
    }, Constants.defaultFlags);

    shooterNt.addEntryListener("tx", (table, key, entry, value, flags) -> {
      drivetrainXSteer = value.getDouble();
    }, Constants.defaultFlags);

    shooterNt.addEntryListener("ty", (table, key, entry, value, flags) -> {
      yDistance = value.getDouble();
    }, Constants.defaultFlags);

    maxSpeedEntry.addListener((notification)->{
      maxSpeed = notification.getEntry().getValue().getDouble();
    }, Constants.defaultFlags);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    

    mDrivetrain.setDefaultCommand(standardGTADriveCommand);
    oInterface.getAButton().whenPressed(standardBallChaseCommand);
    oInterface.getBButton().whenPressed(standardGTADriveCommand);
    oInterface.getLeftBumper().whenPressed(new AimDrivetrain(()->(centerXSteer), ()->(yDistance)));
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
