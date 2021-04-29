// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private Spark intakeMotor = new Spark(Constants.INTAKE_SPARK); 
  private DoubleSolenoid solenoid = new DoubleSolenoid(Constants.FORWARD_SOLENOID, Constants.REVERSE_SOLENOID);
  private Compressor compressor = new Compressor(Constants.COMPRESSOR);
  
  public Intake() {}

  public void setIntakeSpeed(double speed) {
    intakeMotor.set(speed);
  }

  public void extend() { 
    solenoid.set(Value.kForward);
  }

  public void retract() {
    solenoid.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    if(compressor.getPressureSwitchValue()) {
      compressor.start();
    } else {
      compressor.stop();
    }
  }
}
