package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {
    
    ColorSensorV3 sensor;

    public ColorSensor(){
    sensor = new ColorSensorV3(I2C.Port.kOnboard);
    }

    // TODO: Normallize the colors to be between 0 and 1
    // 20 bit register so
    // Min of 0 and max of 1048575 (maybe, not sure)

    public int getRed(){
        return sensor.getRed();
    }

    public int getGreen(){
        return sensor.getGreen();
    }

    public int getBlue(){
        return sensor.getBlue();
    }

    // TODO: Get value in meters instead of unitless value with a max of 2047
    // 11 bit register so
    // value goes from 0 to 2047 with 0 being closest and 2047 being furthest
    public int getProximity(){
        return sensor.getProximity();
    }
}
