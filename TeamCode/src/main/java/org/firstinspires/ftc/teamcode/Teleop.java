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
        telemetry.addData("Status", "Initialized");

    }
    double x = 0;
    @Override
    public void loop(){
        motorPowers power = controller.getPower();
        bot.setPower(power);
        bot.updateArm();
        bot.spinLatch(arm.getLatchPower());
        telemetry.addData("Motors", "left front(%.2f), left back(%.2f)", power.leftFront, power.leftBack);
        telemetry.addData("Motors", "right front(%.2f), right back(%.2f)", power.rightFront, power.rightBack);
        telemetry.addData("latch power: ", arm.getLatchPower());
//        bot.clawRight.setPosition(x);
//        bot.setClawLeft(arm.getLeftClawPos());
//        bot.setClawRight(arm.getRightClawPos());
//        //arm.spin();

    }
}
