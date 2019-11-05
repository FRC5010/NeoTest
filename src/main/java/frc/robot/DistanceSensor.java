package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceSensor extends Subsystem {
	
	public static double rightDPP = 0.5 * Math.PI / 252;
	// Uses 120 pulses per rotation from k1X 	
	
	public DistanceSensor() {

		Robot.leftEncoder.setPosition(0);
		Robot.rightEncoder.setPosition(0);
		
		Robot.leftEncoder.setPositionConversionFactor(rightDPP);
		Robot.rightEncoder.setPositionConversionFactor(rightDPP);
		
		//Robot.rightEncoder.setInverted(true);		
	}

	public DistanceSensor(String name) {
		super(name);
	}
	public double getLeftDistance() {
		SmartDashboard.putNumber("left Distance",Robot.leftEncoder.getPosition());
		return Robot.leftEncoder.getPosition();
	}

	public double getRightDistance() {
		//set inverted not working rightside needs to be inverted negated for now
		SmartDashboard.putNumber("right Distance", -Robot.rightEncoder.getPosition());
		return Robot.rightEncoder.getPosition();
	}
	
	public double getRightRate() {
		SmartDashboard.putNumber("right encoder getRate", Robot.rightEncoder.getVelocity());
		return Robot.rightEncoder.getVelocity();
	}

	public double getLeftRate() {
		SmartDashboard.putNumber("left encoder getRate", Robot.leftEncoder.getVelocity());
		return Robot.leftEncoder.getVelocity();
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
