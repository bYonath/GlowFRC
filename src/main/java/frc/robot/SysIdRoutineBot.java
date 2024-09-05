// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DT;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class SysIdRoutineBot {
  // The robot's subsystems
  private final DT m_drive = new DT();
  private final Shooter m_shooter = new Shooter();

  // The driver's controller
  CommandPS4Controller m_driverController =
      new CommandPS4Controller(OperatorConstants.kDriverControllerPort);

  /**
   * Use this method to define bindings between conditions and commands. These are useful for
   * automating robot behaviors based on button and sensor input.
   *
   * <p>Should be called during {@link Robot#robotInit()}.
   *
   * <p>Event binding methods are available on the {@link Trigger} class.
   */
  public void configureBindings() {
    // Control the drive with split-stick arcade controls
    m_drive.setDefaultCommand(
        m_drive.arcadeDriveCommand(
          m_driverController.getRightX(), m_driverController.getLeftY() ));

    // Bind full set of SysId routine tests to buttons; a complete routine should run each of these
    // once.
    // Using bumpers as a modifier and combining it with the buttons so that we can have both sets
    // of bindings at once
    m_driverController
        .cross()
        .and(m_driverController.R1())
        .whileTrue(m_drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    m_driverController
        .circle()
        .and(m_driverController.R1())
        .whileTrue(m_drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    m_driverController
        .square()
        .and(m_driverController.R1())
        .whileTrue(m_drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    m_driverController
        .triangle()
        .and(m_driverController.R1())
        .whileTrue(m_drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Control the shooter wheel with the left trigger
    m_shooter.setDefaultCommand(m_shooter.runShooter());

    m_driverController
        .cross()
        .and(m_driverController.L1())
        .whileTrue(m_drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    m_driverController
        .circle()
        .and(m_driverController.L1())
        .whileTrue(m_drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    m_driverController
        .square()
        .and(m_driverController.L1())
        .whileTrue(m_drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    m_driverController
        .triangle()
        .and(m_driverController.L1())
        .whileTrue(m_drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
  }

  /**
   * Use this to define the command that runs during autonomous.
   *
   * <p>Scheduled during {@link Robot#autonomousInit()}.
   */
  public Command getAutonomousCommand() {
    // Do nothing
    return m_drive.run(() -> {});
  }
}
