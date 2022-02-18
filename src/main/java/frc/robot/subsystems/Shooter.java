package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final CANSparkMax flywheelMotor1, flywheelMotor2;
  private SparkMaxPIDController pidControllerFlywheel1, pidControllerFlywheel2;
  private final double kPFlywheel1 = Constants.kPFlywheel1;
  private final double kIFlywheel1 = Constants.kIFlywheel1;
  private final double kdFlywheel1 = Constants.kdFlywheel1;
  private final double kPFlywheel2 = Constants.kdFlywheel2;
  private final double kIFlywheel2 = Constants.kIFlywheel2;
  private final double kdFlywheel2 = Constants.kdFlywheel2;
  private final double kMaxOutput = Constants.kMaxOutput;
  private final double kMinOutput = Constants.kMinOutput;

  public Shooter() {
    flywheelMotor1 = new CANSparkMax(15, MotorType.kBrushless);
    flywheelMotor2 = new CANSparkMax(13, MotorType.kBrushless);
    flywheelMotor1.setInverted(true);
    flywheelMotor2.setInverted(false);
    pidControllerFlywheel1 = flywheelMotor1.getPIDController();
    pidControllerFlywheel2 = flywheelMotor2.getPIDController();
    pidControllerFlywheel1.setP(kPFlywheel1);
    pidControllerFlywheel1.setI(kIFlywheel1);
    pidControllerFlywheel1.setD(kdFlywheel1);
    pidControllerFlywheel1.setOutputRange(kMinOutput, kMaxOutput);
    pidControllerFlywheel2.setP(kPFlywheel2);
    pidControllerFlywheel2.setI(kIFlywheel2);
    pidControllerFlywheel2.setD(kdFlywheel2);
    pidControllerFlywheel2.setOutputRange(kMinOutput, kMaxOutput);
  }

  public void setShooterPID(double rpmFlywheel1, double rpmFlywheel2) {
    pidControllerFlywheel1.setReference(rpmFlywheel1, CANSparkMax.ControlType.kVelocity);
    pidControllerFlywheel2.setReference(rpmFlywheel2, CANSparkMax.ControlType.kVelocity);
  }

  public double getVelocity() {
    return flywheelMotor1.getEncoder().getVelocity();
  }

  public double getVelovityFlywheel2() {
    return flywheelMotor2.getEncoder().getVelocity();
  }

  public void setFlywheel1(double speed) {
    flywheelMotor1.set(speed);
  }

  public void setFlywheel2(double speed) {
    flywheelMotor2.set(speed);
  }
}
