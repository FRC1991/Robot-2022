package frc.robot.subsystems;

import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.Pigeon2.AxisDirection;
// import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAlternateEncoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private boolean leftMotorInverted = false;
  private boolean rightMotorInverted = true;
  private DifferentialDrive differentialDrive;
  private double deadband = Constants.globalDeadband;

  private static CANSparkMax leftMotor1,
      leftMotor2,
      leftMotor3,
      rightMotor1,
      rightMotor2,
      rightMotor3;
  private Servo leftServo, rightServo;

  // private final AHRS navx;
  private final Pigeon2 pigeon;

  public Drivetrain() {

    // navx = new AHRS();
    pigeon = new Pigeon2(Constants.pigeonIMU);

    pigeon.configMountPose(AxisDirection.NegativeY, AxisDirection.PositiveZ);
    pigeon.setYaw(0);

    rightServo = new Servo(0);
    leftServo = new Servo(1);

    // define motors with CAN IDs
    leftMotor1 = new CANSparkMax(Constants.leftMotor1, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax(Constants.leftMotor2, MotorType.kBrushless);
    leftMotor3 = new CANSparkMax(Constants.leftMotor3, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(Constants.rightMotor1, MotorType.kBrushless);
    rightMotor2 = new CANSparkMax(Constants.rightMotor2, MotorType.kBrushless);
    rightMotor3 = new CANSparkMax(Constants.rightMotor3, MotorType.kBrushless);

    leftMotor1.setInverted(leftMotorInverted);
    leftMotor2.setInverted(leftMotorInverted);
    leftMotor3.setInverted(leftMotorInverted);
    rightMotor1.setInverted(rightMotorInverted);
    rightMotor2.setInverted(rightMotorInverted);
    rightMotor3.setInverted(rightMotorInverted);

    leftMotor2.follow(leftMotor1);
    leftMotor3.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);
    rightMotor3.follow(rightMotor1);

    differentialDrive = new DifferentialDrive(leftMotor1, rightMotor1);
  }

  public void setDrivetrain(double leftSpeed, double rightSpeed) {
    leftMotor1.set(leftSpeed);
    rightMotor1.set(rightSpeed);
    rightMotor2.set(rightSpeed);
    rightMotor3.set(rightSpeed);
  }

  public void setDrivetrain(double leftSpeed, double rightSpeed, double multiplier) {
    setDrivetrain(leftSpeed * multiplier, rightSpeed * multiplier);
  }

  public void setDrivetrain(
      double leftSpeed, double rightSpeed, double multiplier, boolean isDeadbandEnabled) {
    if (isDeadbandEnabled) {
      if (Math.abs(leftSpeed) > deadband) leftMotor1.set(leftSpeed);
      else leftMotor1.set(0);
      if (Math.abs(rightSpeed) > deadband) rightMotor1.set(rightSpeed);
      else rightMotor1.set(0);
    } else {
      setDrivetrain(leftSpeed, rightSpeed, multiplier);
    }
  }

  public void arcadeDrive(double speed, double rotation) {
    differentialDrive.arcadeDrive(speed, rotation);
  }

  public void setGTADrive(
      double forwardSpeed,
      double backwardSpeed,
      double rotation,
      boolean isQuickTurn,
      double multiplier) {

    forwardSpeed = multiplier * forwardSpeed;
    backwardSpeed = -1 * multiplier * backwardSpeed;
    double netSpeed = forwardSpeed + backwardSpeed;

    if (Math.abs(netSpeed) > deadband * multiplier) {
      differentialDrive.curvatureDrive(netSpeed, multiplier * -rotation, isQuickTurn);
    } else if (Math.abs(netSpeed) > 0.01 * multiplier) {
      setDrivetrain(rotation, rotation, multiplier);
    } else if (isQuickTurn) {
      differentialDrive.curvatureDrive(0, -rotation * multiplier, true);
    } else {
      differentialDrive.curvatureDrive(0, -rotation * multiplier, true);
    }
  }

  public void stopDrivetrain() {
    setDrivetrain(0, 0);
  }

  public double getLeftMotor1Pos() {
    return leftMotor1.getEncoder().getPosition();
  }

  public double getLeftMotor2Pos() {
    return leftMotor2.getEncoder().getPosition();
  }

  public double getRightMotor1Pos() {
    return rightMotor1.getEncoder().getPosition();
  }

  public double getRightMotor2Pos() {
    return rightMotor2.getEncoder().getPosition();
  }

  public void resetEncoders() {
    leftMotor1.getEncoder().setPosition(0);
    leftMotor2.getEncoder().setPosition(0);
    rightMotor1.getEncoder().setPosition(0);
    rightMotor2.getEncoder().setPosition(0);
  }

  public double getYaw() {
    return pigeon.getYaw();
  }

  public double getPitch() {
    return pigeon.getPitch();
  }

  public double getRoll() {
    return pigeon.getRoll();
  }

  public double getDistanceFeet() {
    double averageDistanceInRotations =
        (leftMotor1.getEncoder().getPosition()
                + leftMotor2.getEncoder().getPosition()
                + rightMotor1.getEncoder().getPosition()
                + rightMotor2.getEncoder().getPosition())
            / 4.0;
    double averageDistanceInRotationsOfOutputShaft = averageDistanceInRotations / 14.17;
    return Math.PI
        * averageDistanceInRotationsOfOutputShaft; // 6 in wheels, so circumfrence in ft is pi
  }

  public void setServos(double leftServoPosition, double rightServoPosition) {
    leftServo.set(leftServoPosition);
    rightServo.set(rightServoPosition);
  }

  public double getTransverseShaftEncoderPosition() {
    return rightMotor1
        .getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 8192)
        .getPosition();
  }

  public CANSparkMax getLeftMotor1() {
    return leftMotor1;
  }

  public CANSparkMax getLeftMotor2() {
    return leftMotor2;
  }

  public CANSparkMax getLeftMotor3() {
    return leftMotor3;
  }

  public CANSparkMax getRightMotor2() {
    return rightMotor2;
  }

  public CANSparkMax getRightMotor3() {
    return rightMotor3;
  }

  public CANSparkMax getRightMotor1() {
    return rightMotor1;
  }
}
