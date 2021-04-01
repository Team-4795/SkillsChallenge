// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivebase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.controller.PIDController;

import java.lang.Math;

public class DriveToAngle extends CommandBase {
  /** Creates a new driveToAngle. */

  private double speed;
  // private double angle;
  private Drivebase drivebase;

  private double turnSpeed;

  private final PIDController controller = new PIDController(0.0333, 0.00, 0.0);

  // private AHRS gyro;

  public DriveToAngle(Drivebase drivebase, double speed /*, double angle */ ) {
    
    this.speed = speed;
    // this.angle = angle;
    this.drivebase = drivebase;

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(drivebase);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // drivebase.resetHeading();
    controller.reset();
    // controller.setIntegratorRange(-0.5, 0.5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turnSpeed = controller.calculate(SmartDashboard.getNumber("goal_angle", 0), 0);
    drivebase.arcadeDrive(0, turnSpeed * speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return (angle - 3) < gyro.getAngle() && gyro.getAngle() < (angle + 3);
    return Math.abs(SmartDashboard.getNumber("goal_angle", 0)) < 3;
    // 3 is just a buffer or something
    // return false;
  }
}