//TODO: Finish this

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LiDAR;

public class GetDistanceLiDAR extends CommandBase {

    private LiDAR mLiDAR;

    public GetDistanceLiDAR(LiDAR lidar) {
        mLiDAR = lidar;
        addRequirements(mLiDAR);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // System.out.println(mLiDAR.getDistance());
        SmartDashboard.putNumber("LiDAR Distance (cm)", mLiDAR.getDistance());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }

    
    
}
