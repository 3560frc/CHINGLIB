/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.networktables.*;
import frc.robot.*;

public class Chassis extends SubsystemBase {
  
  private final Encoder rightEncoder, leftEncoder;
  private final WPI_VictorSPX rightFront, rightBack, leftFront, leftBack;
  private final NetworkTableInstance instance;
  private final NetworkTable table;
  private final DifferentialDrive drive;

  public Chassis() {
    instance = NetworkTableInstance.getDefault();
    table = instance.getTable("SmartDashboard");

    rightFront = new WPI_VictorSPX(Constants.portRightFront);
    rightBack = new WPI_VictorSPX(Constants.portRightBack);
    leftFront = new WPI_VictorSPX(Constants.portLeftFront);
    leftBack = new WPI_VictorSPX(Constants.portLeftBack);

    rightFront.configFactoryDefault();
    rightBack.configFactoryDefault();
    leftFront.configFactoryDefault();
    leftBack.configFactoryDefault();

    rightBack.setInverted(true);
    leftBack.setInverted(true);

    drive = new DifferentialDrive(leftFront, rightFront);

    rightBack.follow(rightFront);
    leftBack.follow(leftFront);

    rightEncoder = new Encoder(Constants.rightEncoderA, Constants.rightEncoderB);
    leftEncoder = new Encoder(Constants.leftEncoderA, Constants.leftEncoderB);

  }

  /**
   * @param distance Distance in inches
   * @param speed PWM Speed Value
   **/
  public void driveBoth(double speed, double distance){
    double currentTicks = (getLeft() + getRight()) / 2;
    while (Math.abs((distance * Constants.ticksPerInch) - currentTicks) < 1){
      drive.tankDrive(speed, speed);
      currentTicks = (getLeft() + getRight()) / 2;
    }
  }

  /**
   * @param speed PWM Speed Value
   * @param time Time the robot spins
   */
  public void spinLeft(double speed, double time){
    drive.tankDrive(speed, -speed);
    Timer.delay(time);
  }

  /**
   * @param speed PWM Speed Value
   * @param time Time the robot spins
   */
  public void spinRight(double speed, double time){
    drive.tankDrive(-speed, speed);
    Timer.delay(time);
  }

  public void stop() {

    drive.stopMotor();
    
  }

  /**
   * @param controller XboxController Object
   */
  public void tankDrive(XboxController controller){
    drive.tankDrive(controller.getY(Hand.kLeft), controller.getY(Hand.kRight));
  }

  public double getRight(){
    return rightEncoder.getDistance();
  }

  public double getLeft(){
    return leftEncoder.getDistance();
  }

  public double getDistance(){
    double result = (getLeft() + getRight()) / (Constants.enconderCounts*2);
    SmartDashboard.putNumber("Distance", result);
    return result;
  }

  public void resetEncoders(){
    leftEncoder.reset();
    rightEncoder.reset();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Encoder", getLeft());
	SmartDashboard.putNumber("Right Encoder", getRight());
	SmartDashboard.putNumber("Distance", getDistance());
  }
}
