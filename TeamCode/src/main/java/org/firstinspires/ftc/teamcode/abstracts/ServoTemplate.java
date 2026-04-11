package org.firstinspires.ftc.teamcode.abstracts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Drive;

public abstract class ServoTemplate {

    /**
     * Called once per TeleOp loop.
     * RESTRICTIONS: No loops, delay statements, and telemetry.update().
     * Only use ONE gamepad: gamepad1 for drivetrain, gamepad2 for arm/claw/etc.
     * @param robot The CenterStageRobot instance for that TeleOp
     * @param opMode Literally "this" - The TeleOp instance
     */
    public abstract void loop(Drive robot,
                              OpMode opMode);
}