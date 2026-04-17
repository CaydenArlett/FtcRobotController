package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {
    DcMotor Left_Front;
    DcMotor Right_Front;
    DcMotor Left_Back;
    DcMotor Right_Back;

    Servo Launcher;


    DcMotorEx slideMotor;

    private double distance;

    private double checkTicks;

    private double checkCount;
    private double resetPower = 0.5;

    private double slidePower = 0.5;
    public boolean armReset = false;


    public boolean resetMode = false;


    public Drive(HardwareMap hardwareMap) {
        Left_Front = hardwareMap.get(DcMotor.class, "leftFront");
        Right_Front = hardwareMap.get(DcMotor.class, "rightFront");
        Left_Back = hardwareMap.get(DcMotor.class, "leftBack");
        Right_Back = hardwareMap.get(DcMotor.class, "rightBack");
        Launcher = hardwareMap.get(Servo.class, "launcher");
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");

        //initialise wheels
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setBrake(boolean x){
        if (x){
            Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
    public void setPower(motorPowers power) {
        this.Left_Front.setPower(power.leftFront);
        this.Right_Front.setPower(power.rightFront);
        this.Left_Back.setPower(power.leftBack);
        this.Right_Back.setPower(power.rightBack);
    }

    //moves arm to a desired pos
    public void moveArm(double targetPos) {
        slideMotor.setTargetPosition((int)targetPos); // Ticks
        slideMotor.setPower(slidePower);
    }


    //resets arm encoder
    public void updateArm() {
        if (resetMode) {
            this.slideMotor.setPower(-0.2);
            if (distance > 0.7) {
                checkTicks++;
                if (checkTicks > checkCount) {
                    slideMotor.setPower(0);
                    slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
            else if (distance > 0.5) {
                if (checkTicks > 0){
                    checkTicks--;
                }
                this.slideMotor.setPower(-0.01);
            }
            else if (distance > 0) {
                checkTicks = 0;
                this.slideMotor.setPower(-0.1);
            }
        }
    }
    public void setPos(double pos) {
        Launcher.setPosition(pos);
    }
}
