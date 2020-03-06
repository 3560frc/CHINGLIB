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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.networktables.*;
import frc.robot.*;

public class Chassis extends SubsystemBase {
  
  private final Encoder rightEncoder, leftEncoder;
  private final WPI_VictorSPX rightFront, leftBack, leftFront;
  private final WPI_TalonSRX rightBack;
  private final NetworkTableInstance instance;
  private final NetworkTable table;


  public Chassis() {
    instance = NetworkTableInstance.getDefault();
    table = instance.getTable("SmartDashboard");

    rightFront = new WPI_VictorSPX(Constants.portRightFront);
    rightBack = new WPI_TalonSRX(Constants.portRightBack);
    leftFront = new WPI_VictorSPX(Constants.portLeftFront);
    leftBack = new WPI_VictorSPX(Constants.portLeftBack);

    rightFront.configFactoryDefault();
    rightBack.configFactoryDefault();
    leftFront.configFactoryDefault();
    leftBack.configFactoryDefault();

    rightFront.setInverted(true);
    leftFront.setInverted(true);


    rightEncoder = new Encoder(Constants.rightEncoderA, Constants.rightEncoderB);
    leftEncoder = new Encoder(Constants.leftEncoderA, Constants.leftEncoderB);

  }

  /**
   * @param speed PWM Speed Value
   **/

  public void controlLeft(double speed) {
    leftFront.set(speed);
    leftBack.set(speed);
  }

  /**
   * @param speed PWM Speed Value
   **/

  public void controlRight(double speed) {
    rightFront.set(speed);
    rightBack.set(speed);
  }

  /**
   * @param distance Distance in inches
   * @param speed PWM Speed Value
   **/
  public void driveBoth(double speed, double distance){
    double currentTicks = (getLeft() + getRight()) / 2;
    while (Math.abs((distance * Constants.ticksPerInch) - currentTicks) < 1){
      controlLeft(speed);
      controlRight(speed);
      currentTicks = (getLeft() + getRight()) / 2;
    }
  }

  /**
   * @param speed PWM Speed Value
   * @param time Time the robot spins
   */
  public void spinLeft(double speed, double time){
    controlLeft(-speed);
    controlRight(speed);
    Timer.delay(time);
  }

  /**
   * @param speed PWM Speed Value
   * @param time Time the robot spins
   */
  public void spinRight(double speed, double time){
    controlLeft(speed);
    controlRight(-speed);
    Timer.delay(time);
  }

  public void stop() {
    leftFront.stopMotor();
    rightFront.stopMotor();
    leftBack.stopMotor();
    rightBack.stopMotor();
  }

  /**
   * @param controller XboxController Object
   */
  public void tankDrive(XboxController controller){
    leftFront.set(-controller.getRawAxis(2));
    rightFront.set(-controller.getRawAxis(5));
    rightBack.set(controller.getRawAxis(5));
    leftBack.set(controller.getRawAxis(2));
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
