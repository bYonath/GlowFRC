// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {
  private final CANSparkMax feederMotor;
  
  /** Creates a new Feeder. */
  public Feeder() {
    feederMotor = new CANSparkMax(9, MotorType.kBrushless);
    feederMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command runFeeder() {
    return run(() -> {
      feederMotor.set(1);
    });
  }
  public Command autoFeeder() {
    return run(() -> {
      feederMotor.set(1);
    }).withTimeout(.1);
  }
  public Command reverseFeeder() {
    return run(() -> {
      feederMotor.set(-1);
    });
  }

  public Command idleFeeder() {
    return run(() -> {
      feederMotor.set(0);
    });
  }
}
