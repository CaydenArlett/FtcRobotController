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
    DcMotor Intake_Motor1;
    DcMotor Intake_Motor2;
    DcMotorEx Shoot_Motor;
    public Drive(HardwareMap hardwareMap){
        Left_Front = hardwareMap.get(DcMotor.class, "leftFront");
        Right_Front = hardwareMap.get(DcMotor.class, "rightFront");
        Left_Back = hardwareMap.get(DcMotor.class, "leftBack");
        Right_Back = hardwareMap.get(DcMotor.class, "rightBack");
        Intake_Motor1 = hardwareMap.get(DcMotor.class, "intakeMotor1");
        Intake_Motor2 = hardwareMap.get(DcMotor.class, "intakeMotor2");
        Shoot_Motor = hardwareMap.get(DcMotorEx.class, "shootMotor");
        Right_Back.setDirection(DcMotorSimple.Direction.REVERSE);
        Right_Front.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake_Motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        Intake_Motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        Shoot_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void setMode(boolean x){
        if (x){
            Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else{
            Left_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Right_Front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Left_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Right_Back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }

    public void setPower(motorPowers power){
        this.Left_Front.setPower(power.leftFront);
        this.Right_Front.setPower(power.rightFront);
        this.Left_Back.setPower(power.leftBack);
        this.Right_Back.setPower(power.rightBack);
    }

    public void shoot(double power){
        this.Shoot_Motor.setPower(power);
    }

    public void intake1(double power){
        this.Intake_Motor1.setPower(power*0.7);
    }
    public void intake2(double power){
        this.Intake_Motor2.setPower(power*0.7);
    }
}
