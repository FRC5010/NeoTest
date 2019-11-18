/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.dynasty;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Robot;

/*
 * Add your docs here.
 */
public class SparkMaxConfig {
    public int ampLimit = 40;
    public int numSpark = 4;
    public double rampRate;
    public static CANPIDController lPidController, rPidController;
    public static CANSparkMax leftDt1,leftDt2,leftDt3,rightDt1,rightDt2,rightDt3;
    public static boolean pidInit = false;

    
        public SparkMaxConfig(int[] sparkId,boolean leftInvert){
            if (sparkId.length>6){
                System.out.println("no stop thats too many");
            }else if(!Robot.running){
                setupDt(sparkId, leftInvert);
            }
            for(int i = 0; i<sparkId.length;i++){

            }
        }
        public static void setupDt(int[] sparkId,boolean leftInvert){
           //why did i do this? im not really sure but who knows maybe we use 6 neos
            if(sparkId.length == 6){
            leftDt1 = new CANSparkMax(sparkId[0], MotorType.kBrushless);
            leftDt2 = new CANSparkMax(sparkId[1], MotorType.kBrushless);
            leftDt3 = new CANSparkMax(sparkId[2], MotorType.kBrushless);

            rightDt1 = new CANSparkMax(sparkId[3], MotorType.kBrushless);
            rightDt2 = new CANSparkMax(sparkId[4], MotorType.kBrushless);
            rightDt3 = new CANSparkMax(sparkId[5], MotorType.kBrushless);

            leftDt1.setInverted(leftInvert);
            leftDt2.setInverted(leftInvert);
            leftDt3.setInverted(leftInvert);

            rightDt1.setInverted(!leftInvert);
            rightDt2.setInverted(!leftInvert);
            rightDt3.setInverted(!leftInvert);
            
            leftDt2.follow(leftDt1);
            leftDt3.follow(leftDt1);
            rightDt2.follow(rightDt1);
            rightDt3.follow(rightDt1);

           }else if(sparkId.length == 4){
            leftDt1 = new CANSparkMax(sparkId[0], MotorType.kBrushless);
            leftDt2 = new CANSparkMax(sparkId[1], MotorType.kBrushless);

            rightDt1 = new CANSparkMax(sparkId[2], MotorType.kBrushless);
            rightDt2 = new CANSparkMax(sparkId[3], MotorType.kBrushless);
           
            leftDt1.setInverted(leftInvert);
            leftDt2.setInverted(leftInvert);

            rightDt1.setInverted(!leftInvert);
            rightDt2.setInverted(!leftInvert);
            
            leftDt2.follow(leftDt1);
            rightDt2.follow(rightDt1);
            
            

            lPidController = leftDt1.getPIDController();
            rPidController = rightDt1.getPIDController();
            

           }else{
               System.out.println("no that isnt a valid number of motors");
           }
           initPID(lPidController, new Gains(5,0,0,0,0));
           initPID(lPidController, new Gains(5,0,0,0,0));
           pidInit = true;
        }
        public static void dtLimits(){
            leftDt1.setSmartCurrentLimit(20, 20);
            leftDt1.setOpenLoopRampRate(.8);

            leftDt2.setSmartCurrentLimit(20, 20);
            leftDt2.setOpenLoopRampRate(.8);

            rightDt1.setSmartCurrentLimit(20, 20);
            rightDt1.setOpenLoopRampRate(.8);

            rightDt2.setSmartCurrentLimit(20, 20);
            rightDt2.setOpenLoopRampRate(.8);
        }
        public static void initPID(CANPIDController controler, Gains gains) {
            controler.setP(gains.kP);
            controler.setI(0);
            controler.setFF(gains.kV);
        }
        public static void velDrive(double fPow, double tPow, int maxVel) {
            //max vel in motor rpm 5700 max
            if(pidInit){
            lPidController.setReference((fPow+tPow)*maxVel, ControlType.kVelocity);
            rPidController.setReference((fPow-tPow)*maxVel, ControlType.kVelocity);
            }else{
                System.out.println("you never initilized yout pid values");
            }
        }
}
