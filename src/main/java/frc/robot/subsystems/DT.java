// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import edu.wpi.first.wpilibj2.command.button.Trigger;
//import frc.robot.Constants;
//import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.SysIdConstants;

//import java.util.function.DoubleSupplier;

// Old Motor Controller
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.MutableMeasure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.CAN;
//import edu.wpi.first.wpilibj.DutyCycle;
//import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
//import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import static edu.wpi.first.units.Units.Volts;

//import java.net.CacheRequest;

//import javax.print.CancelablePrintJob;

//import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
//import edu.wpi.first.units.MutableMeasure;
//import edu.wpi.first.units.Velocity;
//import edu.wpi.first.units.Voltage;
import static edu.wpi.first.units.MutableMeasure.mutable;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
//import frc.robot.Constants;

// Experimental Imports for CanSparkMax?
// It seems as if the Neos may still work with victors?
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class DT extends SubsystemBase {

  public DifferentialDrive m_drive;
  private CommandPS4Controller ps1 = new CommandPS4Controller(0);

  // Motor Declarations
  public CANSparkMax frontRight;
  public CANSparkMax frontLeft;
  public CANSparkMax backRight;
  public CANSparkMax backLeft;
  public CANSparkMax transferMotor;

  // Older Motor Declarations
  /*
  public WPI_VictorSPX frontRight;
  public WPI_VictorSPX frontLeft;
  public WPI_VictorSPX backRight;
  public WPI_VictorSPX backLeft;
  public WPI_VictorSPX transferMotor;
  */
  public Encoder leftEncoder = SysIdConstants.leftEncoder;
  public Encoder rightEncoder = SysIdConstants.rightEncoder;

  ADXRS450_Gyro m_gyro = SysIdConstants.m_gyro;

  //private int i = 0;

  private final DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(
      m_gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance()
    );
      private final MutableMeasure<Voltage> m_appliedVoltage = mutable(Volts.of(0));
  // Mutable holder for unit-safe linear distance values, persisted to avoid reallocation.
  private final MutableMeasure<Distance> m_distance = mutable(Meters.of(0));
  // Mutable holder for unit-safe linear velocity values, persisted to avoid reallocation.
  private final MutableMeasure<Velocity<Distance>> m_velocity = mutable(MetersPerSecond.of(0));
  /* Creates a new Motor Object */
  /** Creates a new Drivetrain. (Note the args passed through are getting the axis of the controller joystick) */
   private final SysIdRoutine m_sysIdRoutine =
      new SysIdRoutine(
          // Empty config defaults to 1 volt/second ramp rate and 7 volt step voltage.
          new SysIdRoutine.Config(),
          new SysIdRoutine.Mechanism(
              // Tell SysId how to plumb the driving voltage to the motors.
              (Measure<Voltage> volts) -> {
                frontRight.setVoltage(volts.in(Volts));
                frontLeft.setVoltage(volts.in(Volts));
                backRight.setVoltage(volts.in(Volts));
                backLeft.setVoltage(volts.in(Volts));
              },
              // Tell SysId how to record a frame of data for each motor on the mechanism being
              // characterized.
              log -> {
                // Record a frame for the left motors.  Since these share an encoder, we consider
                // the entire group to be one motor.
                log.motor("drive-left(Front)")
                    .voltage(
                        m_appliedVoltage.mut_replace(
                            frontLeft.get() * RobotController.getBatteryVoltage(), Volts))
                    .linearPosition(m_distance.mut_replace(leftEncoder.getDistance(), Meters))
                    .linearVelocity(
                        m_velocity.mut_replace(leftEncoder.getRate(), MetersPerSecond));
                // Record a frame for the right motors.  Since these share an encoder, we consider
                // the entire group to be one motor.
                 log.motor("drive-left(Back)")
                    .voltage(
                        m_appliedVoltage.mut_replace(
                            backLeft.get() * RobotController.getBatteryVoltage(), Volts))
                    .linearPosition(m_distance.mut_replace(leftEncoder.getDistance(), Meters))
                    .linearVelocity(
                        m_velocity.mut_replace(leftEncoder.getRate(), MetersPerSecond));
                log.motor("drive-right(Right)")
                    .voltage(
                        m_appliedVoltage.mut_replace(
                            frontRight.get() * RobotController.getBatteryVoltage(), Volts))
                    .linearPosition(m_distance.mut_replace(rightEncoder.getDistance(), Meters))
                    .linearVelocity(
                        m_velocity.mut_replace(rightEncoder.getRate(), MetersPerSecond));
               log.motor("drive-right(Back)")
                    .voltage(
                        m_appliedVoltage.mut_replace(
                            backRight.get() * RobotController.getBatteryVoltage(), Volts))
                    .linearPosition(m_distance.mut_replace(rightEncoder.getDistance(), Meters))
                    .linearVelocity(
                        m_velocity.mut_replace(rightEncoder.getRate(), MetersPerSecond));
              },
              // Tell SysId to make generated commands require this subsystem, suffix test state in
              // WPILog with this subsystem's name ("drive")
              this));
  public DT() {
    // Initializes all the motors.
    /*
    backRight = new WPI_VictorSPX(7);
    frontLeft = new WPI_VictorSPX(1);
    frontRight = new WPI_VictorSPX(2);
    backLeft = new WPI_VictorSPX(3);
    */
    
    // Theoretical Code for Neo motors?
    backRight = new CANSparkMax(7, MotorType.kBrushless);
    frontLeft = new CANSparkMax(1, MotorType.kBrushless);
    frontRight = new CANSparkMax(2, MotorType.kBrushless);
    backLeft = new CANSparkMax(3, MotorType.kBrushless);

    // Initializes the drive train as a new instance of the DifferentialDrive class
    Shuffleboard.getTab("SYSID DT ROUTINE");
    // Shuffleboard.getTab("ODOMETRY").add("Od", m_gyro.getRotation2d());
    System.out.println("Is the gyro connected?: " + m_gyro.isConnected());
  
    m_drive = new DifferentialDrive(
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
 
  public Pose2d getPos(){
    return m_odometry.getPoseMeters();

  }
  public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
    return m_sysIdRoutine.quasistatic(direction);
  }
  public Command sysIdDynamic(SysIdRoutine.Direction direction) {
    return m_sysIdRoutine.dynamic(direction);
  }
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
  }
  public double getAverageEncoderDistance() {
    return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
  }
  public Command arcadeDriveCommand(double a, double b) {
    // A split-stick arcade command, with forward/backward controlled by the left
    // hand, and turning controlled by the right.
    return run(() -> m_drive.arcadeDrive(a, b))
        .withName("arcadeDrive");
  }
  public Command driveForward(){
    return run(() -> {
     m_drive.arcadeDrive(1, 0);
    }).withTimeout(.77);
  }
  public Command driveIndefinitely(){
    return run(() -> {
     m_drive.arcadeDrive(1, 0);
    });
  }
  public Command stop(){
    return run(() -> {
     m_drive.tankDrive(0, 0);
    }).withTimeout(.5);
  }
  public Command driveBackwards(){
    return run(() -> {
       m_drive.arcadeDrive(-1, 0);
    }).withTimeout(.8);
  } 
  public Command waitUntil(){
    return run(() -> {
      
    }).withTimeout(.75);
  } 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println(m_gyro.getRotation2d());
    // Using the tankDrive (or arcadeDrive) method of the driveTrain class and flipping the right side inputs to fit driver's tastes and have both sides move the same way
    //driveTrain.tankDrive(ps1.getRightY() * -1, ps1.getLeftY());
    m_drive.arcadeDrive(ps1.getLeftY(), (ps1.getRightX()));
    // m_odometry.update(m_gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
  }
}