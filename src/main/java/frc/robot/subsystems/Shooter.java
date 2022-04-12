package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final CANSparkMax mainFlyWheelMotor1, mainFlywheelMotor2, secondaryFlywheelMotor;
  private SparkMaxPIDController pidControllerMainFlywheel1, pidControllerSecondaryFlywheel;
  private final double kPMainFlywheel = Constants.kPFlywheel1;
  private final double kIMainFlywheel = Constants.kIFlywheel1;
  private final double kDMainFlywheel = Constants.kdFlywheel1;
  private final double kPSecondaryFlywheel = Constants.kdFlywheel2;
  private final double kISecondaryFlywheel = Constants.kIFlywheel2;
  private final double kDSecondaryFlywheel = Constants.kdFlywheel2;
  private final double kMaxOutput = Constants.kMaxOutput;
  private final double kMinOutput = Constants.kMinOutput;

  public Shooter() {
    mainFlyWheelMotor1 = new CANSparkMax(Constants.mainFlywheelMotor1, MotorType.kBrushless);
    mainFlywheelMotor2 = new CANSparkMax(Constants.mainFlywheelMotor2, MotorType.kBrushless);
    secondaryFlywheelMotor = new CANSparkMax(Constants.secondaryFlywheel, MotorType.kBrushless);
    mainFlyWheelMotor1.setInverted(false);
    mainFlywheelMotor2.follow(mainFlyWheelMotor1, true);
    // mainFlywheelMotor2.setInverted(true);
    secondaryFlywheelMotor.setInverted(false);
    pidControllerMainFlywheel1 = mainFlyWheelMotor1.getPIDController();
    // pidControllerMainFlywheel2 = mainFlywheelMotor2.getPIDController();
    pidControllerSecondaryFlywheel = secondaryFlywheelMotor.getPIDController();
    pidControllerMainFlywheel1.setP(kPMainFlywheel);
    pidControllerMainFlywheel1.setI(kIMainFlywheel);
    pidControllerMainFlywheel1.setD(kDMainFlywheel);
    pidControllerMainFlywheel1.setOutputRange(kMinOutput, kMaxOutput);
    // pidControllerMainFlywheel2.setP(kPMainFlywheel);
    // pidControllerMainFlywheel2.setI(kPMainFlywheel);
    // pidControllerMainFlywheel2.setD(kDMainFlywheel);
    // pidControllerMainFlywheel2.setOutputRange(kMinOutput, kMaxOutput);
    pidControllerSecondaryFlywheel.setP(kPSecondaryFlywheel);
    pidControllerSecondaryFlywheel.setI(kISecondaryFlywheel);
    pidControllerSecondaryFlywheel.setD(kDSecondaryFlywheel);
    pidControllerSecondaryFlywheel.setOutputRange(kMinOutput, kMaxOutput);
  }

  public void setShooterPID(double rpmMainFlywheel, double rpmSecondaryFlywheel) {
    pidControllerMainFlywheel1.setReference(rpmMainFlywheel, CANSparkMax.ControlType.kVelocity);
    // pidControllerMainFlywheel2.setReference(rpmMainFlywheel, CANSparkMax.ControlType.kVelocity);
    pidControllerSecondaryFlywheel.setReference(
        rpmSecondaryFlywheel, CANSparkMax.ControlType.kVelocity);
  }

  public double getMainFlywheel1Velocity() {
    return mainFlyWheelMotor1.getEncoder().getVelocity();
  }

  public double getMainFlywheel2Velocity() {
    return mainFlywheelMotor2.getEncoder().getVelocity();
  }

  public double getSecondaryFlywheelVelocity() {
    return secondaryFlywheelMotor.getEncoder().getVelocity();
  }

  public void setMainFlywheel1(double speed) {
    mainFlyWheelMotor1.set(speed);
  }

  public void setMainFlywheel2(double speed) {
    mainFlywheelMotor2.set(speed);
  }

  public void setSecondaryFlywheel(double speed) {
    secondaryFlywheelMotor.set(speed);
  }
}
