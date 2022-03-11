package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private boolean leftMotorInverted = true;
  private boolean rightMotorInverted = false;
  private DifferentialDrive differentialDrive;
  private double deadband = Constants.globalDeadband;

  private final CANSparkMax leftMotor1,
      leftMotor2,
      leftMotor3,
      rightMotor1,
      rightMotor2,
      rightMotor3;

  private Servo leftServo, rightServo;

  private final AHRS navx;

  public Drivetrain() {

    navx = new AHRS();

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
      differentialDrive.curvatureDrive(netSpeed, multiplier * rotation, isQuickTurn);
    } else if (Math.abs(netSpeed) > 0.01 * multiplier) {
      setDrivetrain(-rotation, rotation, multiplier);
    } else if (isQuickTurn) {
      differentialDrive.curvatureDrive(0, rotation * multiplier, true);
    } else {
      differentialDrive.curvatureDrive(0, rotation * multiplier * 0.5, true);
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
    return navx.getYaw();
  }

  public double getPitch() {
    return navx.getPitch();
  }

  public double getRoll() {
    return navx.getRoll();
  }

  public double getHeading() {
    return navx.getFusedHeading();
  }

  public double getAngle() {
    return navx.getAngle();
  }

  public double getDisplacementX() {
    return navx.getDisplacementX();
  }

  public double getDisplacementY() {
    return navx.getDisplacementY();
  }

  public double getDisplacementZ() {
    return navx.getDisplacementZ();
  }

  public double getVelocityX() {
    return navx.getVelocityX();
  }

  public double getVelocityY() {
    return navx.getVelocityY();
  }

  public double getVelocityZ() {
    return navx.getVelocityZ();
  }

  public double getAccelerationX() {
    return navx.getWorldLinearAccelX();
  }

  public double getAccelerationY() {
    return navx.getWorldLinearAccelY();
  }

  public double getAccelerationZ() {
    return navx.getWorldLinearAccelZ();
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

  public void setServos(double leftServoPosition, double rightServoPosition){
      leftServo.set(leftServoPosition);
      rightServo.set(rightServoPosition);
  }

  public double getTransverseShaftEncoderPosition(){
    return rightMotor1.getAlternateEncoder(SparkMaxAlternateEncoder.Type.kQuadrature, 8192).getPosition();
  }

  @Override
  public void periodic() {
    // TODO Gyro Values Update
    super.periodic();
  }
}
