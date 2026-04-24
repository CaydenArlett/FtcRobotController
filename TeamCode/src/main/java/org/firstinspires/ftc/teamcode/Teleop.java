package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    Drive bot;
    Control controler;
    ArmControl arm;
    //Shoot arm;
    @Override
    public void init(){
        bot = new Drive(hardwareMap);
        arm = new ArmControl(this, bot);
        controler = new Control(this, bot);
    }
    @Override
    public void loop(){
        motorPowers power = controler.getPower();
        bot.setPower(power);
        bot.updateArm();
//        bot.setLatch(arm.getLatchPos());
//        bot.setClawLeft(arm.getLeftClawPos());
//        bot.setClawRight(arm.getRightClawPos());
//        //arm.spin();
    }
}
