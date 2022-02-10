package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final CANSparkMax turretMotor, flywheelMotor;
  private SparkMaxPIDController pidController;
  public double kP,
      kI,
      kD,
      kIz,
      kFF,
      kMaxOutput,
      kMinOutput,
      maxRPM,
      maxVel,
      minVel,
      maxAcc,
      allowedErr;

  public Shooter() {
    turretMotor = new CANSparkMax(Constants.turretMotor, MotorType.kBrushless);
    flywheelMotor = new CANSparkMax(15, MotorType.kBrushless);
    flywheelMotor.setInverted(true);
    pidController = flywheelMotor.getPIDController();
    kP = 0.00026666;
    kI = 0.000000632;
    kD = 0.000000;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1;
    kMinOutput = -1;
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);
  }

  public void setTurretPID(double rpm) {
    pidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
  }

  public double getVelocity() {
    return flywheelMotor.getEncoder().getVelocity();
  }

  public void setTurret(double speed) {
    turretMotor.set(speed);
  }

  public void setFlywheel(double speed) {
    flywheelMotor.set(speed);
  }
}
