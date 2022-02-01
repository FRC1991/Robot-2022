package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OperatingInterface {
    
    public XboxController driveJoystick;
    private JoystickButton aButton, bButton, xButton, yButton, leftBumper, rightBumper, selectButton, startButton;

    public OperatingInterface(){
        driveJoystick = new XboxController(0);
        aButton = new JoystickButton(driveJoystick, 1);
        bButton = new JoystickButton(driveJoystick, 2);
        xButton = new JoystickButton(driveJoystick, 3);
        yButton = new JoystickButton(driveJoystick, 4);
        leftBumper = new JoystickButton(driveJoystick, 5);
        rightBumper = new JoystickButton(driveJoystick, 6);
        selectButton = new JoystickButton(driveJoystick, 7);
        startButton = new JoystickButton(driveJoystick, 8);
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

    public XboxController getDriveJoystick() {
        return driveJoystick;
    }

    public JoystickButton getAButton(){
        return aButton;
    }


    public JoystickButton getBButton() {
        return bButton;
    }

    public JoystickButton getXButton() {
        return xButton;
    }

    public JoystickButton getYButton() {
        return yButton;
    }

    public JoystickButton getLeftBumper() {
        return leftBumper;
    }

    public JoystickButton getRightBumper() {
        return rightBumper;
    }

    public JoystickButton getSelectButton() {
        return selectButton;
    }

    public JoystickButton getStartButton() {
        return startButton;
    }

    public void doubleVibrate(){
        driveJoystick.setRumble(RumbleType.kLeftRumble, 1);
        Timer.delay(0.2);
        driveJoystick.setRumble(RumbleType.kLeftRumble, 0);
        Timer.delay(0.1);
    }
}
