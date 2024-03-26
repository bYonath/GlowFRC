// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Robot;
public class arm extends SubsystemBase {
  WPI_VictorSPX arm;
  WPI_VictorSPX arm2;
  /** Creates a new arm. */
  public arm() {
    
    arm = new WPI_VictorSPX(11); 
    arm2 = new WPI_VictorSPX(12);
    arm.setInverted(false);
    arm2.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // System.out.println(enDC.getAbsolutePosition());
  }
  public Command idleArm(){
    return run(() -> {
      arm.set(0);
      arm2.set(0);
    });
  }
  public Command armUp() {
      return run(() -> {
          arm.set(0); // Old value is -0.5 currently not using arm.
          arm2.set(0); // Old value is -0.5 currently not using arm.
    });
  }
  public Command armDown(){
    return run(() -> {
      arm.set(0); // Old value is 1, currently not using arm.
      arm2.set(0); // Old value is 1, currently not using arm.
    });
  }
}
