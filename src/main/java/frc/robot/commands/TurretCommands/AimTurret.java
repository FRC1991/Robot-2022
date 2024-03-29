package frc.robot.commands.TurretCommands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;
import java.util.function.Supplier;

public class AimTurret extends CommandBase {

  private Turret turret;
  private final double steeringScale = 1.7;
  private double steeringAdjust = 0;
  private Supplier<Double> xSteer;

  public AimTurret(Supplier<Double> targetXSteerSupplier) {
    turret = RobotContainer.mTurret;
    addRequirements(turret);
    xSteer = targetXSteerSupplier;
  }

  @Override
  public void initialize() {
    NetworkTableInstance.getDefault()
        .getTable("Shuffleboard")
        .getSubTable("Main")
        .getEntry("IsTargetFound")
        .setBoolean(false);
  }

  @Override
  public void execute() {
    // if target is off by more than 1 degree, adjust steering, otherwise, do nothing
    // note that this is a very rough approximation, and may need to be adjusted
    // multiplying by 0.015 to normalize the degree value to between -1 and 1
    if (xSteer.get() > 0.2) {
      steeringAdjust = xSteer.get() * 0.015;
      steeringAdjust = steeringAdjust * steeringScale;
    } else if (xSteer.get() < -0.2) {
      steeringAdjust = xSteer.get() * 0.015;
      steeringAdjust = steeringAdjust * steeringScale;
    } else {
      steeringAdjust = 0;
      NetworkTableInstance.getDefault()
          .getTable("Shuffleboard")
          .getSubTable("Main")
          .getEntry("IsTargetFound")
          .setBoolean(true);
    }
    // System.out.printf("StrAdj: " + steeringAdjust + "\nxSteer: " + xSteer + "\n\n");
    turret.setTurret(steeringAdjust);
  }

  @Override
  public boolean isFinished() {
    // if target is within 0.2 degree, finish command
    return Math.abs(xSteer.get()) < 0.2;
  }

  @Override
  public void end(boolean interrupted) {
    // let Aux know target is aligned
    // oInterface.singleVibrateAux();
  }
}
