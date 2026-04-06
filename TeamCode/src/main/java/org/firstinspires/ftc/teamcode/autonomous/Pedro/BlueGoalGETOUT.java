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

@Autonomous(name = "BlueGoalGETOUT!!!!!")
public class BlueGoalGETOUT extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;
    public ServoGoodBot robot;

    public enum PathState {
        //Start pos-end
        //Drive > move state
        //Shoot > attempt to score


        DRIVE_STARTPOS_SHOOT_POS,
        SHOOT_PRELOAD,

        DRIVE_SHOOTPOS_ENDPOS
    }

    PathState pathState;

    private final Pose startPose = new Pose(27, 131.358, Math.toRadians(-37));

    private final Pose shootPose = new Pose(45, 117, Math.toRadians(40));

    private final Pose shootPose2 = new Pose(68, 134, Math.toRadians(25));

    private PathChain driveStartPosShootPos, driveShootPosEndPos;

    public void buildPaths() {
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
        driveShootPosEndPos = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, shootPose2))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose2.getHeading())
                .build();


    }

    public void statePathUpdate() {
        switch (pathState) {
            case DRIVE_STARTPOS_SHOOT_POS:
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.SHOOT_PRELOAD); //rest timer
                break;
            case SHOOT_PRELOAD:
                // check if follower is done
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 11) {
                    telemetry.addLine("done Path");
                    follower.followPath(driveShootPosEndPos, true);
                    setPathState(PathState.DRIVE_SHOOTPOS_ENDPOS);
                    PedroFunctions.shoot(robot);
                }
                break;
            default:
                telemetry.addLine("No State commanded");
                break;
            case DRIVE_SHOOTPOS_ENDPOS:
                // check if follower is done
                if (!follower.isBusy()) {
                    telemetry.addLine("done Path2");
                    PedroFunctions.shoot(robot);
                }
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.DRIVE_STARTPOS_SHOOT_POS;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        opModeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);


        buildPaths();
        follower.setPose(startPose);
        robot = new ServoGoodBot(hardwareMap, new Pose2d(0, 0, 0), this, new DualLogger(telemetry));
    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();
    }
}