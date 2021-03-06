/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.dynasty.SparkMaxConfig;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot{
  public static CANSparkMax d1;
  public static CANSparkMax d2;
  public static CANSparkMax d3;
  public static CANSparkMax d4;
  public static SpeedControllerGroup left;
  public static SpeedControllerGroup right;
  public static CANEncoder leftEncoder;
  public static CANEncoder rightEncoder;
  public static Gyro gyro;
  public static PowerDistributionPanel pdp;
  public static Joystick joy;
  public static JoystickAxis fwd;
  public static JoystickAxis trn;
  private static final String kDefaultAuto = "Default";
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();
  public static long timer = 0;
  public static DistanceSensor distance;
  public static boolean running = false;
  public static SparkMaxConfig sparks;
  public static int[] sparkArr={1,2,3,4};;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
     m_chooser.setDefaultOption(kDefaultAuto, new CycleSprintTest());
     SmartDashboard.putData("Auto choices", m_chooser);
    //SparkMax  
    //only use brushless mode with can spark max, bad if not 
    //left
    // d1 = new CANSparkMax(1,MotorType.kBrushless);
    // d2 = new CANSparkMax(2,MotorType.kBrushless);
    d1 = new CANSparkMax(1,MotorType.kBrushed);
    d2 = new CANSparkMax(2,MotorType.kBrushed);
    // leftEncoder = d1.getEncoder();
    // System.out.println("Left CPR: " + leftEncoder.getCPR());
    //right
    //SparkMax
    // d3 = new CANSparkMax(3,MotorType.kBrushless);
    // d4 = new CANSparkMax(4,MotorType.kBrushless);
    d3 = new CANSparkMax(3,MotorType.kBrushed);
    d4 = new CANSparkMax(4,MotorType.kBrushed);
    rightEncoder = d3.getEncoder();
    // System.out.println("Left CPR: " + rightEncoder.getCPR());
    //sparks = new SparkMaxConfig(sparkArr,false);
    d2.follow(d1);
    d4.follow(d3);

    // Talon/Victors
    left = new SpeedControllerGroup(d1, d2);
    right = new SpeedControllerGroup(d3, d4);
    right.setInverted(true);

    joy = new Joystick(0);
    fwd = new JoystickAxis(joy, 1, true, 1);
    trn = new JoystickAxis(joy, 4, .55);

    gyro = new AnalogGyro(0);
    gyro.reset();
    distance = new DistanceSensor();
    distance.reset();
  }
  public static void drive(double forward, double turn){
    double angleP = gyro.getAngle() * .01;
    
    left.set(forward+turn);
    right.set(forward - turn); 
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // // distance.getLeftDistance();
    // // distance.getLeftRate();
    // // distance.getRightDistance();
    // // distance.getRightRate();
    // SmartDashboard.putNumber("PDP current", pdp.getTotalCurrent());
    // SmartDashboard.putNumber("Heading", gyro.getAngle());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    running = true;
    if (joy.getRawAxis(1) != 0 || joy.getRawAxis(4) != 0) {
      m_autonomousCommand.cancel();
    }
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    running = true;
   drive(joy.getRawAxis(1),joy.getRawAxis(4));
  // sparks.velDrive(joy.getRawAxis(1), joy.getRawAxis(4), 5700);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
