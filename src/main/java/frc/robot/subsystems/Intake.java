// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private PWMSparkMax intakeMotor = new PWMSparkMax(0); 
  private DoubleSolenoid extensionSolenoid = new DoubleSolenoid(6,7);
  public Intake(){}
  public void motors(double speed){
    intakeMotor.set(speed);
  }
  public void out() { 
    extensionSolenoid.set(Value.kForward);
  }
  public void in() {
    extensionSolenoid.set(Value.kOff);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
