// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    mainFlywheel1.configFactoryDefault();

    mainFlywheel1.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

    mainFlywheel1.config_kF(0, 0, 0);
    mainFlywheel1.config_kP(0, 0, 0);
		mainFlywheel1.config_kI(0, 0, 0);
		mainFlywheel1.config_kD(0, 0, 0);
  }

  public void setShooter(double speed) {
    mainFlywheel1.set(ControlMode.PercentOutput, speed);
    acceleratorWheel.set(speed);
  }

  public void setShooterRPM(double speed){
//2048 ticks per revolution 
//ticks per .10 second 
// 1/2048*60
    double speed_FalconUnits = speed/(600.0)*2048.0;
    mainFlywheel1.set(TalonFXControlMode.Velocity, speed_FalconUnits);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("speed in RPM",(mainFlywheel1.getSelectedSensorVelocity())/2048.0*600);
    // This method will be called once per scheduler run
  }
}
//(mainFlywheel1.getSelectedSensorVelocity())/2048.0*600
