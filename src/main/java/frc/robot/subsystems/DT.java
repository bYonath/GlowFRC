// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
public class DT extends SubsystemBase {
  public DifferentialDrive driveTrain;
  private CommandXboxController xb1 = new CommandXboxController(0);
  public WPI_VictorSPX frontRight;
  public WPI_VictorSPX frontLeft;
  public WPI_VictorSPX backRight;
  public WPI_VictorSPX backLeft;
  public WPI_VictorSPX transferMotor;
  public Trigger rightTrigger = xb1.rightTrigger();
  public Trigger rightBumper = xb1.rightBumper();
  public Trigger leftTrigger = xb1.leftTrigger();
  public Trigger aButton = xb1.a();
  public Trigger leftBumper = xb1.leftBumper();
  /* Creates a new Motor Object */
  /** Creates a new Drivetrain. (Note the args passed through are getting the axis of the controller joystick) */
  public DT() {
    // Initializes all the motors. 
    frontRight = new WPI_VictorSPX(7);
    frontLeft = new WPI_VictorSPX(1);
    backRight = new WPI_VictorSPX(2);
    backLeft = new WPI_VictorSPX(3);
    
    transferMotor = new WPI_VictorSPX(7);
    // Initializes the drive train as a new instance of the DifferentialDrive class
    driveTrain = new DifferentialDrive(
      (double output) -> {
        frontLeft.set(output);
        backLeft.set(output);
    },
    (double output) -> {
        frontRight.set(output);
        backRight.set(output);
    }
    );
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Using the tankDrive (or arcadeDrive) method of the driveTrain class and flipping the right side inputs to fit driver's tastes and have both sides move the same way
    //driveTrain.tankDrive(xb1.getRightY() * -1, xb1.getLeftY());
    driveTrain.arcadeDrive(xb1.getRightX(), xb1.getLeftY());
  }
}