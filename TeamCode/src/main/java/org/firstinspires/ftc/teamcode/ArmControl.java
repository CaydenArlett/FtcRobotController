package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Drive;

public class ArmControl {
    private OpMode opMode;
    private Drive robot;
    private double latchPower;
    private double leftClawPos;
    private double rightClawPos;
    // under construction
    public ArmControl(OpMode o, Drive r) {
        opMode = o;
        robot = r;
    }
    public double getLatchPower() {
        if (opMode.gamepad2.x) {
            latchPower = 0.5;
        } else {
            latchPower = 0;
        } // if a pressed release else stay clamped
        return latchPower;
    }

    // im not actually sure what the actual values for closed and open are but we can always program that later
    public double getLeftClawPos() {
        if (opMode.gamepad2.left_bumper) {
            leftClawPos = 0.5;
        } else {
            leftClawPos = 0;
        }
        return leftClawPos;
    }
    public double getRightClawPos() {
        if (opMode.gamepad2.right_bumper) {
            rightClawPos = 0.5;
        } else {
            rightClawPos = 1;
        }
        return rightClawPos;
    }
}
