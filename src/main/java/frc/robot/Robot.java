// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private NetworkTable ballNt;
  public static boolean isRedAlliance = false;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    isRedAlliance = DriverStation.getAlliance().compareTo(DriverStation.Alliance.Red) == 0;
    ballNt = NetworkTableInstance.getDefault().getTable("limelight-balls");
    if (Robot.isRedAlliance) {
      ballNt.getEntry("pipeline").setNumber(0);
    } else {
      ballNt.getEntry("pipeline").setNumber(1);
    }
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    RobotContainer.mTurret.hoodMotor.setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getLeftMotor1().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getLeftMotor2().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getLeftMotor3().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getRightMotor1().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getRightMotor2().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getRightMotor3().setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    isRedAlliance = DriverStation.getAlliance().compareTo(DriverStation.Alliance.Red) == 0;
    ballNt = NetworkTableInstance.getDefault().getTable("limelight-balls");
    if (Robot.isRedAlliance) {
      ballNt.getEntry("pipeline").setNumber(0);
    } else {
      ballNt.getEntry("pipeline").setNumber(1);
    }
    RobotContainer.mDrivetrain.getLeftMotor1().setIdleMode(IdleMode.kBrake);
    RobotContainer.mDrivetrain.getLeftMotor2().setIdleMode(IdleMode.kBrake);
    RobotContainer.mDrivetrain.getLeftMotor3().setIdleMode(IdleMode.kBrake);
    RobotContainer.mDrivetrain.getRightMotor1().setIdleMode(IdleMode.kBrake);
    RobotContainer.mDrivetrain.getRightMotor2().setIdleMode(IdleMode.kBrake);
    RobotContainer.mDrivetrain.getRightMotor3().setIdleMode(IdleMode.kBrake);
    RobotContainer.mTurret.hoodMotor.setIdleMode(IdleMode.kBrake);
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    RobotContainer.mDrivetrain.getLeftMotor1().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getLeftMotor2().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getLeftMotor3().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getRightMotor1().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getRightMotor2().setIdleMode(IdleMode.kCoast);
    RobotContainer.mDrivetrain.getRightMotor3().setIdleMode(IdleMode.kCoast);
    RobotContainer.mTurret.hoodMotor.setIdleMode(IdleMode.kBrake);
    isRedAlliance = DriverStation.getAlliance().compareTo(DriverStation.Alliance.Red) == 0;
    ballNt = NetworkTableInstance.getDefault().getTable("limelight-balls");
    if (Robot.isRedAlliance) {
      ballNt.getEntry("pipeline").setNumber(0);
    } else {
      ballNt.getEntry("pipeline").setNumber(1);
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
