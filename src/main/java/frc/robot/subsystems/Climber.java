package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private CANSparkMax climberMotor;

  public Climber() {
    climberMotor = new CANSparkMax(Constants.elevatorRaiseMotor, MotorType.kBrushless);
    // transverseMotor = new CANSparkMax(Constants.climberTransverseMotor, MotorType.kBrushless);
  }

  public void setClimberMotor(double speed) {
    climberMotor.set(speed);
  }

  // public void setTransverseMotor(double speed) {
  //   transverseMotor.set(speed);
  // }
}
