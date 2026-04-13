package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    Drive bot;
    Control controler;
    @Override
    public void init(){
        bot = new Drive(hardwareMap);
        controler = new Control(this, bot);
    }
    @Override
    public void loop(){
        motorPowers power = controler.getPower();
        bot.setPower(power);
        bot.updateArm();
    }
}
