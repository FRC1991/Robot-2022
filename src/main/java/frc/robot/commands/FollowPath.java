package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveDistance;

public class FollowPath extends SequentialCommandGroup {
   public FollowPath(){
       addCommands(
           new DriveDistance(3, 0.5),
            new RotateGyro(-90, 0.5),
            new DriveDistance(3, 0.5),
            new RotateGyro(-90, 0.5),
            new DriveDistance(3, 0.5),
            new RotateGyro(90, 0.5),
            new DriveDistance(3, 0.5),
            new RotateGyro(90, 0.5),
            new DriveDistance(3, 0.5)
       );
   }

}
