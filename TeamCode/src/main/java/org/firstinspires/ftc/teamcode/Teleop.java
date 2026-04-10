package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    Drive bot;
    Control controler;
    //Shoot shoot;
    @Override
    public void init(){
        bot = new Drive(hardwareMap);
        //shoot = new Shoot(bot, telemetry);
        controler = new Control(this, bot, shoot);
    }
    @Override
    public void loop(){
        motorPowers power = controler.getPower();
        bot.setPower(power);
        //shoot.spin();
    }
}
