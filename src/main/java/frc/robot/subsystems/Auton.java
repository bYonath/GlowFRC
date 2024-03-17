// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.arm;
import edu.wpi.first.wpilibj.Timer;
public class Auton extends SubsystemBase {
  /** Creates a new Auton. */
  private final LimeLight lml = new LimeLight();
  private final arm A = new arm();
  public Auton() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }
  public void adjustArm(){
    
  }
}
