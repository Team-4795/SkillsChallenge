// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
  /** Creates a new Indexer. */

  VictorSPX indexerMotor = new VictorSPX(9);
  VictorSPX selectorMotor = new VictorSPX(10);

  public Indexer() {}

  public void setIndexerSpeed(double indexerSpeed, double selectorSpeed) {
    indexerMotor.set(VictorSPXControlMode.PercentOutput, indexerSpeed);
    selectorMotor.set(VictorSPXControlMode.PercentOutput, selectorSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
