package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shoot {
    public double targetV;
    Drive drive;
    Telemetry telemetry;
    Shoot(Drive b, Telemetry t){
        drive = b;
        telemetry = t;
    }
    public void spin(){
        double power = 0;
        if (targetV > 0) {
            power = targetV + (targetV - drive.Shoot_Motor.getVelocity() / 2500);
            if (power > 1) {
                power = 1;
            }
        }
        telemetry.addData("Motor Velocity :", drive.Shoot_Motor.getVelocity());
        telemetry.addData("Target Velocity :", targetV  * 2500);
        drive.shoot(power);
    }
}
