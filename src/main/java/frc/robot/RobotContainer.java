// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.commands.EastDrive;
import frc.robot.commands.ZeroHood;
import frc.robot.commands.TurnToGoal;
import frc.robot.commands.Shoot;
import frc.robot.commands.Index;
import frc.robot.commands.RunTests;
import frc.robot.Constants.ControllerConstants;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton; 

public class RobotContainer {
  private final PowerDistributionPanel PDP = new PowerDistributionPanel(0);

  private final Drivebase drivebase = new Drivebase();

  private final Shooter shooter = new Shooter();

  private final Indexer indexer = new Indexer();

  private final Intake intake = new Intake();

  private final Joystick controller = new Joystick(0);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    drivebase.setDefaultCommand(new EastDrive(
      drivebase,
      () -> applyDeadband(-controller.getRawAxis(ControllerConstants.LEFT_JOYSTICK)),
      () -> applyDeadband(-controller.getRawAxis(ControllerConstants.RIGHT_JOYSTICK)),
      () -> applyDeadband((controller.getRawAxis(ControllerConstants.RIGHT_TRIGGER) + 1) / 2)
    ));
    shooter.setDefaultCommand(new ZeroHood(shooter));

    JoystickButton leftBumper = new JoystickButton(controller, ControllerConstants.LEFT_BUMPER);
    JoystickButton rightBumper = new JoystickButton(controller, ControllerConstants.RIGHT_BUMPER);
    JoystickButton leftTrigger = new JoystickButton(controller, ControllerConstants.LEFT_TRIGGER);
    JoystickButton aButton = new JoystickButton(controller, ControllerConstants.A_BUTTON);
    JoystickButton bButton = new JoystickButton(controller, ControllerConstants.B_BUTTON);

    leftBumper.whileHeld(new ParallelCommandGroup(
      new TurnToGoal(drivebase),
      new Shoot(shooter, 5500),
      new SequentialCommandGroup(new WaitCommand(0.75), new Index(indexer, 0.3, 0.75))
    ));
    rightBumper.whenPressed(new InstantCommand(() -> drivebase.reverse()));
    leftTrigger.whenPressed(new InstantCommand(() -> intake.setIntakeSpeed(Math.max(drivebase.getSpeed(), 0.6))));
    leftTrigger.whenReleased(new InstantCommand(() -> intake.setIntakeSpeed(0)));
    aButton.whenPressed(new InstantCommand(() -> intake.extend()));
    bButton.whenPressed(new InstantCommand(() -> intake.retract()));
  }

  public void setRumble(double rumble) {
    controller.setRumble(RumbleType.kLeftRumble, rumble);
    controller.setRumble(RumbleType.kRightRumble, rumble);
  }
  
  public Command getAutonomousCommand() {
    return new ZeroHood(shooter);
  }

  public Command getTestCommand() {
    return new RunTests(PDP, drivebase, intake, indexer, shooter);
  }

  private double applyDeadband(double value) {
    double deadband = ControllerConstants.JOYSTICK_DEADBAND;

    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
}
