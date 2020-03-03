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

  public void liftControl(XboxController controller) {
    //CHANGE BUTTON BINDINGS
    if(controller.getBButton()) {

      extend();
      
    }
    else if(!(controller.getBButton())) {

      retract();
      
    }
    if(controller.getXButton()) {

      retractWinch();
      
    }
    else if(!(controller.getBButton()) && !(controller.getXButton())) {

      stopWinch();

    }

  }

  public void extend() {
    lifty.set(true);
  }

  public void retract() {
    lifty.set(false);
  }

  public void timedPull(double time) {
    winchMotor.set(1);
    Timer.delay(time);
    winchMotor.set(0);
  }

  public void retractWinch() {

    winchMotor.set(1);

  }

  public void stopWinch() {

    winchMotor.set(0);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
