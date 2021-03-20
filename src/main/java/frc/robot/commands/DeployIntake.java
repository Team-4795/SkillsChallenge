// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DeployIntake extends CommandBase {
  private final Intake intake;
  /** Creates a new EmployIntake. */
  public DeployIntake(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.motors(-0.5);
    // intake.out();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // intake.in();
    intake.motors(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; //need to say smthg aht when button is pressed?
  }
}