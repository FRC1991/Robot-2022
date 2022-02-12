package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final CANSparkMax turretMotor, flywheelMotor1, flywheelMotor2;
  private SparkMaxPIDController pidControllerFlywheel1, pidControllerFlywheel2;
  public double kPFlywheel1,
      kIFlywheel1,
      kdFlywheel1,
      kPFlywheel2,
      kIFlywheel2,
      kdFlywheel2,
      kMaxOutput,
      kMinOutput;

  public Shooter() {
    turretMotor = new CANSparkMax(Constants.turretMotor, MotorType.kBrushless);
    flywheelMotor1 = new CANSparkMax(15, MotorType.kBrushless);
    flywheelMotor2 = new CANSparkMax(13, MotorType.kBrushless);
    flywheelMotor1.setInverted(true);
    flywheelMotor2.setInverted(false);
    pidControllerFlywheel1 = flywheelMotor1.getPIDController();
    pidControllerFlywheel2 = flywheelMotor2.getPIDController();
    kPFlywheel1 = 0.00026666;
    kIFlywheel1 = 0.000000632;
    kdFlywheel1 = 0.00004;
    kPFlywheel2 = 0.000013333;
    kIFlywheel2 = 0.000000266;
    kdFlywheel2 = 0;
    kMaxOutput = 1;
    kMinOutput = -1;
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

  public void setTurret(double speed) {
    // TODO: add turret turn degrees command
    turretMotor.set(speed);
  }

  public void setFlywheel1(double speed) {
    flywheelMotor1.set(speed);
  }

  public void setFlywheel2(double speed) {
    flywheelMotor2.set(speed);
  }
}
