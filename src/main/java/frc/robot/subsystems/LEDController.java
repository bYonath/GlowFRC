// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class LEDController extends SubsystemBase {
  /** Creates a new LEDController. */
  public Spark m_blinkin = null;
  public LEDController(int pwmPort) {
    m_blinkin = new Spark(pwmPort);
  }
  public void set(double val){
    if ((val >= -1.0) && (val <= 1.0)) {
      m_blinkin.set(val);
    }
  }
  public void Green(){
      set(0.65);
  }
  public void Rainbow(){
    System.out.println("Color set to rainbow");
    set(-0.99);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
