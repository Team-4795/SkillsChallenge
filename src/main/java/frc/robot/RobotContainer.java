// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.IndexerShooterGroup;
import frc.robot.commands.IndexerWithDelay;
import frc.robot.commands.ResetHeading;
import frc.robot.commands.SetHood;
import frc.robot.commands.SetHoodAngle;
import frc.robot.commands.DeployIntake;
import frc.robot.commands.DriveToAngle;
import frc.robot.commands.DriveToAngleNavX;
import frc.robot.commands.DriveToZero;
import frc.robot.commands.IndexerCommand;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Indexer;
import frc.robot.commands.ShooterFixedSpeed;
import frc.robot.commands.ShooterRPM;
import frc.robot.commands.TurnHoodShooterGroup;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton; 
import frc.robot.subsystems.Intake;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivebase drivebase = new Drivebase();

  private final Shooter shooter = new Shooter();

  private final Indexer indexer = new Indexer();

  private final Joystick controller = new Joystick(0);

  //private final Intake intake = new Intake(); 

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    double throttle = controller.getRawButtonPressed(8) ? 1.0 : 0.0 ;
    // double forward = -controller.getRawAxis(1) * (0.85 - 0.65 * throttle);
    // double turn = -controller.getRawAxis(2) * (0.85 - 0.65 * throttle);
    drivebase.setDefaultCommand(new ArcadeDrive(
                                  drivebase, 
                                  () -> -controller.getRawAxis(1) * 0.9, 
                                  () -> -controller.getRawAxis(2) * 0.65));
    shooter.setDefaultCommand(new ShooterFixedSpeed(shooter, 0.0));

    JoystickButton leftBumper = new JoystickButton(controller, 5); //10 is a guess
    JoystickButton buttonY = new JoystickButton(controller, 1);
    JoystickButton buttonX = new JoystickButton(controller, 4);
    JoystickButton buttonB = new JoystickButton(controller, 2);
    // buttonC.whenHeld(new ShooterFixedSpeed(shooter, 0.8));
    SmartDashboard.putNumber("hood degrees", 35); 
    SmartDashboard.putNumber("Shooter RPM", 0);
    leftBumper.whenHeld(new IndexerShooterGroup(0.5, indexer, 0.15, 0.75, shooter, 4000));
     /*Drivebase drivebase, double turnSpeed, 
    Shooter shooter, double shooterSpeed, double hoodAngle, 
    Indexer indexer, double indexerDelay, double indexerSpeed, double selectorSpeed*/
    buttonY.whenHeld(new SetHood(shooter, 0.15));
    buttonX.whenHeld(new SetHood(shooter, -0.15));
    buttonB.whenHeld(new SetHoodAngle(shooter, 35));
    
    //0.5 second indexer delay, 0.25 on indexer motor, 0.75 on selector motor, 0.8 on shooter

    JoystickButton rightTrigger = new JoystickButton(controller, Constants.RIGHT_TRIGGER);
    //buttonA.whileHeld( new DeployIntake(intake));
    //reduce numbers below to reduce indexer and selector speeds
    rightTrigger.whenHeld(new ShooterRPM(shooter, 4000.0));

    Command toRun = new ParallelCommandGroup(
      new DriveToAngle(drivebase, 0.75).withTimeout(0.75),
      new IndexerWithDelay(0.25, indexer, 1, 1),
      new SetHoodAngle(shooter, 35.0)
    ); 
    
    JoystickButton leftTrigger = new JoystickButton(controller, Constants.LEFT_TRIGGER); // this may not be an actual button
    //leftTrigger.whenHeld(new TurnHoodShooterGroup(drivebase, 0.5, shooter, 4230, 30, indexer, 0.5, 0.25, 0.75));
    leftTrigger.whenHeld(toRun);
   
    JoystickButton buttonFake = new JoystickButton(controller, 99); // not an actual button
    buttonFake.whenPressed(new DriveToAngleNavX(drivebase, 0.5, 30.0).withTimeout(0.7));
    // drivebase, max turn speed, angle, 0.7 second timeout

    JoystickButton buttonD = new JoystickButton(controller, 100); //not an actual button
    buttonD.whenPressed(new ResetHeading(drivebase));
    // set 0 degree heading

    JoystickButton buttonE = new JoystickButton(controller, 101); //not an actual button
    buttonE.whenPressed(new DriveToZero(drivebase, 0.5));
    // turn to the 0 degree heading

  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new ShooterRPM(shooter, 3000);
  }

}
