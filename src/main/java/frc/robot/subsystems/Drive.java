/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Drive extends SubsystemBase {
  
  WPI_VictorSPX leftFront = new WPI_VictorSPX(1);
  WPI_VictorSPX leftBack = new WPI_VictorSPX(3);
  WPI_VictorSPX rightFront = new WPI_VictorSPX(2);
  WPI_VictorSPX rightBack = new WPI_VictorSPX(4);
  XboxController controller = new XboxController(0);

  public Drive() {
    rightBack.follow(rightFront);
    leftBack.follow(leftFront);

    leftBack.configFactoryDefault();
    leftFront.configFactoryDefault();
    rightBack.configFactoryDefault();
    rightFront.configFactoryDefault();

    rightBack.setInverted(true);
    leftBack.setInverted(true);

  }

  public void setLeft(double speed){
    leftFront.set(speed);
  }

  public void setRight(double speed){
    rightFront.set(speed);
  }

  public void setSpeed(double speed){
    setRight(speed);
    setLeft(speed);
  }

  public void controlLeft(){
    setLeft(controller.getY(Hand.kLeft));
  }

  public void controlRight(){
    setRight(controller.getY(Hand.kRight));
  }

  public void tankDrive(){
    controlLeft();
    controlRight();
  }
}
