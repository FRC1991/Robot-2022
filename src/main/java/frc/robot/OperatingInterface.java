package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatingInterface {
    
    Joystick driveJoystick;

    

    public OperatingInterface(){
        driveJoystick = new Joystick(0);

    }

    public double getLeftYAxis(){
        return driveJoystick.getRawAxis(1);
    }

    public double getLeftXAxis(){
        return driveJoystick.getRawAxis(0);
    }

    public double getRightYAxis(){
        return driveJoystick.getRawAxis(5);
    }

    public double getRightXAxis(){
        return driveJoystick.getRawAxis(4);
    }

    public double getRightTriggerAxis(){
        return driveJoystick.getRawAxis(3);
    }

    public double getLeftTriggerAxis(){
        return driveJoystick.getRawAxis(2);
    }

    public boolean getRightBumper(){
        return driveJoystick.getRawButton(5);
    }

}
