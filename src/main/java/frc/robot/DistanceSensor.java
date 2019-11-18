package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceSensor extends Subsystem {
	
	public static double effectiveWheelSize = 0.5155;
	public static double DPP = 1;
	public static double velConvFactor = 1;
	public static double gearRatio = 16.37;
	// Uses CPR output to RioLog 	
	
	public DistanceSensor() {
		DPP = effectiveWheelSize * Math.PI / ((double)Robot.leftEncoder.getCPR() * gearRatio);
		SmartDashboard.putNumber("DPP", DPP);
		velConvFactor = DPP / 60.0;
		reset();		
	}

	public DistanceSensor(String name) {
		super(name);
	}
	public double getLeftDistance() {
		double distance = Robot.leftEncoder.getPosition() * DPP;
		SmartDashboard.putNumber("left Distance", distance);
		return distance;
	}

	public double getRightDistance() {
		//set inverted not working rightside needs to be inverted negated for now
		double distance = -Robot.rightEncoder.getPosition() * DPP;
		SmartDashboard.putNumber("right Distance", distance);
		return distance;
	}
	
	public double getRightRate() {
		double velocity = -Robot.rightEncoder.getVelocity() * velConvFactor;
		SmartDashboard.putNumber("right encoder getRate", velocity);
		return velocity;
	}

	public double getLeftRate() {
		double velocity = -Robot.leftEncoder.getVelocity() * velConvFactor;
		SmartDashboard.putNumber("left encoder getRate", velocity);
		return velocity;
	}

	public void reset() {
		Robot.rightEncoder.setPosition(0);
		Robot.leftEncoder.setPosition(0);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
