package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;

import org.opencv.core.Core;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ShootBall;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  Chassis chassis;
  XboxController controller;
  Intake intake;
  Shooter shooter;
  Lift lift;
  ShootBall shootBall;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    chassis = new Chassis();
    intake = new Intake();
    shooter = new Shooter();
    controller = new XboxController(1);
    lift = new Lift();
    shootBall = new ShootBall();
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    shootBall.execute();
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    //chassis.tankDrive(controller);
    //if (controller.getAButtonPressed()) shootBall.execute();
    chassis.driveBoth(0.25, 10);
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
