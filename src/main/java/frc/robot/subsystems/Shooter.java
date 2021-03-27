// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import org.graalvm.compiler.core.common.CancellationBailoutException;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */

  CANSparkMax acceleratorWheel = new CANSparkMax(8, MotorType.kBrushless);
    
  TalonFX mainFlywheel1 = new TalonFX(6);
  TalonFX mainFlywheel2 = new TalonFX(7);

  TalonSRX hoodLeft = new TalonSRX(Constants.HOOD_LEFT);
  TalonSRX hoodRight = new TalonSRX(Constants.HOOD_RIGHT);
    
  CANEncoder acceleratorEncoder;

  // Encoder hoodEncoder = new Encoder(Constants.sourceA, Constants.sourceB);

  // CANPIDController acceleratorController;

  // CANPIDController hoodPID;

  private final static double encoderCountsPerGearDegree = 1680/720;
  private final static double encoderCountsPerHoodDegree = 400/46.3;

  private final int currentLimitHood = 5;

  // private final DigitalInput hoodLimit;




  public Shooter() {
    // hoodLimit = new DigitalInput(channel);
    mainFlywheel1.setInverted(true);
    mainFlywheel2.follow(mainFlywheel1);

    // hoodRight.setInverted(true);
    hoodLeft.follow(hoodRight);
    hoodLeft.setInverted(true);

    mainFlywheel1.configFactoryDefault();
    hoodRight.configFactoryDefault();

    hoodRight.enableCurrentLimit(true);
    hoodLeft.enableCurrentLimit(true);
    hoodRight.configContinuousCurrentLimit(currentLimitHood);
    hoodLeft.configContinuousCurrentLimit(currentLimitHood);

    mainFlywheel1.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    mainFlywheel2.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    hoodRight.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.QuadEncoder, 0, 0);

    hoodRight.config_kP(0, 0.5, 0);
    hoodRight.config_kI(0, 0.0002, 0);
    hoodRight.config_kD(0, 0, 0);

    mainFlywheel1.config_kF(0, 0.05, 0);
    mainFlywheel1.config_kP(0, 0.02, 0);
		mainFlywheel1.config_kI(0, 0, 0);
    mainFlywheel1.config_kD(0, 0, 0);
  }

  public void resetHoodEncoder() {
    hoodRight.setSelectedSensorPosition(0, 0, 10);
  }

  public void setHood(double speed) {
    hoodRight.set(ControlMode.PercentOutput, speed);
  }

  public void setHoodAngle(double degrees) {
      hoodRight.set(TalonSRXControlMode.Position, 1000);
  }

  public TalonSRX getHood() {
    return hoodRight;
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
    acceleratorWheel.set(speed/6000.0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("speed in RPM",(mainFlywheel1.getSelectedSensorVelocity())/2048.0*600);
    SmartDashboard.putNumber("encoderTiks", hoodRight.getSelectedSensorPosition());
    SmartDashboard.putNumber("degrees", hoodRight.getSelectedSensorPosition()/encoderCountsPerDegree);
    SmartDashboard.putNumber("limit switch status", hoodRight.isFwdLimitSwitchClosed());
    SmartDashboard.putNumber("rev limit switch", hoodRight.isRevLimitSwitchClosed());
    // This method will be called once per scheduler run
  }
}
//(mainFlywheel1.getSelectedSensorVelocity())/2048.0*600
