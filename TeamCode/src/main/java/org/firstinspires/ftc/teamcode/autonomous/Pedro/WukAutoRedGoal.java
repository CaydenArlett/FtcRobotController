package org.firstinspires.ftc.teamcode.autonomous.Pedro;

import com.acmerobotics.roadrunner.Pose2d;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ComplexRobots.ServoGoodBot;
import org.firstinspires.ftc.teamcode.DualLogger;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "WukAutoRedGoal")
public class WukAutoRedGoal extends OpMode {

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
        PARK,
        DONE
    }

    private AutoState state;

    Pose startPose = new Pose(124, 122, Math.toRadians(35));
    Pose shootPose = new Pose(94.2826, 93.2522, Math.toRadians(50));

    Pose pickPPGstart = new Pose(102, 83.463, Math.toRadians(0));
    Pose pickPPGend = new Pose(128, 83.463, Math.toRadians(0));

    Pose pickPGPstart = new Pose(102, 60, Math.toRadians(0));
    Pose pickPGPend = new Pose(130, 60, Math.toRadians(0));

    Pose gateClear = new Pose(131.5, 62, Math.toRadians(-90));
    Pose parkPose = new Pose(105, 65, Math.toRadians(0));

    PathChain startToShoot;
    PathChain shootToPickupPPG;
    PathChain pickupPPGToPPGend;
    PathChain pickupPPGendToShoot;
    PathChain shootToPickupPGP;
    PathChain pickupPGPToPGPend;
    PathChain pgpendToGate;
    PathChain gateToShoot;
    PathChain shootToPark;

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
                PedroFunctions.shoot(robot);
                break;
            case SHOOT_TO_PICKUP_PPG:
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
                PedroFunctions.shoot(robot);
                break;
            case SHOOT_TO_PICKUP_PGP:
                follower.followPath(shootToPickupPGP, true);
                break;
            case PICKUP_PGP_TO_PGPEND:
                PedroFunctions.intake(robot);
                follower.followPath(pickupPGPToPGPend, true);
                break;
            case PGPEND_TO_GATE:
                follower.followPath(pgpendToGate, true);
                break;
            case GATE_CLEAR:
                break;
            case GATE_TO_SHOOT:
                follower.followPath(gateToShoot, true);
                break;
            case SHOOT_3:
                PedroFunctions.shoot(robot);
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
                    setState(AutoState.SHOOT_1);
                break;
            case SHOOT_1:
                if (stateTimer.getElapsedTimeSeconds() > 3.0)
                    setState(AutoState.SHOOT_TO_PICKUP_PPG);
                break;
            case SHOOT_TO_PICKUP_PPG:
                if (!follower.isBusy())
                    setState(AutoState.PICKUP_PPG_TO_PPGEND);
                break;
            case PICKUP_PPG_TO_PPGEND:
                if (!follower.isBusy())
                    setState(AutoState.PICKUP_PPGEND_TO_SHOOT);
                break;
            case PICKUP_PPGEND_TO_SHOOT:
                if (!follower.isBusy())
                    setState(AutoState.SHOOT_2);
                break;
            case SHOOT_2:
                if (stateTimer.getElapsedTimeSeconds() > 3.0)
                    setState(AutoState.SHOOT_TO_PICKUP_PGP);
                break;
            case SHOOT_TO_PICKUP_PGP:
                if (!follower.isBusy())
                    setState(AutoState.PICKUP_PGP_TO_PGPEND);
                break;
            case PICKUP_PGP_TO_PGPEND:
                if (!follower.isBusy())
                    setState(AutoState.PGPEND_TO_GATE);
                break;
            case PGPEND_TO_GATE:
                if (!follower.isBusy())
                    setState(AutoState.GATE_CLEAR);
                break;
            case GATE_CLEAR:
                if (stateTimer.getElapsedTimeSeconds() > 3.0)
                    setState(AutoState.GATE_TO_SHOOT);
                break;
            case GATE_TO_SHOOT:
                if (!follower.isBusy())
                    setState(AutoState.SHOOT_3);
                break;
            case SHOOT_3:
                if (stateTimer.getElapsedTimeSeconds() > 3.0)
                    setState(AutoState.PARK);
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
        follower.setStartingPose(startPose);
        setState(AutoState.START_TO_SHOOT);
        robot = new ServoGoodBot(hardwareMap, new Pose2d(0, 0, 0), this, new DualLogger(telemetry));
    }

    @Override
    public void loop() {
        follower.update();
        updateStateMachine();
    }
}
