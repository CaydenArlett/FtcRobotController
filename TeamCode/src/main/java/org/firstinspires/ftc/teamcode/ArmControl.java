package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Drive;

public class ArmControl {
    private OpMode opMode;
    private double pos;
    // under construction
    public ArmControl(OpMode o) {
        opMode = o;
    }
    public double getPos() {
        if (opMode.gamepad2.x) {
            pos = 0.5;
        } else {
            pos = 0;
        } // if x pressed release else stay clamped
        return pos;
    }
}
