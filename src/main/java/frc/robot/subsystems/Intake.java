/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  
  private final WPI_TalonSRX intakeMotor;
  
  public Intake() {
    intakeMotor = new WPI_TalonSRX(Constants.portIntakeMotor);
    intakeMotor.configFactoryDefault();
  }

  /**
   * @param speed The speed that the intake motors will move at
   */
  public void setSpeed(double speed){
    intakeMotor.set(speed);
  }

  /**
   * @param controller Xbox Controller Object
   */
  public void controlIntake(XboxController controller){
    if (controller.getStartButtonPressed()) setSpeed(0.75);
    if (controller.getBackButtonPressed()) stop();
  }

  public void stop(){
    intakeMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
