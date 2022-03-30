package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  public static DigitalInput proximitySensor;
  private final CANSparkMax intakeMotor1, intakeMotor2;

  public Intake() {
    proximitySensor = new DigitalInput(Constants.proximitySensorDIOIndex);
    intakeMotor1 = new CANSparkMax(Constants.intakeMotor1, MotorType.kBrushless);
    intakeMotor2 = new CANSparkMax(Constants.intakeMotor2, MotorType.kBrushless);
    intakeMotor1.setInverted(true);
  }

  public void setIntakeMotor1(double speed) {
    intakeMotor1.set(speed);
  }

  public void setIntakeMotor2(double speed) {
    intakeMotor2.set(speed);
  }

  public boolean isBallIn() {
    return proximitySensor.get();
  }
}
