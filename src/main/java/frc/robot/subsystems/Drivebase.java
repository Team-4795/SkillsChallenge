// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Drivebase extends SubsystemBase {
  
  private CANSparkMax leftLeader = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax leftFollower = new CANSparkMax(5, MotorType.kBrushless);

  private CANSparkMax rightLeader = new CANSparkMax(2, MotorType.kBrushless);
  // private CANSparkMax rightFollower = new CANSparkMax(3, MotorType.kBrushless);

  private DifferentialDrive diffDrive = new DifferentialDrive(leftLeader, rightLeader);

  private CANEncoder leftEncoder, rightEncoder;

  public Drivebase() {
    leftFollower.follow(leftLeader);
    // rightFollower.follow(rightLeader);

    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();

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

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
