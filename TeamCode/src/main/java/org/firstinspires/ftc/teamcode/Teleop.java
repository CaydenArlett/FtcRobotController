package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    Drive bot;
    Control controler;
    ArmControl shoot;
    //Shoot shoot;
    @Override
    public void init(){
        bot = new Drive(hardwareMap);
        shoot = new ArmControl(this);
        controler = new Control(this, bot);
    }
    @Override
    public void loop(){
        motorPowers power = controler.getPower();
        bot.setPower(power);
        bot.setPos(shoot.getPos());
        //shoot.spin();
    }
}
