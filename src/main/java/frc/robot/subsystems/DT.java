// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.XboxController;
public class DT extends SubsystemBase {
  public DifferentialDrive driveTrain;
  private XboxController xb1 = new XboxController(0);

  public WPI_VictorSPX frontRight;
  public WPI_VictorSPX frontLeft;
  public WPI_VictorSPX backRight;
  public WPI_VictorSPX backLeft;
  public WPI_VictorSPX intakeMotor;
  /* Creates a new Motor Object */
  /** Creates a new Drivetrain. (Note the args passed through are getting the axis of the controller joystick) */
  public DT() {

    frontRight = new WPI_VictorSPX(0);
    frontLeft = new WPI_VictorSPX(1);
    backRight = new WPI_VictorSPX(2);
    backLeft = new WPI_VictorSPX(3);
    intakeMotor = new WPI_VictorSPX(4);
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
    driveTrain.arcadeDrive(xb1.getRawAxis(0), xb1.getRawAxis(1));
    if(xb1.getAButtonPressed()){
      intakeMotor.set(-1);
    }
    if(xb1.getAButtonReleased()){
      intakeMotor.set(0);
    }
  }
}