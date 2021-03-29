// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class Drivebase extends SubsystemBase {
  
  private CANSparkMax leftLeader = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax leftFollower = new CANSparkMax(5, MotorType.kBrushless);

  private CANSparkMax rightLeader = new CANSparkMax(2, MotorType.kBrushless);
  private CANSparkMax rightFollower = new CANSparkMax(3, MotorType.kBrushless);

  private DifferentialDrive diffDrive = new DifferentialDrive(leftLeader, rightLeader);

  private CANEncoder leftEncoder, rightEncoder;

  public AHRS gyro;

  public Drivebase() {
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();

    gyro = new AHRS(SPI.Port.kMXP);

  }

  public void arcadeDrive(double speed, double rotation) {
    diffDrive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left encoder", leftEncoder.getPosition());
    SmartDashboard.putNumber("Right encoder", rightEncoder.getPosition());
  }

  public void resetHeading() {
    // Not currently used in driveToAngle
    gyro.zeroYaw();
    gyro.setAngleAdjustment(-gyro.getAngle());
  }

  public Rotation2d getHeading() {
    return Rotation2d.fromDegrees(gyro.getAngle());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
