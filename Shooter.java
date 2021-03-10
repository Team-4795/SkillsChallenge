// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */

  CANSparkMax acceleratorWheel = new CANSparkMax(8, MotorType.kBrushless);
    
  TalonFX mainFlywheel1 = new TalonFX(6);
  TalonFX mainFlywheel2 = new TalonFX(7);

  // CANTalonFX mainFlywheel1 = new CANTalonFX()
    
  CANEncoder acceleratorEncoder;

  public Shooter() {
    
    acceleratorEncoder = acceleratorWheel.getEncoder();
    
    mainFlywheel2.follow(mainFlywheel1);

  }

  public void setShooter(double speed) {
    mainFlywheel1.set(ControlMode.PercentOutput, speed);
    acceleratorWheel.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
