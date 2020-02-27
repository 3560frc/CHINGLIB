/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.networktables.*;
import frc.robot.*;

public class Chassis extends SubsystemBase {
  
  private final Encoder rightEncoder, leftEncoder;
  private final WPI_VictorSPX rightFront, rightBack, leftFront, leftBack;
  private final Compressor compressor;
  private final NetworkTableInstance instance;
  private final NetworkTable table;

  public Chassis() {
    instance = NetworkTableInstance.getDefault();
    table = instance.getTable("SmartDashboard");

    rightFront = new WPI_VictorSPX(Constants.portRightFront);
    rightBack = new WPI_VictorSPX(Constants.portRightBack);
    leftFront = new WPI_VictorSPX(Constants.portLeftFront);
    leftBack = new WPI_VictorSPX(Constants.portLeftBack);
    rightBack.setInverted(true);
    leftBack.setInverted(true);

    rightBack.follow(rightFront);
    leftBack.follow(leftFront);

    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
