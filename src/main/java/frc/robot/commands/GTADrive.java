package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class GTADrive extends CommandBase {

    private Drivetrain drivetrain;
    private OperatingInterface oInterface;
    
    public GTADrive() {
        drivetrain = RobotContainer.mdDrivetrain;
        oInterface = RobotContainer.oInterface;
        addRequirements(drivetrain);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        // drivetrain.setGTADrive(oInterface.getRightTriggerAxis()+(-1*oInterface.getLeftTriggerAxis()), oInterface.getLeftXAxis(), false);
        drivetrain.setGTADriveV2(oInterface.getRightTriggerAxis(), oInterface.getLeftTriggerAxis(), oInterface.getLeftXAxis(), oInterface.getRightBumper());
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}
