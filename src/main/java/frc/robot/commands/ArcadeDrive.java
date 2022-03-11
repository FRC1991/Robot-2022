// forward/back and left/right is arcade dive
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OperatingInterface;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {

  private Drivetrain drivetrain;
  private OperatingInterface oInterface;

  public ArcadeDrive() {
    drivetrain = RobotContainer.mDrivetrain;
    oInterface = RobotContainer.oInterface;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  public void execute() {
    drivetrain.arcadeDrive(oInterface.getDriveLeftXAxis(), oInterface.getDriveRightYAxis());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0, 0);
    super.end(interrupted);
  }
}
