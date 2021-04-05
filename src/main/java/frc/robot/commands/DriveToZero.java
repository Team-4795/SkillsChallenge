// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.controller.PIDController;

import java.lang.Math;

public class DriveToZero extends CommandBase {
  /** Creates a new driveToAngle. */

  private double speed;
  private double initialAngle;
  private Drivebase drivebase;

  private double turnSpeed;

  private final PIDController controller = new PIDController(0.0333, 0.00, 0.0);

  private AHRS gyro;

  public DriveToZero(Drivebase drivebase, double speed) {
    
    this.speed = speed;
    this.drivebase = drivebase;

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(drivebase);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    controller.reset();

    // controller.setIntegratorRange(-0.5, 0.5);
    // this is currently unnecessary

    initialAngle = gyro.getAngle();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turnSpeed = controller.calculate(gyro.getAngle() - initialAngle, -1 * initialAngle);
    drivebase.arcadeDrive(0, turnSpeed * speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    // May need something here
  }
}