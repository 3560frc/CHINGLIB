/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.util.Color;


public class Spinner extends SubsystemBase {
  
  private final I2C.Port port = I2C.Port.kOnboard;

  ColorSensorV3 sensorV3 = new ColorSensorV3(port);
  ColorMatch matcher = new ColorMatch();

  private final Color Blue = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color Green = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color Red = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color Yellow = ColorMatch.makeColor(0.361, 0.524, 0.113);

  Jaguar spinner = new Jaguar(1); // Change and FIX pls

  String desiredColor;

  public Spinner() {
    matcher.addColorMatch(Blue);
    matcher.addColorMatch(Red);
    matcher.addColorMatch(Green);
    matcher.addColorMatch(Yellow);
  }

  public String getColor(){
    Color detectedColor = sensorV3.getColor();
    String res;
    ColorMatchResult match = matcher.matchClosestColor(detectedColor);
    if (match.color == Blue){
      res = "Blue";
    } else if (match.color == Red){
      res = "Red";
    } else if (match.color == Green){
      res = "Green";
    } else if (match.color == Yellow){
      res = "Yellow";
    } else {
      res = "U";
    }
    return res;
  }

  public void init(){
    desiredColor = getColor();
  }

  public void spin(double rotationTime, int spins){
    init();
    spinner.set(0.5);
    spinner.setExpiration(rotationTime);

    while(!getColor().equals(desiredColor)){
      spinner.set(0.1);
    }
    spinner.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
