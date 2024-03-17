// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  public CANSparkMax shooterRight;
  public CANSparkMax shooterLeft;

  /** Creates a new Shooter. */
  public Shooter() {
    shooterRight = new CANSparkMax(5, MotorType.kBrushless);
    shooterLeft = new CANSparkMax(6, MotorType.kBrushless);
    shooterLeft.restoreFactoryDefaults();
    shooterRight.restoreFactoryDefaults();
    shooterLeft.setInverted(true);
    shooterRight.setInverted(false);
    shooterLeft.burnFlash();
    shooterRight.burnFlash();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command runShooter() {
    return run(() -> {
      shooterLeft.set(1);
      shooterRight.set(1);
    });
  }
  public Command reverseShooter() {
    return run(() -> {
      shooterLeft.set(-1);
      shooterRight.set(-1);
    });
  }
  public Command slowedShooter() {
    return run(() -> {
      shooterLeft.set(.25);
      shooterRight.set(.25);
    });
  }
  public Command idleShooter() {
    return run(() -> {
      shooterLeft.set(0);
      shooterRight.set(0);
    });
  }
}
