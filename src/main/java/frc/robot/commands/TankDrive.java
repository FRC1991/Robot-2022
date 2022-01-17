package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase {
    
    private Drivetrain drivetrain;
    private OperatingInterface oInterface;

    public TankDrive() {
        addRequirements(RobotContainer.mDrivetrain);
        drivetrain = RobotContainer.mDrivetrain;
        oInterface = RobotContainer.oInterface;
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.setDrivetrain(oInterface.getLeftYAxis(), oInterface.getRightYAxis(), 0.60, true);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

}
