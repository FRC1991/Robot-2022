// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.BallChase;
import frc.robot.commands.GTADrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;

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
  public static double centerXSteer = 0;
  public static boolean isTargetFound = false;
  private GTADrive standardGTADriveCommand = new GTADrive(oInterface::getRightTriggerAxis, oInterface::getLeftTriggerAxis, oInterface::getLeftXAxis, oInterface.getRightBumper()::get);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    visionInit();    
    configureButtonBindings();
  }

  
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // mDrivetrain.setDefaultCommand(new BallChase(()->(centerXSteer)));
    // mDrivetrain.setDefaultCommand(new TankDrive());
    // mDrivetrain.arcadeDrive(0.5, centerXSteer);
    // GTADrive gtaDrive = new GTADrive();
    // mDrivetrain.setDefaultCommand(gtaDrive);
    // JoystickButton aButton = new JoystickButton(oInterface.driveJoystick, 1);
    // aButton.whenPressed(new InstantCommand(()->{System.out.printf("Blue: %d, Red: %d, Green: %d\n", mColorSensor.getBlue(), mColorSensor.getRed(), mColorSensor.getGreen());}, mColorSensor));
    // mLiDAR.setDefaultCommand(new GetDistanceLiDAR());
    // aButton.whenPressed(new GetDistanceLiDAR());
    
    mDrivetrain.setDefaultCommand(standardGTADriveCommand);
    oInterface.getAButton().whenPressed(new BallChase(()->(centerXSteer), ()->(isTargetFound)));
    oInterface.getBButton().whenPressed(standardGTADriveCommand);
  }

  private void visionInit() {
    NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
    NetworkTable nt = ntInst.getTable("limelight-balls");

    if(DriverStation.getAlliance().equals(Alliance.Red)){
      nt.getEntry("pipeline").setNumber(0);
    }
    else {
      nt.getEntry("pipeline").setNumber(1);
    }

    nt.addEntryListener("tx", (table, key, entry, value, flags) -> {
      centerXSteer = value.getDouble();
    }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

    // nt.addEntryListener("tv", (table, key, entry, value, flags) -> {
    //   isTargetFound = value.getBoolean();
    // }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

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
