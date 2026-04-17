package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Drive;

public class ArmControl {
    private OpMode opMode;
    private double latchPos;
    private double slidePower;
    // under construction
    public ArmControl(OpMode o) {
        opMode = o;
    }
    public double getLatchPos() {
        if (opMode.gamepad2.dpad_right) {
            latchPos = 0.5;
        } else {
            latchPos = 0;
        } // if x pressed release else stay clamped
        return latchPos;
    }
    public double getSlidePos() {
        if (opMode.gamepad2.dpad_up) {
            slidePower = 0.2;
        } else if (opMode.gamepad2.dpad_down) {
            slidePower = 0.2;
        } else {
            slidePower = 0;
        }
        return slidePower;
    }
}
