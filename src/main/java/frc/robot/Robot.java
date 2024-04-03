// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.ctre.phoenix6.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
// import frc.robot.subsystems.Encoder;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Auton;
import frc.robot.subsystems.DT;
import frc.robot.subsystems.LEDController;
import frc.robot.subsystems.Shooter;

// import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import com.revrobotics.ColorMatch;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  // private final ColorSensorV3 cSens = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private final Color k_orange = new Color(.3333333333333333, 0.4470588235294118, 0.21568627450980393);
  private final LEDController led_controller = new LEDController();
  private final XboxController xb1 = new XboxController(OperatorConstants.kDriverControllerPort);
  private RobotContainer m_robotContainer;
  private static final String m_defaultAuto = "Straight 2 note";
  private static final String m_autoTwo = "One note auto, no move";
  private static final String m_autoThree = "One note auto, drive forward for A stop";
  private String m_autoSelected;
  private final Auton auto = new Auton();
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public int i = 0;
  private final DT m_drive = new DT();
  // private final SysIdRoutineBot m_robot = new SysIdRoutineBot();
  // Initializes the xbox controller and statically references the port from
  // Constants for simplicities sake. Value is typically set to 0.

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Starts a new Automatic Capture for our USB camera.
    CameraServer.startAutomaticCapture();
    // Adds a new chooser so that we can select our Auton.
    SmartDashboard.putData("Auto choices", m_chooser);
    m_chooser.setDefaultOption("Straight 2 note", m_defaultAuto);
    m_chooser.addOption("One note, no movement", m_autoTwo);
    m_chooser.addOption("One note, drive forward for A-stop", m_autoThree);

    // Initializes a new robot container.
    m_robotContainer = new RobotContainer();

    // Forwards all ports 5800 -> 5807 so that we can connect to our limelight over
    // usb coonnection to roboRio.
    for (int port = 5800; port <= 5807; port++) {
      PortForwarder.add(port, "limelight.local", port);
    }
    
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.

    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

    // Getting the autonomous selection chosen
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    
    // Here we check if the auton selected is our default auton and then if it is we schedule our default auton.
    if(m_autoSelected == m_defaultAuto){
        if (auto.defaultAuton(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake) != null) {
      auto.defaultAuton(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake).schedule();
        }
    } else if(m_autoSelected == m_autoTwo){
        if (auto.autonTwo(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake) != null) {
      auto.autonTwo(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake).schedule();
        }
    } else if(m_autoSelected == m_autoThree){
      if (auto.autonThree(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake) != null) {
      auto.autonThree(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake).schedule();
        }
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
     m_autoSelected = m_chooser.getSelected();
   
    if(m_autoSelected == m_defaultAuto){
        if (auto.defaultAuton(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake) != null) {
      auto.defaultAuton(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake).cancel();
        }
    } else if(m_autoSelected == m_autoTwo){
        if (auto.autonTwo(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake) != null) {
      auto.autonTwo(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake).cancel();
        }
    } else if(m_autoSelected == m_autoThree){
      if (auto.autonThree(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake) != null) {
      auto.autonThree(m_drive, m_robotContainer.m_shooter, m_robotContainer.m_feeder, m_robotContainer.m_intake).cancel();
        }
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
     CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
