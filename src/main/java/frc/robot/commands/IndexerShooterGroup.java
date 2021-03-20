// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IndexerShooterGroup extends ParallelCommandGroup {
  /** Creates a new IndexerSelectorShooterGroup. 
   * 
   * @param indexer
   * @param shooter
   * 
  */

  

  public IndexerShooterGroup(double indexerDelay, Indexer indexer, double indexerSpeed, double selectorSpeed, Shooter shooter, double shooterSpeed) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new IndexerWithDelay(indexerDelay, indexer, indexerSpeed, selectorSpeed),
      new ShooterFixedSpeed(shooter, shooterSpeed)
    );
  }
}
