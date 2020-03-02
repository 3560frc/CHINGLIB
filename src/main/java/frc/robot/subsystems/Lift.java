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

public class Lift extends SubsystemBase {
  /**
   * Creates a new Lift.
   */
  private final WPI_VictorSPX winchMotor;
  private final Solenoid lifty;
  public Lift() {
    winchMotor = new WPI_VictorSPX(1); //FIX PORTS                                     
    lifty = new Solenoid(2); 
  }

  public void extend() {
    lifty.set(true);
  }

  public void retract() {
    lifty.set(false);
  }

  public void pull(double time) {
    winchMotor.set(1);
    Timer.delay(time);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
