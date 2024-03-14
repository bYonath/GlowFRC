// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Robot;
public class arm extends SubsystemBase {
  WPI_VictorSPX arm;
  public static DutyCycleEncoder enDC = new DutyCycleEncoder(OperatorConstants.dutyCycleEncoderPort);
  /** Creates a new arm. */
  public arm() {
    
    arm = new WPI_VictorSPX(11);  
    arm.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public Command idleArm(){
    return run(() -> {
      arm.set(0);
    });
  }
  public Command armUp() {
    System.out.println("Endc Connected?:" + enDC.isConnected());
    System.out.println("ENDC Distance: " + enDC.getAbsolutePosition());
      return run(() -> {
        if(enDC.getAbsolutePosition() < .8 && enDC.getAbsolutePosition() > -1){
          arm.set(0.5);
        System.out.println(enDC.getAbsolutePosition());
        } else {
          arm.set(0);
        }
    });
  }
  public Command armDown(){
    return run(() -> {
      arm.set(-0.5);
    });
  }
}
