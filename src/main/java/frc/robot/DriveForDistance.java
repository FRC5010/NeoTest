package frc.robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForDistance extends PIDCommand {

	private double currentAngle = 0;
	private double startAngle = 0;
	private static double p = 0.05;
	private static double i = 0.0;
	private static double d = 0.005;
	private static double tolerance = 2;
	private int stopCount = 0;

	public DriveForDistance() {
		super("DriveForDistance", p, i, d);
		getPIDController().setInputRange(-5000, 5000);
		getPIDController().setOutputRange(-.2, .2);
	}

	public DriveForDistance(double distance) {
		super("DriveForDistance", p, i, d);
		getPIDController().setInputRange(-5000, 5000);
		getPIDController().setOutputRange(-.2, .2);
		getPIDController().setSetpoint(distance);
	}

	public DriveForDistance(double distance, double angle) {
		super("DriveForDistance", p, i, d);
		getPIDController().setInputRange(-5000, 5000);
		getPIDController().setOutputRange(-.2, .2);
		getPIDController().setSetpoint(distance);
		setAngle(angle);
	}

	public void setPoint(double setPoint) {
		setSetpoint(setPoint);
	}

	public void setAngle(double setAngle) {
		startAngle = setAngle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		getPIDController().setAbsoluteTolerance(tolerance);
		SmartDashboard.putNumber("startAngle", startAngle);
		Robot.distance.reset();
		getPIDController().enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		currentAngle = Robot.gyro.getAngle();
		SmartDashboard.putNumber("currentAngle", currentAngle);
		SmartDashboard.putNumber("Error", getPIDController().getError());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (getPIDController().onTarget()) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive(0,0);
		getPIDController().reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

	@Override
	protected double returnPIDInput() {
		double distance = Robot.distance.getRightDistance();
		SmartDashboard.putNumber("distance", distance);
		return distance;

	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("output", output);
		// RobotMap.drivetrain.drive(output, output);
		double leftOutput = output + ((startAngle - currentAngle) / 45);
		double rightOutput = output - ((startAngle - currentAngle) / 45);
		SmartDashboard.putNumber("leftOutput", leftOutput);
		SmartDashboard.putNumber("rightOutput", rightOutput);
		Robot.drive(leftOutput, rightOutput);
	}
}