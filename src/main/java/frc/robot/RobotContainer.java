// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.IndexerShooterGroup;
import frc.robot.commands.DeployIntake;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Indexer;
import frc.robot.commands.ShooterFixedSpeed;
import frc.robot.commands.ShooterRPM;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
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

  private final Intake intake = new Intake(); 

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
    drivebase.setDefaultCommand(new ArcadeDrive(
                                  drivebase,
                                   () -> controller.getRawAxis(1), 
                                  () -> controller.getRawAxis(2)));
    shooter.setDefaultCommand(new ShooterFixedSpeed(shooter, 0.0));

    JoystickButton buttonC = new JoystickButton(controller, 10); //10 is a guess
    // buttonC.whenHeld(new ShooterFixedSpeed(shooter, 0.8));
    buttonC.whenHeld(new IndexerShooterGroup(0.5, indexer, 0.4, 0.4, shooter, 0.8));
    //0.5 second indexer delay, 0.4 on indexer motor, 0.4 on selector motor, 0.8 on shooter
  }


    Command command = new DeployIntake(intake);
    final JoystickButton buttonA = new JoystickButton(controller,12);
    buttonA.whenPressed(command);
    final JoystickButton buttonB = new JoystickButton(controller,11);
    buttonB.cancelWhenPressed(command);
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new ShooterFixedSpeed(shooter, 0.4);
  }

}
