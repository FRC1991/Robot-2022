package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

    private boolean leftMotor1Inverted = false;
    private boolean leftMotor2Inverted = false;
    private boolean rightMotor1Inverted = true;
    private boolean rightMotor2Inverted = true;
    private DifferentialDrive differentialDrive;
    private double deadband = Constants.globalDeadband;

    private final CANSparkMax leftMotor1, leftMotor2, rightMotor1, rightMotor2;

    public Drivetrain() {
        // define motors with CAN IDs
        leftMotor1 = new CANSparkMax(Constants.leftMotor1, MotorType.kBrushless);
        leftMotor2 = new CANSparkMax(Constants.leftMotor2, MotorType.kBrushless);
        rightMotor1 = new CANSparkMax(Constants.rightMotor1, MotorType.kBrushless);
        rightMotor2 = new CANSparkMax(Constants.rightMotor2, MotorType.kBrushless);
        
        leftMotor1.setInverted(leftMotor1Inverted);
        leftMotor2.setInverted(leftMotor2Inverted);
        rightMotor1.setInverted(rightMotor1Inverted);
        rightMotor2.setInverted(rightMotor2Inverted);

        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);

        differentialDrive = new DifferentialDrive(leftMotor1, rightMotor1);
    }

    public void setDrivetrain(double leftSpeed, double rightSpeed){
        leftMotor1.set(leftSpeed);
        rightMotor1.set(rightSpeed);
    }

    public void setDrivetrain(double leftSpeed, double rightSpeed, double multiplier) {
        setDrivetrain(leftSpeed*multiplier, rightSpeed*multiplier);
    }

    public void setDrivetrain(double leftSpeed, double rightSpeed, double multiplier, boolean isDeadbandEnabled){
        if(isDeadbandEnabled){
            if(Math.abs(leftSpeed) > deadband)
                leftMotor1.set(leftSpeed);
            else 
                leftMotor1.set(0);
            if(Math.abs(rightSpeed) > deadband)
                rightMotor1.set(rightSpeed);
            else
                rightMotor1.set(0);
        }
        else{
            setDrivetrain(leftSpeed, rightSpeed, multiplier);
        }
    }

    public void arcadeDrive(double speed, double rotation){
        differentialDrive.arcadeDrive(speed, rotation);
    }

    public void setGTADrive(double forwardSpeed, double backwardSpeed, double rotation, boolean isQuickTurn, double multiplier){
        forwardSpeed = multiplier*forwardSpeed;
        backwardSpeed = -1*multiplier*backwardSpeed;
        double netSpeed = forwardSpeed + backwardSpeed;
        if(Math.abs(netSpeed)>deadband*multiplier){
            differentialDrive.curvatureDrive(netSpeed, multiplier*rotation, isQuickTurn);
        }
        else if(Math.abs(netSpeed)>0.01){
            setDrivetrain(-rotation, rotation, multiplier);
        }
        else{
            setDrivetrain(rotation, -rotation, multiplier);
        }
    }

    public void stopDrivetrain(){
        setDrivetrain(0, 0);
    }

    public double getLeftMotor1Pos(){
        return leftMotor1.getEncoder().getPosition();
    }

    // get encoder position of left motor 2
    public double getLeftMotor2Pos(){
        return leftMotor2.getEncoder().getPosition();
    }
    
    public double getRightMotor1Pos(){
        return rightMotor1.getEncoder().getPosition();
    }

    // get encoder position of right motor 2
    public double getRightMotor2Pos(){
        return rightMotor2.getEncoder().getPosition();
    }

    public void resetEncoders(){
        leftMotor1.getEncoder().setPosition(0);
        leftMotor2.getEncoder().setPosition(0);
        rightMotor1.getEncoder().setPosition(0);
        rightMotor2.getEncoder().setPosition(0);
    }

    
}
