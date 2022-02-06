// TODO: Finish this

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LiDAR;

public class GetDistanceLiDAR extends CommandBase {

  private LiDAR mLiDAR;
  private NetworkTableEntry distanceEntry;

  public GetDistanceLiDAR(LiDAR lidar) {
    mLiDAR = lidar;
    addRequirements(mLiDAR);
  }

  @Override
  public void initialize() {
    // init shuffleboard entry
    distanceEntry = Shuffleboard.getTab("Main").add("LiDAR Distance (cm)", 0).getEntry();
  }

  @Override
  public void execute() {
    // update entry with distance
    distanceEntry.setNumber(mLiDAR.getDistance());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {}
}
