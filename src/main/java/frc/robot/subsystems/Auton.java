// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj2.command.WaitCommand;
//import frc.robot.subsystems.LimeLight;
//import frc.robot.RobotContainer;
//import edu.wpi.first.units.Time;
//import edu.wpi.first.wpilibj.Timer;
//import frc.robot.subsystems.DT;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
public class Auton extends SubsystemBase {
  /** Creates a new Auton. */
  // private final LimeLight lml = new LimeLight();

  // use later?

  //private final DT drive = new DT();
  //private final Intake intake = new Intake();
  //private final Timer time = new Timer();
  // private final WaitCommand wait = new WaitCommand(.75);
  //private final Feeder feed = new Feeder();
  public Auton() {
    // All code for AUTON is temporary as of 3/21/24
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }


  public Command defaultAuton(DT dt, Shooter shoot, Feeder feed, Intake in) {

    // RAHH!! GIVE ME MORE EXPLANATIONS REEEE!!!!

    return Commands.sequence(
      // shooter shooter,
      shoot.autoShooter().alongWith(feed.autoFeeder()),
      shoot.stopShooter(),
      dt.driveForward().alongWith(in.autoIntake()),
      dt.stop(),
      dt.driveBackwards(),
      dt.stop(),
      shoot.autoShooter().alongWith(in.autoIntake())
      );
  }
   public Command autonTwo(DT dt, Shooter shoot, Feeder feed, Intake in) {
    return Commands.sequence(
      // shooter shooter,
      shoot.autoShooter().alongWith(feed.autoFeeder()),
      shoot.stopShooter()
      );
  }
  public Command autonThree(DT dt, Shooter shoot, Feeder feed, Intake in) {
    return Commands.sequence(
      // shooter shooter, 
      shoot.autoShooter().alongWith(feed.autoFeeder()),
      shoot.stopShooter(),
      dt.driveIndefinitely()
      );
  }

  // What i learned today: we love "// shooter shooter," 
  // it has hereby become the new motto of this class
}
