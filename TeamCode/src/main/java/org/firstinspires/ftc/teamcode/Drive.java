package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    DcMotor Left_Front;
    DcMotor Right_Front;
    DcMotor Left_Back;
    DcMotor Right_Back;
    DcMotorEx slideMotor;

    private double resetPower = 0.5;

    private double slidePower = 0.5;
    public boolean armReset = false;

    public boolean switchPressed = false;

    public Drive(HardwareMap hardwareMap) {
        Left_Front = hardwareMap.get(DcMotor.class, "leftFront");
        Right_Front = hardwareMap.get(DcMotor.class, "rightFront");
        Left_Back = hardwareMap.get(DcMotor.class, "leftBack");
        Right_Back = hardwareMap.get(DcMotor.class, "rightBack");
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotorEx slideMotor = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMode(boolean x) {
        if (x) {
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

    public void moveArm(int targetPos) {
        slideMotor.setTargetPosition(targetPos); // Ticks
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(slidePower);
    }

    public void updateArm() {
        if (switchPressed) {
            this.slideMotor.setPower(0);
            slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if (armReset){
            this.slideMotor.setPower(resetPower);
        }
    }
}