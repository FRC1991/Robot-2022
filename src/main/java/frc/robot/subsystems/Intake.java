package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    
  public static DigitalInput proximitySensor;
  // TODO: add motors
    
    public Intake(){
        proximitySensor = new DigitalInput(Constants.proximitySensorDIOIndex);
    }

    public boolean isBallIn(){
        return proximitySensor.get();
    }

}
