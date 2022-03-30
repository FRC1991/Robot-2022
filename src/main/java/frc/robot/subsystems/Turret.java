package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

  private final CANSparkMax turretMotor;

  public Turret() {
    turretMotor = new CANSparkMax(Constants.turretMotor, MotorType.kBrushless);
    resetTurretEncoder();
    // turretMotor.setSoftLimit(SoftLimitDirection.kForward, 14);
    // turretMotor.setSoftLimit(SoftLimitDirection.kReverse, -14);
    // turretMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
    // turretMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
  }

  public void setTurret(double speed) {
    turretMotor.set(speed);
  }

  public double getTurretPosition() {
    return turretMotor.getEncoder().getPosition();
  }

  public void resetTurretEncoder() {
    turretMotor.getEncoder().setPosition(0);
  }

  public void stopTurret() {
    turretMotor.set(0);
  }
}
