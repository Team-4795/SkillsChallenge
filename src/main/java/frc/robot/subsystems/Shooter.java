// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
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

  CANSparkMax hoodRight = new CANSparkMax(Constants.HOOD_RIGHT, MotorType.kBrushed);
  CANSparkMax hoodLeft = new CANSparkMax(Constants.HOOD_LEFT, MotorType.kBrushed);

  CANDigitalInput hoodLimit;

  private final static double scale = 292.571;

  private final static double encoderRotationsPerHoodDegree = (1.0/(22.0/285.0)/360.0);

  private final int currentLimitHood = 5;

  

  CANPIDController acceleratController; 
  CANPIDController hoodController;

  double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM; 
  double p, i, d, iz, ff, maxOutput, minOutput, maxrpm;

  CANEncoder acceleratorEncoder;
  CANEncoder hoodEncoder;



  public Shooter() {
    acceleratorWheel.restoreFactoryDefaults();
    hoodRight.restoreFactoryDefaults();

    

    acceleratController = acceleratorWheel.getPIDController();
    acceleratorEncoder = acceleratorWheel.getEncoder();

    

    // PID coefficients --> NOT TESTED, RANDOM
    kP = 0.01; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.1; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5500;

    //TODO --> TUNE PID
    p = 8; 
    i = 0.0002;
    d = 0; 
    iz = 0; 
    maxOutput = .25; 
    minOutput = -.25;

    // set PID coefficients
    acceleratController.setP(kP);
    acceleratController.setI(kI);
    acceleratController.setD(kD);
    acceleratController.setIZone(kIz);
    acceleratController.setFF(kFF);
    acceleratController.setOutputRange(kMinOutput, kMaxOutput);

    hoodController = hoodRight.getPIDController();
    hoodEncoder = hoodRight.getEncoder(EncoderType.kQuadrature, 8192);   hoodEncoder.setInverted(true);
    hoodEncoder.setPosition(0);
    hoodLimit = hoodRight.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    hoodRight.setSoftLimit(SoftLimitDirection.kReverse, -1.3f); 
    hoodRight.enableSoftLimit(SoftLimitDirection.kReverse, true);
    hoodController.setP(p);
    hoodController.setI(i);
    hoodController.setD(d);
    hoodController.setIZone(iz);
    hoodController.setOutputRange(minOutput, maxOutput);
    hoodLeft.follow(hoodRight, true);
    

    mainFlywheel1.setInverted(true);
    mainFlywheel2.follow(mainFlywheel1);
    mainFlywheel1.configFactoryDefault();

    mainFlywheel1.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    mainFlywheel2.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

    mainFlywheel1.config_kF(0, 0.0485, 0);
    mainFlywheel1.config_kP(0, 0.016, 0);
		mainFlywheel1.config_kI(0, 0, 0);
    mainFlywheel1.config_kD(0, 0, 0);

  }

  public void resetHoodEncoder() {
    hoodEncoder.setPosition(0.0);
  }

  public void setHood(double speed) {
    hoodRight.set(speed);
  }

  public void setHoodAngle(double degrees) {

    //TODO FIX DEGREES
      hoodController.setReference(-degrees*encoderRotationsPerHoodDegree, ControlType.kPosition);
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

  public void setAcceleratorRPM(double speed){
    acceleratController.setReference(speed, ControlType.kVelocity);
  }

  @Override
  public void periodic() {
    if (hoodLimit.get()) {
      hoodEncoder.setPosition(0);
    }
    SmartDashboard.putNumber("speed in RPM",(mainFlywheel1.getSelectedSensorVelocity())/2048.0*600);
    SmartDashboard.putNumber("encoderTiks", hoodEncoder.getPosition());
    SmartDashboard.putNumber("degrees", -hoodEncoder.getPosition()/ encoderRotationsPerHoodDegree);
    SmartDashboard.putBoolean("limit switch status", hoodLimit.get());
    // SmartDashboard.putNumber("rev limit switch", hoodRight.isRevLimitSwitchClosed());
    // This method will be called once per scheduler run
  }
}
//(mainFlywheel1.getSelectedSensorVelocity())/2048.0*600
