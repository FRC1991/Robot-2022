package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LiDAR extends SubsystemBase {

  I2C lidarSensor;

  public LiDAR() {
    lidarSensor = new I2C(I2C.Port.kMXP, 0x62);
  }

  public double getDistance() {
    byte[] buffer;
    buffer = new byte[2];
    lidarSensor.write(0x00, 0x04);
    Timer.delay(0.01);
    lidarSensor.read(0x8f, 2, buffer);
    int distanceInCm =
        (int) (Integer.toUnsignedLong(buffer[0] << 8) + Byte.toUnsignedInt(buffer[1]));
    return distanceInCm / 2.54; // convert to inches
  }
}
