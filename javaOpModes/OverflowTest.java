package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by maverbits11482 on 2/26/17.
 */
@TeleOp(name = "OverflowTest", group = "maverbits")
public class OverflowTest extends LinearOpMode {


    byte currPos;
    byte distance;
    byte targetPos;

    public void runOpMode(){

        this.waitForStart();

        while(opModeIsActive()) {
            currPos = -120;
            distance = -9;
            targetPos = (byte) (currPos + distance);
            telemetry.addData("currPos", currPos);
            telemetry.addData("distance", distance);
            telemetry.addData("targetPos", targetPos);
            telemetry.update();
        }
    }
}
