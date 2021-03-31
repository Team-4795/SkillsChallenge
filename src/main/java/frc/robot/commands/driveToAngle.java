// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

public class driveToAngle extends CommandBase {
  /** Creates a new driveToAngle. */

  private double speed;
  private double angle;
  private double initialAngle;
  private Drivebase drivebase;

  private AHRS gyro;

  public driveToAngle(Drivebase drivebase, double speed, double angle) {
    
    this.speed = speed;
    this.angle = angle;
    this.drivebase = drivebase;

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(drivebase);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialAngle = gyro.getAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivebase.arcadeDrive(0, speed * ((angle > 0) ? 1 : -1));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (initialAngle + angle - 2) < gyro.getAngle() && gyro.getAngle() < (initialAngle + angle + 2);
    // 2 is just a buffer so it actually stops around the desired angle, since this doesn't use pid
  }
}