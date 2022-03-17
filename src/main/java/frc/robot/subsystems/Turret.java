package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

  private final CANSparkMax turretMotor, hoodMotor;

  public Turret() {
    turretMotor = new CANSparkMax(Constants.turretMotor, MotorType.kBrushless);
    hoodMotor = new CANSparkMax(Constants.hoodMotor, MotorType.kBrushless);
    resetTurretEncoder();
    resetHoodEncoder();
    hoodMotor.setSoftLimit(SoftLimitDirection.kForward, 55);
    hoodMotor.setSoftLimit(SoftLimitDirection.kReverse, 0);
    hoodMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    hoodMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
    // turretMotor.setSoftLimit(SoftLimitDirection.kForward, 14);
    // turretMotor.setSoftLimit(SoftLimitDirection.kReverse, -14);
    // turretMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    // turretMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
  }

  public void setTurret(double speed) {
    turretMotor.set(speed);
  }

  public void setHood(double speed) {
    hoodMotor.set(speed);
  }

  public double getTurretPosition() {
    return turretMotor.getEncoder().getPosition();
  }

  public double getHoodPosition() {
    return hoodMotor.getEncoder().getPosition();
  }

  public void resetTurretEncoder() {
    turretMotor.getEncoder().setPosition(0);
  }

  public void resetHoodEncoder() {
    hoodMotor.getEncoder().setPosition(0);
  }
}
