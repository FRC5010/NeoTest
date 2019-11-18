/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.dynasty;

/**
 * Add your docs here.
 */
public class Gains {
    public double kD,kP,kV,kA,kS;
    public Gains(double kP,double kD,double kV,double kA,double kS){
        this.kP = kP;
        this.kD = kD;
        this.kV = kV;
        this.kA = kA;
        this.kS = kS;
    }
}
