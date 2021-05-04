// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivebaseConstants;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class Drivebase extends SubsystemBase {
  
  private CANSparkMax leftLeader = new CANSparkMax(DrivebaseConstants.LEFT_LEADER, MotorType.kBrushless);
  private CANSparkMax leftFollower = new CANSparkMax(DrivebaseConstants.LEFT_FOLLOWER, MotorType.kBrushless);

  private CANSparkMax rightLeader = new CANSparkMax(DrivebaseConstants.RIGHT_LEADER, MotorType.kBrushless);
  private CANSparkMax rightFollower = new CANSparkMax(DrivebaseConstants.RIGHT_FOLLOWER, MotorType.kBrushless);

  private DifferentialDrive diffDrive = new DifferentialDrive(leftLeader, rightLeader);

  private CANEncoder leftEncoder, rightEncoder;

  public double movementSpeed = 0;
  public int direction = 1;

  public AHRS gyro;

  public Drivebase() {
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();

    gyro = new AHRS(SPI.Port.kMXP);

    diffDrive.setDeadband(0.0);
  }

  public void curvatureDrive(double speed, double rotation, boolean quickTurn) {
    movementSpeed = Math.max(Math.abs(speed), Math.abs(rotation));

    diffDrive.curvatureDrive(speed * direction, rotation, quickTurn);
  }

  public void reverse() {
    if(Math.abs(movementSpeed) < 0.25) direction *= -1;
  }

  @Override
  public void periodic() {
    if(movementSpeed == 0 && getVelocity() < 3500) {
      leftLeader.setIdleMode(IdleMode.kBrake);
      leftFollower.setIdleMode(IdleMode.kBrake);
      rightLeader.setIdleMode(IdleMode.kBrake);
      rightFollower.setIdleMode(IdleMode.kBrake);
    } else {
      leftLeader.setIdleMode(IdleMode.kCoast);
      leftFollower.setIdleMode(IdleMode.kCoast);
      rightLeader.setIdleMode(IdleMode.kCoast);
      rightFollower.setIdleMode(IdleMode.kCoast);
    }

    SmartDashboard.putNumber("Left drivebase encoder", leftEncoder.getPosition());
    SmartDashboard.putNumber("Right drivebase encoder", rightEncoder.getPosition());
    SmartDashboard.putNumber("Drivebase speed", rightEncoder.getPosition());
  }

  public void resetHeading() {
    gyro.zeroYaw();
    gyro.setAngleAdjustment(-gyro.getAngle());
  }

  public Rotation2d getHeading() {
    return Rotation2d.fromDegrees(gyro.getAngle());
  }

  public double getVelocity() {
    return Math.max(Math.abs(leftEncoder.getVelocity()), Math.abs(rightEncoder.getVelocity()));
  }
}
