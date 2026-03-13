package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

static double trigerLeft = 0.1;
static double trigerRight = 0.1;

static double leftStick = 0.1;
static double rightStick = 0.1;

public class Controller {
    private OpMode opMode
    public Controller(OpMode x){
        opMode = x;
    }
    public motorPowers getPower() {
        double x = 0;
        double y = 0;
        double rotate = 0;
        if (opMode.gamepad1.left_trigger > trigerLeft){
            x-=opMode.gamepad1.left_stick_x;
        }
        if (opMode.gamepad1.right_trigger > trigerRight){
            x+=opMode.gamepad1.right_stick_x;
        }
        if (opMode.gamepad1.left_bumper){
            rotate --;
        }
        if (opMode.gamepad1.right_bumper){
            rotate ++;
        }
        if (opMode.gamepad1.left_stick_y > leftStick || opMode.gamepad1.left_stick_x > leftStick){
            x+=opMode.gamepad1.left_stick_x;
            y+=opMode.gamepad1.left_stick_y;
        }
        if (opMode.gamepad1.right_stick_y > rightStick || opMode.gamepad1.right_stick_x > rightStick) {
            rotate += opMode.gamepad1.right_stick_x;
        }

        return setPower(x, y, rotate);
    }
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
