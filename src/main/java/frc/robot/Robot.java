package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;

  WPI_TalonSRX liftMain;
  WPI_TalonSRX liftBlackMan;

  WPI_TalonSRX intake;

  WPI_TalonSRX shooterRight;
  WPI_TalonSRX shooterLeft;
  WPI_TalonSRX shooterIntake;
  WPI_TalonSRX shooterIntakeBlackMan;

  Drive drive = new Drive();

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    liftMain = new WPI_TalonSRX(5);
    liftBlackMan = new WPI_TalonSRX(6);

    intake = new WPI_TalonSRX(7);

    shooterRight = new WPI_TalonSRX(8);
    shooterLeft = new WPI_TalonSRX(9);
    shooterIntake = new WPI_TalonSRX(10);
    shooterIntakeBlackMan = new WPI_TalonSRX(11);

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
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    drive.tankDrive();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
