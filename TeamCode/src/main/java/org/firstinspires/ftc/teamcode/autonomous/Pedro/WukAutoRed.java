package org.firstinspires.ftc.teamcode.autonomous.Pedro;

import static org.firstinspires.ftc.teamcode.Autonomous.Pedro.PedroFunctions.turn;

import com.acmerobotics.roadrunner.Pose2d;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.ButtonMaps.Arm.FlywheelPDIFF;
import org.firstinspires.ftc.teamcode.ComplexRobots.ServoGoodBot;
import org.firstinspires.ftc.teamcode.DualLogger;
import org.firstinspires.ftc.teamcode.limelightData;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "WukAutoRed")
public class WukAutoRed extends OpMode {
    public DcMotorEx ShootMotor;
    private final DualLogger dualLogger = new DualLogger(telemetry);
    public DcMotorEx intakeMotor1;
    public DcMotorEx intakeMotor2;

    private Follower follower;
    private Timer stateTimer;

    public ServoGoodBot robot;


    enum AutoState {
        START_TO_SHOOT,
        SHOOT_1,
        SHOOT_TO_PICKUP_PPG,
        PICKUP_PPG_TO_PPGEND,
        PICKUP_PPGEND_TO_SHOOT,
        SHOOT_2,
        SHOOT_TO_PICKUP_PGP,
        PICKUP_PGP_TO_PGPEND,
        PGPEND_TO_GATE,
        GATE_CLEAR,
        GATE_TO_SHOOT,
        SHOOT_3,
        SHOOT_TO_LOAD,
        LOAD_TO_SHOOT,
        SHOOT_4,
        PARK,
        AIM_1,
        AIM_2,
        AIM_3,
        AIM_4,
        DONE
    }

    private AutoState state;
    Pose startPose   = new Pose(87.8, 8, Math.toRadians(90));
    Pose shootPose   = new Pose(83, 12, Math.toRadians(69.3));

    Pose pickPPGstart = new Pose(94, 37.3438, Math.toRadians(0));
    Pose pickPPGend   = new Pose(124, 37.3438, Math.toRadians(0));

    Pose pickPGPstart = new Pose(94, 63, Math.toRadians(0));
    Pose pickPGPend   = new Pose(121, 62, Math.toRadians(0));

    Pose gateClear   = new Pose(123, 64, Math.toRadians(-90));
    Pose loadingZone = new Pose(132, 11, Math.toRadians(0));
    Pose parkPose    = new Pose(92, 27, Math.toRadians(0));

    PathChain startToShoot;
    PathChain shootToPickupPPG;
    PathChain pickupPPGToPPGend;
    PathChain pickupPPGendToShoot;
    PathChain shootToPickupPGP;
    PathChain pickupPGPToPGPend;
    PathChain pgpendToGate;
    PathChain gateToShoot;
    PathChain shootToLoad;
    PathChain loadToShoot;
    PathChain shootToPark;
    PathChain aim;

