/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;

public class Shooter extends SubsystemBase {
  
  WPI_TalonSRX shooterRight = new WPI_TalonSRX(5);
  WPI_TalonSRX shooterLeft = new WPI_TalonSRX(6);
  WPI_TalonSRX shooterMain = new WPI_TalonSRX(7);
  WPI_TalonSRX shooterSlave = new WPI_TalonSRX(8);

  public Shooter() {
    shooterLeft.configFactoryDefault();
    shooterMain.configFactoryDefault();
    shooterSlave.configFactoryDefault();
    shooterRight.configFactoryDefault();

    shooterSlave.follow(shooterMain);
  }

  public void shootBall(double rSpeed, double lSpeed, double iSpeed){
    shooterMain.set(iSpeed);
    shooterLeft.set(lSpeed);
    shooterRight.set(rSpeed);
  }
}
