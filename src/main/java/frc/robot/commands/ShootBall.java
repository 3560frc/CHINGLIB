/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.Constants;
import org.opencv.core.*;

public class ShootBall extends CommandBase {
  
  Shooter shooter;
  Chassis chassis;
  VisionGRIP grip;
  UsbCamera camera;
  CvSink sink;
  CvSource outputStream;
  Mat base = new Mat(), output = new Mat();
  Boolean isComplete = false;
  NetworkTable nt;

  public ShootBall() {
    shooter = new Shooter();
    chassis = new Chassis();
    grip = new VisionGRIP();
    camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(640, 480);
    sink = CameraServer.getInstance().getVideo();

    addRequirements(shooter);
    addRequirements(chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.liftShooter();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    while (!isComplete){
      if (sink.grabFrame(base) == 0) continue;
      grip.process(base);
      NetworkTable table = NetworkTableInstance.getDefault().getTable("greenVision");
      double x = table.getEntry("blobX").getDouble(-1);
      double y = table.getEntry("blobY").getDouble(-1);
      double size = table.getEntry("blobSize").getDouble(-1);
      // Use these Variables as needed
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
