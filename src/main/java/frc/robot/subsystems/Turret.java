package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase{

    private final CANSparkMax turretMotor;

    public Turret(){
        turretMotor = new CANSparkMax(Constants.turretMotor, MotorType.kBrushless);
    }

    public void setTurret(double speed) {
        // TODO: add turret turn degrees command
        turretMotor.set(speed);
    }
}
