package org.firstinspires.ftc.teamcode;

public class motorPowers {
    double leftFront, rightFront, leftBack, rightBack;
    public motorPowers(double x){
        this.leftFront = x;
        this.rightFront = x;
        this.leftBack = x;
        this.rightBack = x;
    }
    //tried renaming to "setMotorPowers" but it's already defined elsewhere - Jake
    //I feel like the naming could still be more clear
    public motorPowers(double a, double b, double c, double d){
        this.leftFront = a;
        this.rightFront = b;
        this.leftBack = c;
        this.rightBack = d;
    }
}
