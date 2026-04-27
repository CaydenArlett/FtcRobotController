package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    Drive bot;
    Control controller;
    ArmControl arm;
    //Shoot arm;
    @Override
    public void init(){
        bot = new Drive(hardwareMap);
        arm = new ArmControl(this, bot);
        controller = new Control(this, bot);
    }
    @Override
    public void loop(){
        motorPowers power = controller.getPower();
        bot.setPower(power);
        bot.updateArm();
        bot.spinLatch(arm.getLatchPower());
//        bot.setClawLeft(arm.getLeftClawPos());
//        bot.setClawRight(arm.getRightClawPos());
//        //arm.spin();
    }
}
