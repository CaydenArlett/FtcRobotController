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
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.ButtonMaps.Arm.FlywheelPDIFF;
import org.firstinspires.ftc.teamcode.ComplexRobots.ServoGoodBot;
import org.firstinspires.ftc.teamcode.DualLogger;
import org.firstinspires.ftc.teamcode.limelightData;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "InfCycleBlue")
public class InfCycleBlue extends OpMode {

    private Follower follower;
    private Timer stateTimer;
    private Timer autoTimer;
    private boolean spikeVisited = false;
    private static final double SHOOT_TIME = 2.8;
    private static final double INTAKE_TIME = 1;
    private static final double INTAKE_BURST_TIME = 0.5;
    private static final double AUTO_END_TIME = 27.0;
    ServoGoodBot robot;

    enum AutoState {
        START_TO_SHOOT,
        SHOOT,
        SHOOT_TO_SPIKE,
        SPIKE_TO_END,
        END_TO_SHOOT,
        SHOOT_TO_PICKLOAD,

        PICKLOAD_INTAKE1,
        PICKLOAD_RECOIL,
        PICKLOAD_RETURN,
        PICKLOAD_END_TO_SHOOT,
        AIM,
        PARK,
        DONE
    }

    private AutoState state;

    Pose startPose = new Pose(56.2, 8, Math.toRadians(90));
    Pose shootPose = new Pose(64, 14, Math.toRadians(120.28));
    Pose pickLoadPoseEnd = new Pose(7, 10, Math.toRadians(193));
    Pose pickLoadPoseRec = new Pose(28, 10, Math.toRadians(202));
    Pose parkPose = new Pose(48.0839, 22.407, Math.toRadians(180));
    Pose SpikeStart = new Pose(55, 32.8438, Math.toRadians(180));
    Pose SpikeEnd = new Pose(20, 32.8438, Math.toRadians(180));

    PathChain startToShoot;
    PathChain shootToPickLoad;
    PathChain shootToPark;
    PathChain shootToSpike;
    PathChain spikeToEnd;
    PathChain endToShoot;
    PathChain pickLoadEndToRec;
    PathChain pickLoadRecToPickLoadEnd;
    PathChain pickLoadEndToShoot;
    PathChain aim;

