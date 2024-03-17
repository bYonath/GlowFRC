// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.arm;
import frc.robot.subsystems.DT;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.LEDController;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.arm;

import com.ctre.phoenix6.configs.FeedbackConfigs;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final DT m_drivetrain = new DT();
  private final Intake m_intake = new Intake();
  private final Shooter m_shooter = new Shooter();
  private final Feeder m_feeder = new Feeder();
  private final arm m_arm = new arm();
  // private final LEDController m_led_controller = new LEDController(0);


  //  Creates a CommandXboxController //Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    m_intake.setDefaultCommand(m_intake.idleIntake());
    m_shooter.setDefaultCommand(m_shooter.idleShooter());
    m_feeder.setDefaultCommand(m_feeder.idleFeeder());
    m_arm.setDefaultCommand(m_arm.idleArm());
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    m_driverController.leftBumper().whileTrue(m_intake.runIntake().alongWith(m_feeder.runFeeder()));
    m_driverController.leftTrigger().whileTrue(m_intake.reverseIntake().alongWith(m_feeder.reverseFeeder()));
    m_driverController.rightBumper().whileTrue(m_shooter.runShooter());
    m_driverController.rightTrigger().whileTrue(m_shooter.reverseShooter().alongWith(m_feeder.reverseFeeder()));
    m_driverController.povUp().whileTrue(m_arm.armUp());
    m_driverController.povDown().whileTrue(m_arm.armDown());
    m_driverController.a().whileTrue(m_shooter.slowedShooter());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto();
  }
}
