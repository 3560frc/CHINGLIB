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
import edu.wpi.first.vision.VisionThread;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.Constants;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class ShootBall extends CommandBase {
  
  Shooter shooter;
  Chassis chassis;
  GripPipeline grip;
  UsbCamera camera;
  CvSink sink;
  CvSource outputStream;
  Mat base = new Mat(), output = new Mat();
  Boolean isComplete[] = {false, false};
  NetworkTable nt;
  XboxController controller;
  VisionThread visionThread;
  Object imgLock;
  double x, size;

  public ShootBall() {
    shooter = new Shooter();
    chassis = new Chassis();
    grip = new GripPipeline();
    camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(640, 480);
    sink = CameraServer.getInstance().getVideo();
    outputStream = CameraServer.getInstance().putVideo("Shooter CAM", 640, 480);
    controller = new XboxController(0);
    imgLock = new Object();
    addRequirements(shooter);
    addRequirements(chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.liftShooter();
    visionThread = new VisionThread(camera, new GripPipeline(), pipeline -> {
      if (!pipeline.findContoursOutput().isEmpty()){
        Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
        synchronized (imgLock){
          x = r.x  + (r.width / 2);
          size = r.area();
        }
      }
    });
    visionThread.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double Nx, Nsize;
    synchronized (imgLock){
      Nx = this.x;
      Nsize = this.size;
    }
    if (Nx < 310) chassis.spinRight(0.25, 2);
    if (Nx > 330) chassis.spinLeft(0.25, 2);
    if (Nsize > Constants.optimalSize + 5) chassis.driveBoth(0.25, 2);
    if (Nsize < Constants.optimalSize - 5) chassis.driveBoth(0.25, 2);
  }

  public void orientForward(double size){
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setIntake(0.25);
    shooter.setFlywheel(1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (x >= 310 && x <= 330) && (size >= Constants.optimalSize - 5 && size <= Constants.optimalSize + 5);
  }
}
