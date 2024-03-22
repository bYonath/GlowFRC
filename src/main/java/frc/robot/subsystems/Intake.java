// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  public WPI_VictorSPX intakeMotor;

  /** Creates a new Intake. */
  public Intake() {
    intakeMotor = new WPI_VictorSPX(4);
    intakeMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command runIntake() {
    return run(() -> {
      intakeMotor.set(.7);
    });
  }
  public Command autoIntake() {
    return run(() -> {
      intakeMotor.set(.75);
    }).withTimeout(1);
  }
  public Command reverseIntake() {
    return run(() -> {
      intakeMotor.set(-0.5);
    });
  }

  public Command idleIntake() {
    return run(() -> {
      intakeMotor.set(0);
    });
  }
}
