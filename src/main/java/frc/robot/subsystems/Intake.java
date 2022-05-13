package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  public static DigitalInput proximitySensorInner, proximitySensorOuter;
  private final CANSparkMax intakeMotor2;

  public Intake() {
    proximitySensorInner = new DigitalInput(Constants.proximitySensorInnerDIOIndex);
    proximitySensorOuter = new DigitalInput(Constants.proximitySensorOuterDIOIndex);
    // intakeMotor1 = new CANSparkMax(Constants.intakeMotor1, MotorType.kBrushless);
    intakeMotor2 = new CANSparkMax(Constants.intakeMotor2, MotorType.kBrushless);
    // intakeMotor1.setInverted(true);
  }

  // public void setIntakeMotor1(double speed) {
  //   intakeMotor1.set(speed);
  // }

  public void setIntakeMotor2(double speed) {
    intakeMotor2.set(speed);
  }

  public boolean isBallPresentInner() {
    return proximitySensorInner.get();
  }

  public boolean isBallPresentOuter() {
    return proximitySensorOuter.get();
  }
}
