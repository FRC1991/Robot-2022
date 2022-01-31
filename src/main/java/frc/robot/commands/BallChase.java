package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class BallChase extends CommandBase {
    
    private Drivetrain drivetrain;
    private OperatingInterface oInterface;
    private Supplier<Double> xSteer;
    
    public BallChase(Supplier<Double> thingy) {
        drivetrain = RobotContainer.mDrivetrain;
        oInterface = RobotContainer.oInterface;
        addRequirements(drivetrain);
        xSteer = thingy;
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        // drivetrain.setGTADrive(oInterface.getRightTriggerAxis()+(-1*oInterface.getLeftTriggerAxis()), oInterface.getLeftXAxis(), false);
        // drivetrain.setGTADriveV2(0.5, 0, xSteer.get()*2, false);
        drivetrain.arcadeDrive(0.65, xSteer.get()*2);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