    void buildPaths() {
        startToShoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        shootToPickupPPG = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, pickPPGstart))
                .setLinearHeadingInterpolation(shootPose.getHeading(), pickPPGstart.getHeading())
                .build();

        pickupPPGToPPGend = follower.pathBuilder()
                .addPath(new BezierLine(pickPPGstart, pickPPGend))
                .setLinearHeadingInterpolation(pickPPGstart.getHeading(), pickPPGend.getHeading())
                .build();

        pickupPPGendToShoot = follower.pathBuilder()
                .addPath(new BezierLine(pickPPGend, shootPose))
                .setLinearHeadingInterpolation(pickPPGend.getHeading(), shootPose.getHeading())
                .build();

        shootToPickupPGP = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, pickPGPstart))
                .setLinearHeadingInterpolation(shootPose.getHeading(), pickPGPstart.getHeading())
                .build();

        pickupPGPToPGPend = follower.pathBuilder()
                .addPath(new BezierLine(pickPGPstart, pickPGPend))
                .setLinearHeadingInterpolation(pickPGPstart.getHeading(), pickPGPend.getHeading())
                .build();

        pgpendToGate = follower.pathBuilder()
                .addPath(new BezierLine(pickPGPend, gateClear))
                .setLinearHeadingInterpolation(pickPGPend.getHeading(), gateClear.getHeading())
                .build();

        gateToShoot = follower.pathBuilder()
                .addPath(new BezierLine(gateClear, shootPose))
                .setLinearHeadingInterpolation(gateClear.getHeading(), shootPose.getHeading())
                .build();

        shootToLoad = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, loadingZone))
                .setLinearHeadingInterpolation(shootPose.getHeading(), loadingZone.getHeading())
                .build();

        loadToShoot = follower.pathBuilder()
                .addPath(new BezierLine(loadingZone, shootPose))
                .setLinearHeadingInterpolation(loadingZone.getHeading(), shootPose.getHeading())
                .build();

        shootToPark = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();
    }

    void setState(AutoState newState) {
        state = newState;
        stateTimer.resetTimer();

        switch (newState) {
            case START_TO_SHOOT:
                follower.followPath(startToShoot, true);
                break;
            case SHOOT_1:
                break;
            case SHOOT_TO_PICKUP_PPG:
                PedroFunctions.intake(robot);
                follower.followPath(shootToPickupPPG, true);
                break;
            case PICKUP_PPG_TO_PPGEND:
                PedroFunctions.intake(robot);
                follower.followPath(pickupPPGToPPGend, true);
                break;
            case PICKUP_PPGEND_TO_SHOOT:
                follower.followPath(pickupPPGendToShoot, true);
                break;
            case SHOOT_2:
                break;
            case SHOOT_TO_PICKUP_PGP:
                PedroFunctions.intake(robot);
                follower.followPath(shootToPickupPGP, true);
                break;
            case PICKUP_PGP_TO_PGPEND:
                PedroFunctions.intake(robot);
                follower.followPath(pickupPGPToPGPend, true);
                break;
            case PGPEND_TO_GATE:
                PedroFunctions.intake(robot);
                follower.followPath(pgpendToGate, true);
                break;
            case GATE_CLEAR:
                PedroFunctions.reset(robot);
                break;
            case GATE_TO_SHOOT:
                follower.followPath(gateToShoot, true);
                break;
            case SHOOT_3:
                break;
            case SHOOT_TO_LOAD:
                PedroFunctions.intake(robot);
                follower.followPath(shootToLoad, true);
                break;
            case LOAD_TO_SHOOT:
                PedroFunctions.intake(robot);
                follower.followPath(loadToShoot, true);
                break;
            case SHOOT_4:
                break;
            case AIM_1:
                aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, 80, follower.getHeading());
                follower.followPath(aim, true);
                break;
            case AIM_2:
                aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, 80, follower.getHeading());
                follower.followPath(aim, true);
                break;
            case AIM_3:
                aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, 80, follower.getHeading());
                follower.followPath(aim, true);
                break;
            case AIM_4:
                aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, 80, follower.getHeading());
                follower.followPath(aim, true);
                break;
            case PARK:
                follower.followPath(shootToPark, true);
                break;
            case DONE:
                follower.breakFollowing();
                break;
        }
    }

    void updateStateMachine() {
        switch (state) {
            case START_TO_SHOOT:
                if (!follower.isBusy())
                    setState(AutoState.AIM_1);
                break;
            case AIM_1:
                if (!follower.isBusy()) setState(AutoState.SHOOT_1);
                break;
            case SHOOT_1:
                PedroFunctions.shoot(robot);
                if (!follower.isBusy()) {
                    if (stateTimer.getElapsedTimeSeconds() > 3.7) {
                        setState(AutoState.SHOOT_TO_PICKUP_PPG);
                        PedroFunctions.reset(robot);
                    }
                }
                break;
            case SHOOT_TO_PICKUP_PPG:
                if (!follower.isBusy())
                    setState(AutoState.PICKUP_PPG_TO_PPGEND);
                break;
            case PICKUP_PPG_TO_PPGEND:
                PedroFunctions.intake(robot);
                if (!follower.isBusy()) {
                    setState(AutoState.PICKUP_PPGEND_TO_SHOOT);
                    PedroFunctions.reset(robot);
                }
                break;
            case PICKUP_PPGEND_TO_SHOOT:
                if (!follower.isBusy())
                    setState(AutoState.AIM_2);
                break;
            case AIM_2:
                if (!follower.isBusy()) setState(AutoState.SHOOT_2);
                break;
            case SHOOT_2:
                PedroFunctions.shoot(robot);
//            PedroFunctions.aim(robot);
                if (!follower.isBusy()) {
                    if (stateTimer.getElapsedTimeSeconds() >= 3.7) {
                        setState(AutoState.SHOOT_TO_PICKUP_PGP);
                        PedroFunctions.reset(robot);
                    }
                }
                break;
            case SHOOT_TO_PICKUP_PGP:
                if (!follower.isBusy())
                    setState(AutoState.PICKUP_PGP_TO_PGPEND);
                break;
            case PICKUP_PGP_TO_PGPEND:
                PedroFunctions.intake(robot);
                if (!follower.isBusy() || stateTimer.getElapsedTimeSeconds() >= .7) {
                    setState(AutoState.PGPEND_TO_GATE);
                    PedroFunctions.reset(robot);
                }
                break;
            case PGPEND_TO_GATE:
                if (!follower.isBusy() || stateTimer.getElapsedTimeSeconds() >= .7)
                    setState(AutoState.GATE_CLEAR);
                break;
            case GATE_CLEAR:
                if (stateTimer.getElapsedTimeSeconds() >= .7) {
                    setState(AutoState.GATE_TO_SHOOT);
                }
                break;
            case GATE_TO_SHOOT:
                if (!follower.isBusy())
                    setState(AutoState.AIM_3);
                break;
            case AIM_3:
                if (!follower.isBusy()) setState(AutoState.SHOOT_3);
                break;
            case SHOOT_3:
                PedroFunctions.shoot(robot);
//            PedroFunctions.aim(robot);
                if (!follower.isBusy()) {
                    if (stateTimer.getElapsedTimeSeconds() >= 4) {
                        setState(AutoState.SHOOT_TO_LOAD);
                        PedroFunctions.reset(robot);
                    }
                }
                break;
            case SHOOT_TO_LOAD:
                if (!follower.isBusy())
                    setState(AutoState.LOAD_TO_SHOOT);
                break;
            case LOAD_TO_SHOOT:
                if (!follower.isBusy())
                    setState(AutoState.AIM_4);
                break;
            case AIM_4:
                if (!follower.isBusy()) setState(AutoState.SHOOT_4);
                break;
            case SHOOT_4:
                if (!follower.isBusy()) {
                    PedroFunctions.shoot(robot);
//                PedroFunctions.aim(robot);
                    if (stateTimer.getElapsedTimeSeconds() >= 4) {
                        setState(AutoState.PARK);
                        PedroFunctions.reset(robot);
                    }
                }
                break;
            case PARK:
                if (!follower.isBusy())
                    setState(AutoState.DONE);
                break;
            case DONE:
                break;
        }
    }


    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        stateTimer = new Timer();
        buildPaths();
        follower.setPose(startPose);
        setState(AutoState.START_TO_SHOOT);
        robot = new ServoGoodBot(hardwareMap, new Pose2d(0, 0, 0), this, new DualLogger(telemetry));
        robot.shootMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(FlywheelPDIFF.P, 0, 0, FlywheelPDIFF.F));

    }

    @Override
    public void loop() {
        follower.update();
        robot.runLimelight(24);
        if (limelightData.accurate) {
            turn(Math.toRadians(limelightData.aprilXDegrees), follower, follower.getPose().getX(), follower.getHeading());
        }
        updateStateMachine();
        dualLogger.addData("X Degrees", limelightData.aprilXDegrees);
        telemetry.update();
    }
}
