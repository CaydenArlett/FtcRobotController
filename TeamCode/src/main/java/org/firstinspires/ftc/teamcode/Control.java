package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Control {
    static double triggerLeft = 0.1;
    static double triggerRight = 0.1;
    static double leftStick = 0.07;
    static double leftStick2 = 0.07;
    static double rightStick = 0.07;
    static double triggerLeft2 = 0.07;
    static double triggerRight2 = 0.07;
    static double armPower = 0.1;

    static double armExtendPos = 20000;
    private OpMode opMode;
    private Drive drive;
    public Control(OpMode x, Drive b){
        opMode = x;
        drive = b;
    }
    public motorPowers getPower() {
        double x = 0;
        double y = 0;
        double rotate = 0;
        if (opMode.gamepad1.left_bumper){
            rotate --;
        }
        if (opMode.gamepad1.right_bumper){
            rotate ++;
        }
        if (Math.abs(opMode.gamepad1.left_stick_y) > leftStick || Math.abs(opMode.gamepad1.left_stick_x) > leftStick){
            x+=opMode.gamepad1.left_stick_x;
            y-=opMode.gamepad1.left_stick_y;
        }
        if (Math.abs(opMode.gamepad1.right_stick_y) > rightStick || Math.abs(opMode.gamepad1.right_stick_x) > rightStick) {
            rotate += opMode.gamepad1.right_stick_x;
        }

        //switches arm servo states
        if (opMode.gamepad2.aWasPressed()){
            drive.switchLeft();
        }
        if (opMode.gamepad2.bWasPressed()){
            drive.switchRight();
        }
        if (opMode.gamepad2.left_trigger> triggerLeft2){
            drive.switchUp(opMode.gamepad2.left_trigger);
        }
        if (opMode.gamepad2.right_trigger> triggerRight2) {
            drive.switchUp(-opMode.gamepad2.right_trigger);
        }
        //extends arm
        if (opMode.gamepad2.dpad_down && opMode.gamepad2.dpad_right) {
            drive.moveArm(armExtendPos);     // 100%
        } else if (opMode.gamepad2.dpad_right) {
            drive.moveArm(armExtendPos * 0.875);   // 87.5%
        } else if (opMode.gamepad2.dpad_up && opMode.gamepad1.dpad_right) {
            drive.moveArm(armExtendPos * 0.75);    // 75%
        } else if (opMode.gamepad2.dpad_up) {
            drive.moveArm(armExtendPos * 0.625);   // 62.5%
        } else if (opMode.gamepad2.dpad_up && opMode.gamepad1.dpad_left) {
            drive.moveArm(armExtendPos * 0.50);    // 50%
        } else if (opMode.gamepad2.dpad_left) {
            drive.moveArm(armExtendPos * 0.375);   // 37.5%
        } else if (opMode.gamepad2.dpad_down && opMode.gamepad1.dpad_left) {
            drive.moveArm(armExtendPos * 0.25);    // 25%
        } else if (opMode.gamepad2.dpad_down) {
            drive.moveArm(0);                      // 0%
        }

        //extends arm
        else if (opMode.gamepad2.left_stick_y > leftStick2 || opMode.gamepad2.left_stick_y < -leftStick2) {
            drive.spinArm(-opMode.gamepad2.left_stick_y*armPower);
        }
        else {
            drive.spinArm(0);
        }
//        if (opMode.gamepad1.x){
//            drive.setMode(false);
//        }
        if (opMode.gamepad2.xWasPressed()){
            drive.resetArm();
        }
        return setPower(x, y, rotate);
    }

    //converts move forward right and turning power to a single set of motor powers
    public motorPowers setPower(double x, double y, double rotate) {
        double denominator = Math.max(Math.abs(x)+Math.abs(y)+Math.abs(rotate),1);
        motorPowers power = new motorPowers(0);
        power.leftFront = (y+x+rotate)/denominator;
        power.rightFront = (y-x-rotate)/denominator;
        power.leftBack = (y-x+rotate)/denominator;
        power.rightBack = (y+x-rotate)/denominator;
        return power;
    }
}
