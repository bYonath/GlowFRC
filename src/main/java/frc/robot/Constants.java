// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kSecondControllerPort = 1;
    public static final int dutyCycleEncoderPort = 7;
    public static final int[] leftEncoder = new int[]{0, 2};
    public static final int[] rightEncoder = new int[]{5, 6};
  }
  public static class SysIdConstants {
  public static final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();
  public static final Encoder leftEncoder = new Encoder(OperatorConstants.leftEncoder[0], OperatorConstants.leftEncoder[1]);
  public static final Encoder rightEncoder = new Encoder(OperatorConstants.rightEncoder[0], OperatorConstants.rightEncoder[1]);
  }
  public static class MotorPorts{
    public static final int backRightPort = 2;
    public static final int frontRightPort = 4;
    public static final int backLeftPort = 1;
    public static final int frontLeftPort = 3;
  }
}
