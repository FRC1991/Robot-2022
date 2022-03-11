package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
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
  
      private Button dPadUpDriveButton,
      dPadDownDriveButton,
      dPadLeftDriveButton,
      dPadRightDriveButton,
      dPadUpAuxButton,
      dPadDownAuxButton,
      dPadLeftAuxButton,
      dPadRightAuxButton,
      rightStickUpDriveButton,
      rightStickDownDriveButton,
      rightStickLeftDriveButton,
      rightStickRightDriveButton,
      rightStickUpAuxButton,
      rightStickDownAuxButton,
      rightStickLeftAuxButton,
      rightStickRightAuxButton,
      leftStickUpDriveButton,
      leftStickDownDriveButton,
      leftStickLeftDriveButton,
      leftStickRightDriveButton,
      leftStickUpAuxButton,
      leftStickDownAuxButton,
      leftStickLeftAuxButton,
      leftStickRightAuxButton,
      leftTriggerDriveButton,
      rightTriggerDriveButton,
      leftTriggerAuxButton,
      rightTriggerAuxButton;

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
    dPadUpDriveButton = new DPadButton(driveJoystick, DPadButton.Direction.UP);
    dPadDownDriveButton = new DPadButton(driveJoystick, DPadButton.Direction.DOWN);
    dPadLeftDriveButton = new DPadButton(driveJoystick, DPadButton.Direction.LEFT);
    dPadRightDriveButton = new DPadButton(driveJoystick, DPadButton.Direction.RIGHT);
    rightStickUpDriveButton = new JoystickAnalogButton(driveJoystick, 5);
    rightStickDownDriveButton = new JoystickAnalogButton(driveJoystick, 5);
    rightStickLeftDriveButton = new JoystickAnalogButton(driveJoystick, 4);
    rightStickRightDriveButton = new JoystickAnalogButton(driveJoystick, 4);
    leftStickUpDriveButton = new JoystickAnalogButton(driveJoystick, 1);
    leftStickDownDriveButton = new JoystickAnalogButton(driveJoystick, 1);
    leftStickLeftDriveButton = new JoystickAnalogButton(driveJoystick, 0);
    leftStickRightDriveButton = new JoystickAnalogButton(driveJoystick, 0);
    leftTriggerDriveButton = new JoystickAnalogButton(driveJoystick, 2);
    rightTriggerDriveButton = new JoystickAnalogButton(driveJoystick, 3);

    aButtonAux = new JoystickButton(auxJoystick, 1);
    bButtonAux = new JoystickButton(auxJoystick, 2);
    xButtonAux = new JoystickButton(auxJoystick, 3);
    yButtonAux = new JoystickButton(auxJoystick, 4);
    leftBumperAux = new JoystickButton(auxJoystick, 5);
    rightBumperAux = new JoystickButton(auxJoystick, 6);
    selectButtonAux = new JoystickButton(auxJoystick, 7);
    startButtonAux = new JoystickButton(auxJoystick, 8);
    dPadUpAuxButton = new DPadButton(auxJoystick, DPadButton.Direction.UP);
    dPadDownAuxButton = new DPadButton(auxJoystick, DPadButton.Direction.DOWN);
    dPadLeftAuxButton = new DPadButton(auxJoystick, DPadButton.Direction.LEFT);
    dPadRightAuxButton = new DPadButton(auxJoystick, DPadButton.Direction.RIGHT);
    rightStickUpAuxButton = new JoystickAnalogButton(auxJoystick, 5);
    rightStickDownAuxButton = new JoystickAnalogButton(auxJoystick, 5);
    rightStickLeftAuxButton = new JoystickAnalogButton(auxJoystick, 4);
    rightStickRightAuxButton = new JoystickAnalogButton(auxJoystick, 4);
    leftStickUpAuxButton = new JoystickAnalogButton(auxJoystick, 1);
    leftStickDownAuxButton = new JoystickAnalogButton(auxJoystick, 1);
    leftStickLeftAuxButton = new JoystickAnalogButton(auxJoystick, 0);
    leftStickRightAuxButton = new JoystickAnalogButton(auxJoystick, 0);
    leftTriggerAuxButton = new JoystickAnalogButton(auxJoystick, 2);
    rightTriggerAuxButton = new JoystickAnalogButton(auxJoystick, 3);
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

  public Button getDriveDPadUp() {
    return dPadUpDriveButton;
  }

  public Button getDriveDPadDown() {
    return dPadDownDriveButton;
  }

  public Button getDriveDPadLeft() {
    return dPadLeftDriveButton;
  }

  public Button getDriveDPadRight() {
    return dPadRightDriveButton;
  }

  public Button getDriveRightStickUpButton() {
    return rightStickUpDriveButton;
  }

  public Button getDriveRightStickDownButton() {
    return rightStickDownDriveButton;
  }

  public Button getDriveRightStickLeftButton(){
    return rightStickLeftDriveButton;
  }

  public Button getDriveRightStickRightButton(){
    return rightStickRightDriveButton;
  }

  public Button getDriveLeftStickUpButton() {
    return leftStickUpDriveButton;
  }

  public Button getDriveLeftStickDownButton() {
    return leftStickDownDriveButton;
  }

  public Button getDriveLeftStickLeftButton(){
    return leftStickLeftDriveButton;
  }

  public Button getDriveLeftStickRightButton(){
    return leftStickRightDriveButton;
  }

  public Button getDriveLeftTriggerButton() {
    return leftTriggerDriveButton;
  }

  public Button getDriveRightTriggerButton() {
    return rightTriggerDriveButton;
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

  public Button getAuxDPadUp() {
    return dPadUpAuxButton;
  }

  public Button getAuxDPadDown() {
    return dPadDownAuxButton;
  }

  public Button getAuxDPadLeft() {
    return dPadLeftAuxButton;
  }

  public Button getAuxDPadRight() {
    return dPadRightAuxButton;
  }

  public Button getAuxRightStickUpButton() {
    return rightStickUpAuxButton;
  }

  public Button getAuxRightStickDownButton() {
    return rightStickDownAuxButton;
  }

  public Button getAuxRightStickLeftButton(){
    return rightStickLeftAuxButton;
  }

  public Button getAuxRightStickRightButton(){
    return rightStickRightAuxButton;
  }

  public Button getAuxLeftStickUpButton() {
    return leftStickUpAuxButton;
  }

  public Button getAuxLeftStickDownButton() {
    return leftStickDownAuxButton;
  }

  public Button getAuxLeftStickLeftButton(){
    return leftStickLeftAuxButton;
  }

  public Button getAuxLeftStickRightButton(){
    return leftStickRightAuxButton;
  }

  public Button getAuxLeftTriggerButton() {
    return leftTriggerAuxButton;
  }

  public Button getAuxRightTriggerButton() {
    return rightTriggerAuxButton;
  }


  public void doubleVibrateAux() {
    auxJoystick.setRumble(RumbleType.kLeftRumble, 1);
    Timer.delay(0.2);
    auxJoystick.setRumble(RumbleType.kLeftRumble, 0);
    Timer.delay(0.1);
  }
}
