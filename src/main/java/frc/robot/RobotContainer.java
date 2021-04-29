// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.commands.AutoIntake;
import frc.robot.commands.EastDrive;
import frc.robot.commands.ZeroHood;
import frc.robot.commands.Score;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton; 

public class RobotContainer {
  private final Drivebase drivebase = new Drivebase();

  private final Shooter shooter = new Shooter();

  private final Indexer indexer = new Indexer();

  private final Joystick controller = new Joystick(0);

  private final Intake intake = new Intake(); 

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    drivebase.setDefaultCommand(getDriveCommand());
    shooter.setDefaultCommand(new ZeroHood(shooter));
    intake.setDefaultCommand(new AutoIntake(intake, drivebase));

    JoystickButton leftBumper = new JoystickButton(controller, Constants.LEFT_BUMPER);
    JoystickButton rightBumper = new JoystickButton(controller, Constants.RIGHT_BUMPER);

    leftBumper.whileHeld(new Score(drivebase, shooter, indexer));
    rightBumper.whenPressed(new InstantCommand(() -> drivebase.reverse()));
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new ZeroHood(shooter);
  }

  protected double applyDeadband(double value) {
    if (Math.abs(value) > Constants.JOYSTICK_DEADBAND) {
      if (value > 0.0) {
        return (value - Constants.JOYSTICK_DEADBAND) / (1.0 - Constants.JOYSTICK_DEADBAND);
      } else {
        return (value + Constants.JOYSTICK_DEADBAND) / (1.0 - Constants.JOYSTICK_DEADBAND);
      }
    } else {
      return 0.0;
    }
  }
  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getDriveCommand() {
    return new EastDrive(
      drivebase,
      () -> applyDeadband(-controller.getRawAxis(Constants.LEFT_JOYSTICK)),
      () -> applyDeadband(-controller.getRawAxis(Constants.RIGHT_JOYSTICK)),
      () -> applyDeadband((controller.getRawAxis(Constants.RIGHT_TRIGGER) + 1) / 2));
  }
}
