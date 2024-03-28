// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

public class Climb extends SubsystemBase {
  /** Creates a new Climgb. */
  public CANSparkMax m_climbOne = new CANSparkMax(21, MotorType.kBrushless);
  public CANSparkMax m_climbTwo = new CANSparkMax(22, MotorType.kBrushless);
  public Climb() {
    m_climbOne.restoreFactoryDefaults();
    m_climbTwo.restoreFactoryDefaults();
    m_climbOne.setInverted(true);
    m_climbTwo.setInverted(false);
    m_climbOne.burnFlash();
    m_climbTwo.burnFlash();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public Command climbRaise(){
    return run(() -> {
      m_climbOne.set(1);
      m_climbTwo.set(1);
    });
  }
  public Command climbLower(){
    return run(() -> {
      m_climbOne.set(-1);
      m_climbTwo.set(-1);
    });
  }
  public Command climbIdle(){
    return run(() -> {
      m_climbOne.set(0);
      m_climbTwo.set(0);
    });
  }
}
