// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.subsystems.Drivebase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToGoal extends CommandBase {
  private Drivebase drivebase;

  public TurnToGoal(Drivebase drivebase) {
    this.drivebase = drivebase;

    addRequirements(drivebase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnSpeed = SmartDashboard.getNumber("goal_angle", 0) / 20;
    turnSpeed = MathUtil.clamp(Math.max(Math.abs(turnSpeed), 0.1) * Math.signum(turnSpeed), -0.8, 0.8);

    drivebase.curvatureDrive(0, turnSpeed, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}