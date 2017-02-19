package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by cait on 2/19/17.
 */

@TeleOp(name="Tom Test Op Mode", group="maverbits")
public class TomTestOpMode extends LinearOpMode {

    // a reference to our robot hardware.
    public MavBot mavbot;


    public void runOpMode()  {

        // create a new object that represents our robot hardware.
        telemetry.addData("status", "creating new MavBot object...");
        telemetry.update();
        mavbot = new MavBot(hardwareMap);

        // wait until driver pushes start button
        telemetry.addData("status", "Press start button to begin...");
        telemetry.update();
        waitForStart();

        // loop while the op mode is active
        while(opModeIsActive()) {

             // run the drive method
            mavbot.gamepadDrive(gamepad1);

            // use telemetry to send info back to drive.
            telemetry.addData("LeftPower", mavbot.motorLeft.getPower());
            telemetry.addData("RightPower", mavbot.motorLeft.getPower());
            telemetry.addData("Current Drive Mode", mavbot.currDriveMode);
            telemetry.addData("Left Position", mavbot.motorLeft.getCurrentPosition());
            telemetry.addData("Right Position", mavbot.motorRight.getCurrentPosition());
            telemetry.update();

            // idle a moment, to give the robot controller a chance to do other parallel tasks.
            idle();

        }

    }

}
