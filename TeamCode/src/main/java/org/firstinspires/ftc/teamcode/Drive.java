package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    DcMotor Left_Front;
    DcMotor Right_Front;
    DcMotor Left_Back;
    DcMotor Right_Back;
    public Drive(HardwareMap hardwareMap){
        Left_Front = hardwareMap.get(DcMotor.class, "leftFront");
        Right_Front = hardwareMap.get(DcMotor.class, "rightFront");
        Left_Back = hardwareMap.get(DcMotor.class, "leftBack");
        Right_Back = hardwareMap.get(DcMotor.class, "rightBack");
    }
}
