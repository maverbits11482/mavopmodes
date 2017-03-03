package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robocol.PeerApp;

/**
 * Created by maverbits11482 on 2/24/17.
 */

@TeleOp(name = "TestDriveMeasured", group="maverbits")
public class TestDriveMeasured extends LinearOpMode {
    MavBot myRobot;
    public void runOpMode() {

        myRobot = new MavBot(hardwareMap, this);

        waitForStart();

        myRobot.measuredDrive(-12, MavBot.DistanceUnit.DISTANCE_IN, -.2);
        sleep(5000);
        myRobot.measuredDrive(12, MavBot.DistanceUnit.DISTANCE_IN, .2);

        while(opModeIsActive()) {
            telemetry.addData("status", "press stop to exit");
            telemetry.addData("Left Position", myRobot.motorLeft.getCurrentPosition());
            telemetry.addData("Right Position", myRobot.motorRight.getCurrentPosition());
            telemetry.update();
            idle();
        }
    }
}