    void buildPaths() {
        startToShoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        shootToPickLoad = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, pickLoadPoseEnd))
                .setLinearHeadingInterpolation(shootPose.getHeading(), pickLoadPoseEnd.getHeading())
                .build();

        shootToPark = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();

        pickLoadEndToRec = follower.pathBuilder()
                .addPath(new BezierLine(pickLoadPoseEnd, pickLoadPoseRec))
                .setLinearHeadingInterpolation(pickLoadPoseEnd.getHeading(), pickLoadPoseRec.getHeading())
                .build();

        pickLoadRecToPickLoadEnd = follower.pathBuilder()
                .addPath(new BezierLine(pickLoadPoseRec, pickLoadPoseEnd))
                .setLinearHeadingInterpolation(pickLoadPoseRec.getHeading(), pickLoadPoseEnd.getHeading())
                .build();

        pickLoadEndToShoot = follower.pathBuilder()
                .addPath(new BezierLine(pickLoadPoseEnd, shootPose))
                .setLinearHeadingInterpolation(pickLoadPoseEnd.getHeading(), shootPose.getHeading())
                .build();
    }

    void setState(AutoState newState) {
        state = newState;
        stateTimer.resetTimer();

        switch (newState) {
            case START_TO_SHOOT:
                stateTimer.resetTimer();
                autoTimer.resetTimer();
                follower.followPath(startToShoot, true);
                break;
            case SHOOT:
                break;
            case SHOOT_TO_PICKLOAD:
                PedroFunctions.intake(robot);
                follower.followPath(shootToPickLoad, true);
                break;
            case PICKLOAD_INTAKE1:
                PedroFunctions.intake(robot);
                break;
            case PICKLOAD_RECOIL:
                PedroFunctions.intake(robot);
                follower.followPath(pickLoadEndToRec, true);
                break;
            case PICKLOAD_RETURN:
                PedroFunctions.intake(robot);
                follower.followPath(pickLoadRecToPickLoadEnd, true);
                break;
            case PICKLOAD_END_TO_SHOOT:
                PedroFunctions.reset(robot);
                follower.followPath(pickLoadEndToShoot, true);
                break;
            case AIM:
                aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, follower.getPose().getX(), follower.getHeading());
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
        if (autoTimer.getElapsedTimeSeconds() >= AUTO_END_TIME) {
            if (state != AutoState.PARK && state != AutoState.DONE) {
                setState(AutoState.PARK);
                return;
            }
        }

        switch (state) {
            case START_TO_SHOOT:
                PedroFunctions.shoot(robot);
                if (!follower.isBusy()) {
                    setState(AutoState.SHOOT);
                }
                break;

            case SHOOT:
                PedroFunctions.shoot(robot);
                if (stateTimer.getElapsedTimeSeconds() >= 3.6) {
                    PedroFunctions.reset(robot);
                        setState(AutoState.SHOOT_TO_PICKLOAD);
                }
                break;
            case SHOOT_TO_PICKLOAD:
                PedroFunctions.intake(robot);
                if (!follower.isBusy() || stateTimer.getElapsedTimeSeconds() >= INTAKE_TIME) {
                    setState(AutoState.PICKLOAD_INTAKE1);
                }
                break;

            case PICKLOAD_INTAKE1:
                PedroFunctions.intake(robot);
                if (stateTimer.getElapsedTimeSeconds() >= INTAKE_BURST_TIME) {
                    setState(AutoState.PICKLOAD_RECOIL);
                }
                break;

            case PICKLOAD_RECOIL:
                PedroFunctions.intake(robot);
                if (!follower.isBusy()) setState(AutoState.PICKLOAD_RETURN);
                break;

            case PICKLOAD_RETURN:
                PedroFunctions.intake(robot);
                if (!follower.isBusy() || stateTimer.getElapsedTimeSeconds() >= INTAKE_TIME) {
                    setState(AutoState.PICKLOAD_END_TO_SHOOT);
                }
                break;

            case PICKLOAD_END_TO_SHOOT:
                if (!follower.isBusy()) {
                    PedroFunctions.shoot(robot);
                    aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, follower.getPose().getX(), follower.getHeading());
                    setState(AutoState.AIM);
                }
                break;
            case AIM:
                PedroFunctions.shoot(robot);
                if (!follower.isBusy()) {
                    if (Math.abs(limelightData.aprilXDegrees) <= 1.5 || !limelightData.accurate ) {
                        setState(AutoState.SHOOT);
                    }
                    else
                        setState(AutoState.AIM);
                }
                break;

            case PARK:
                PedroFunctions.reset(robot);
                if (!follower.isBusy()) {
                    setState(AutoState.DONE);
                }
                break;

            case DONE:
                break;
        }
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        stateTimer = new Timer();
        autoTimer = new Timer();
        follower.setPose(startPose);
        buildPaths();
        robot = new ServoGoodBot(
                hardwareMap,
                new Pose2d(startPose.getX(), startPose.getY(), startPose.getHeading()),
                this,
                new DualLogger(telemetry)
        );

        robot.shootMotor.setPIDFCoefficients(
                DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(FlywheelPDIFF.P + 1.2, 0, 0, FlywheelPDIFF.F)
        );
    }

    @Override
    public void start() {
        stateTimer.resetTimer();
        autoTimer.resetTimer();
        setState(AutoState.START_TO_SHOOT);
    }

    @Override
    public void loop() {
        follower.update();
        robot.runLimelight(20);
        if (limelightData.accurate) {
            aim = turn(Math.toRadians(limelightData.aprilXDegrees), follower, follower.getPose().getX(), follower.getHeading());
        }

        updateStateMachine();
        telemetry.addData("Shoot Velocity", robot.shootMotor.getVelocity());
        telemetry.addData("LimelightDegrees", limelightData.aprilXDegrees);
        telemetry.addData("IMU Degrees", follower.getPose());
        telemetry.update();
    }
}
