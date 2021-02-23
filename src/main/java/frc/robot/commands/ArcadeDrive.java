// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivebase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;


/** Arcade drive command for drivebase subsystem */
public class ArcadeDrive extends CommandBase {
  private final Drivebase drivebase;
  private final Supplier<Double> speedSupplier;
  private final Supplier<Double> rotationSupplier;

  public ArcadeDrive(Drivebase drivebase, 
                      Supplier<Double> xaxisSpeedSupplier,
                      Supplier<Double> zaxisRotateSupplier) {
    
    this.drivebase = drivebase;
    speedSupplier = xaxisSpeedSupplier;
    rotationSupplier = zaxisRotateSupplier;
                      
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivebase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivebase.arcadeDrive(speedSupplier.get(), rotationSupplier.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
