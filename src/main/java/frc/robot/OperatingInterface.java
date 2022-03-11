package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OperatingInterface {

  public XboxController driveJoystick, auxJoystick;
  private JoystickButton aButtonDrive,
      bButtonDrive,
      xButtonDrive,
      yButtonDrive,
      leftBumperDrive,
      rightBumperDrive,
      selectButtonDrive,
      startButtonDrive,
      aButtonAux,
      bButtonAux,
      xButtonAux,
      yButtonAux,
      leftBumperAux,
      rightBumperAux,
      selectButtonAux,
      startButtonAux;

  public OperatingInterface() {
    driveJoystick = new XboxController(0);
    auxJoystick = new XboxController(1);
    aButtonDrive = new JoystickButton(driveJoystick, 1);
    bButtonDrive = new JoystickButton(driveJoystick, 2);
    xButtonDrive = new JoystickButton(driveJoystick, 3);
    yButtonDrive = new JoystickButton(driveJoystick, 4);
    leftBumperDrive = new JoystickButton(driveJoystick, 5);
    rightBumperDrive = new JoystickButton(driveJoystick, 6);
    selectButtonDrive = new JoystickButton(driveJoystick, 7);
    startButtonDrive = new JoystickButton(driveJoystick, 8);

    aButtonAux = new JoystickButton(auxJoystick, 1);
    bButtonAux = new JoystickButton(auxJoystick, 2);
    xButtonAux = new JoystickButton(auxJoystick, 3);
    yButtonAux = new JoystickButton(auxJoystick, 4);
    leftBumperAux = new JoystickButton(auxJoystick, 5);
    rightBumperAux = new JoystickButton(auxJoystick, 6);
    selectButtonAux = new JoystickButton(auxJoystick, 7);
    startButtonAux = new JoystickButton(auxJoystick, 8);
  }

  public double getDriveLeftYAxis() {
    return driveJoystick.getRawAxis(1);
  }

  public double getDriveLeftXAxis() {
    return driveJoystick.getRawAxis(0);
  }

  public double getDriveRightYAxis() {
    return driveJoystick.getRawAxis(5);
  }

  public double getDriveRightXAxis() {
    return driveJoystick.getRawAxis(4);
  }

  public double getDriveRightTriggerAxis() {
    return driveJoystick.getRawAxis(3);
  }

  public double getDriveLeftTriggerAxis() {
    return driveJoystick.getRawAxis(2);
  }

  public XboxController getDriveJoystick() {
    return driveJoystick;
  }

  public JoystickButton getDriveAButton() {
    return aButtonDrive;
  }

  public JoystickButton getDriveBButton() {
    return bButtonDrive;
  }

  public JoystickButton getDriveXButton() {
    return xButtonDrive;
  }

  public JoystickButton getDriveYButton() {
    return yButtonDrive;
  }

  public JoystickButton getDriveLeftBumper() {
    return leftBumperDrive;
  }

  public JoystickButton getDriveRightBumper() {
    return rightBumperDrive;
  }

  public JoystickButton getDriveSelectButton() {
    return selectButtonDrive;
  }

  public JoystickButton getDriveStartButton() {
    return startButtonDrive;
  }

  public void doubleVibrateDrive() {
    for(int i=0; i<2; i++){
      driveJoystick.setRumble(RumbleType.kLeftRumble, 1);
      Timer.delay(0.2);
      driveJoystick.setRumble(RumbleType.kLeftRumble, 0);
      Timer.delay(0.1);
    }
  }

  public void singleVibrateDrive() {
    driveJoystick.setRumble(RumbleType.kLeftRumble, 1);
    Timer.delay(0.25);
    driveJoystick.setRumble(RumbleType.kLeftRumble, 0);
  }

  public JoystickButton getAuxAButton() {
    return aButtonAux;
  }

  public JoystickButton getAuxBButton() {
    return bButtonAux;
  }

  public JoystickButton getAuxXButton() {
    return xButtonAux;
  }

  public JoystickButton getAuxYButton() {
    return yButtonAux;
  }

  public JoystickButton getAuxLeftBumper() {
    return leftBumperAux;
  }

  public JoystickButton getAuxRightBumper() {
    return rightBumperAux;
  }

  public JoystickButton getAuxSelectButton() {
    return selectButtonAux;
  }

  public JoystickButton getAuxStartButton() {
    return startButtonAux;
  }

  public void doubleVibrateAux() {
    auxJoystick.setRumble(RumbleType.kLeftRumble, 1);
    Timer.delay(0.2);
    auxJoystick.setRumble(RumbleType.kLeftRumble, 0);
    Timer.delay(0.1);
  }
}
