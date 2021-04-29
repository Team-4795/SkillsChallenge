// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int LEADER_HOOD_SPARK = 12;
	public static final int FOLLOWER_HOOD_SPARK = 11;
	public static final int HOOD_LIMIT = 0;

    public static final int LEADER_FLYWHEEL_TALON = 6;
	public static final int FOLLOWER_FLYWHEEL_TALON = 7;

	public static final int ACCELERATOR_SPARK = 8;

	public static final int INTAKE_SPARK = 0;

	public static final int COMPRESSOR = 0;

	public static final int FORWARD_SOLENOID = 6;
	public static final int REVERSE_SOLENOID = 7;

	public static final int INDEXER_VICTOR = 9;
	public static final int SELECTOR_VICTOR = 10;

	public static final int DRIVETRAIN_LEFT_LEADER = 4;
	public static final int DRIVETRAIN_LEFT_FOLLOWER = 5;
	public static final int DRIVETRAIN_RIGHT_LEADER = 2;
	public static final int DRIVETRAIN_RIGHT_FOLLOWER = 3;

	public static final int LEFT_TRIGGER = 0;
	public static final int RIGHT_TRIGGER = 3;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int LEFT_JOYSTICK = 1;
	public static final int RIGHT_JOYSTICK = 4;

	public static final double JOYSTICK_DEADBAND = 0.05;
}
