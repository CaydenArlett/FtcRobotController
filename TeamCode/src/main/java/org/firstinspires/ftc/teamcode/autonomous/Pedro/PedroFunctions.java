package org.firstinspires.ftc.teamcode.autonomous.Pedro;

//import static org.firstinspires.ftc.teamcode.ButtonMaps.Arm.FirstAgeGoodArm.velocityShot;
//import static org.firstinspires.ftc.teamcode.ButtonMaps.Drive.LiamPolarDriveGood.pid;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.motorPowers;
//import org.firstinspires.ftc.teamcode.ComplexRobots.ServoGoodBot;
//import org.firstinspires.ftc.teamcode.limelightData;


public class PedroFunctions {
    private motorPowers mp;// = new MotorPowers(0);
    private double servoPosition;
    private double stage = 0;
    private double timeSince;
    private double timeBuffer = 3000;

    //These magic numbers are not final and should be iteratively tested.
    public static double baseShotPower = .40;
    public static double limelightPowerMultiplier = 1.18;
    public static double limelightBaseDistance = 100;
    public static double nonLinearPower = 1.0028;
    public static double shootVel;
    public static double targetVel;
    static double joystickDeadZone = .1;
    static double aimingThreshold = .015;

//    public static void shoot(ServoGoodBot robot) {
//
//        shootVel = robot.shootMotor.getVelocity();
//        targetVel = velocityShot(limelightData.distance);
//        robot.shootMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
////        robot.shootMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        //This is meant to shoot according to the distance to the april tag if the limelight is accurate            //All of these variables are yet to be tested and should be iterated on
//        //robot.ShootMotor.setPower(limelightData.accurate ? limelightPowerMultiplier * Math.pow(nonLinearPower, limelightData.distance) * baseShotPower : baseShotPower * 1.5);
////        if (limelightData.accurate) {
////            if (Math.abs(targetVel - shootVel) < 40) {
////                robot.intakeMotor1.setPower(.75);
////                robot.intakeMotor2.setPower(.8);
////            }
////            robot.ShootMotor.setVelocity(velocityShot(limelightData.distance));
////        } else {
//            if (Math.abs(velocityShot(272) - shootVel) < 55) {
//                robot.intakeMotor1.setPower(.8);
//                robot.intakeMotor2.setPower(1);
//            }
//            else {
//                robot.intakeMotor1.setPower(.4);
//                robot.intakeMotor2.setPower(0);
//            }
//            robot.shootMotor.setVelocity(velocityShot(272));
////            robot.shootMotor2.setVelocity(velocityShot(275));
////        }
//    }
//
//    public static void aim(ServoGoodBot robot) {
//        if (Math.abs(limelightData.aprilXDegrees / 20) < aimingThreshold && limelightData.accurate) {
//            robot.setMotorPowers(new MotorPowers(0, 0, 0, 0));
//        } else if ((Math.abs(limelightData.aprilXDegrees / 20) >= aimingThreshold) && limelightData.accurate) {
//            robot.leftFront.setPower(-pid.output());
//            robot.leftBack.setPower(-pid.output());
//            robot.rightFront.setPower(pid.output());
//            robot.rightBack.setPower(pid.output());
//        }
//    }
//
//
//
//    public static void intake(ServoGoodBot robot) {
//        robot.intakeMotor1.setPower(1);
//    }
//
//    public static void reset(ServoGoodBot robot) {
//        robot.intakeMotor1.setPower(0);
//        robot.intakeMotor2.setPower(0);
//        robot.shootMotor.setVelocity(0);
////        robot.shootMotor2.setPower(0);
//
//    }

//    public static PathChain createHeading(Pose startPos, Pose endPos){
//        PathChain drive = follower.pathBuilder()
//                .addPath(new BezierLine(startPos, endPos))
//                .setLinearHeadingInterpolation(startPos.getHeading(), endPos.getHeading())
//                .build();
//        return drive;
//    }

//    public static PathChain turn(double degrees, Follower follower, double startX, double startRadians){
//        Pose startPos = new Pose (startX, 14, startRadians);
//        Pose endPos = new Pose(startX - .2, 14 + (9.17429/10 * (limelightData.aprilXDegrees) / Math.abs(limelightData.aprilXDegrees)), startRadians-Math.toRadians(limelightData.aprilXDegrees));
//        PathChain drive = follower.pathBuilder()
//                .addPath(new BezierLine(startPos, endPos))
//                .setLinearHeadingInterpolation(startPos.getHeading(), endPos.getHeading())
//                .build();
//        return drive;
//    }
}
