/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.*;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonSRX shooterRight, shooterLeft, shooterMain, shooterSlave;
  private final DifferentialDrive shooter;
  private final Encoder rightEncoder, leftEncoder;

  private final Solenoid lift;

  public Shooter() {
    shooterLeft = new WPI_TalonSRX(Constants.portShooterLeft);
    shooterRight = new WPI_TalonSRX(Constants.portShooterRight);
    shooterMain = new WPI_TalonSRX(Constants.portShooterMain);
    shooterSlave = new WPI_TalonSRX(Constants.portShooterSlave);
    
    shooterLeft.configFactoryDefault();
    shooterMain.configFactoryDefault();
    shooterSlave.configFactoryDefault();
    shooterRight.configFactoryDefault();

    shooterSlave.follow(shooterMain);

    shooter = new DifferentialDrive(shooterLeft, shooterRight);
    rightEncoder = new Encoder(Constants.shooterRightEncoderA, Constants.shooterRightEncoderB);
    leftEncoder = new Encoder(Constants.shooterLeftEncoderA, Constants.shooterLeftEncoderB);

    lift = new Solenoid(Constants.solenoidLift);
  }

  public void setIntake(double speed){
    shooterMain.set(speed);
  }

  public void liftShooter(){
    lift.set(true);
  }

  public void dropShooter(){
    lift.set(false);
  }

  /**
   * @return Returns the distance moved by the Right Shooter Wheel
   */
  public double getRight(){
    return rightEncoder.getDistance();
  }

  /**
   * @return Returns the distance moved by the Left Shooter Wheel
   */
  public double getLeft(){
    return leftEncoder.getDistance();
  }

  /**
   * @param speed PWM Speed value to set the flywheel
   */
  public void setFlywheel(double speed){
    shooter.tankDrive(speed, speed);
  }

  /**
   * @deprecated This is NOT ADVISED. The only reason it exists is cuz Bhavik is wack
   * @param controller Xbox Controller Object
   */
  public void manualShoot(XboxController controller){
    liftShooter();
    Timer.delay(1);
    System.out.println("You have 1 second to press and hold the A Button");
    while (controller.getAButtonPressed()){
      setIntake(1);
      setFlywheel(1);
    }
    dropShooter();
  }

  /**
   * @deprecated This exists sorely to annoy people pls no use
   * @param controller Xbox Controller Object
   */
  public void teleopShoot(XboxController controller){
    if (controller.getBumperPressed(Hand.kRight)) liftShooter();
    if (controller.getBumperPressed(Hand.kLeft)) dropShooter();
    while (controller.getAButtonPressed()){
      setIntake(1);
      setFlywheel(1);
    }
  }


  @Override
  public void periodic(){
    SmartDashboard.putNumber("Right Shooter", getRight());
    SmartDashboard.putNumber("Left Shooter", getLeft());
  }

}
