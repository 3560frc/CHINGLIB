/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static int portRightFront = 1, portRightBack = 2, portLeftFront = 3, portLeftBack = 4;
    public static int rightEncoderA = 1, rightEncoderB = 2, leftEncoderA = 3, leftEncoderB = 4;
    public static int enconderCounts = 2, ticksPerInch = 3;
    public static int portShooterRight = 5, portShooterLeft = 6, portShooterMain = 7, portShooterSlave = 8;
    public static int shooterRightEncoderA = 5, shooterRightEncoderB = 6, shooterLeftEncoderA = 7, shooterLeftEncoderB = 8;
    public static int solenoidLift = 1, portIntakeMotor = 9, xboxPort = 1;
}
