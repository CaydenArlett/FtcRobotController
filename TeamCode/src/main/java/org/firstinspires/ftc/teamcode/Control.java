package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Control {
    static double trigerLeft = 0.1;
    static double trigerRight = 0.1;
    static double leftStick = 0.07;
    static double rightStick = 0.07;
    private OpMode opMode;

    private Drive drive;
    private Shoot shoot;
    public Control(OpMode x, Drive b, Shoot c){
        opMode = x;
        drive = b;
        shoot = c;
    }
    public motorPowers getPower() {
        double x = 0;
        double y = 0;
        double rotate = 0;
        if (opMode.gamepad1.left_trigger > trigerLeft){
            y-=opMode.gamepad1.left_trigger;
        }
        if (opMode.gamepad1.right_trigger > trigerRight){
            y+=opMode.gamepad1.right_trigger;
        }
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
        if (opMode.gamepad1.y){
            drive.setMode(true);
        }
        if (opMode.gamepad1.x){
            drive.setMode(false);
        }
        if (opMode.gamepad1.dpad_right){
            if (opMode.gamepad1.dpad_down){
                shoot.targetV = 0.89;
            }
            else if (!opMode.gamepad1.dpad_up){
                shoot.targetV = 0.92;
            }
        }
        else if (opMode.gamepad1.dpad_left){
            if (opMode.gamepad1.dpad_up){
                shoot.targetV = 0.83;
            }
            else if (!opMode.gamepad1.dpad_down){
                shoot.targetV = 0.8;
            }
        }
        else if (opMode.gamepad1.dpad_up){
            shoot.targetV = 0.86;
        }
        if (opMode.gamepad1.dpad_down && opMode.gamepad1.dpad_left){
            drive.intake1(1);
        }
        else drive.intake1(0);
        if (opMode.gamepad1.dpad_down && opMode.gamepad1.dpad_right){
            drive.intake2(1);
            drive.intake1(1);
        }
        if (opMode.gamepad1.dpad_down && !opMode.gamepad1.dpad_right && !opMode.gamepad1.dpad_left){
            shoot.targetV = 0;
        }
        else drive.intake2(0);
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
