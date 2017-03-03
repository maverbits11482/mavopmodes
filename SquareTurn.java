package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by maverbits11482 on 3/1/17.
 */

@TeleOp(name = "SquareTurn", group = "maverbits")
public class SquareTurn extends LinearOpMode{

    MavBot mavbot;
    ModernRoboticsI2cGyro gyro;
    int initHeading = 0;
    int totalHeading = 0;

    public void refreshTelemetry() {

        boolean bUpdate = true;

        telemetry.addData("Start heading", initHeading);
        telemetry.addData("current heading", gyro.getHeading());
        telemetry.addData("Total Change", totalHeading);

        if (bUpdate) {
            telemetry.update();
        }
    }

    public void runOpMode(){
        mavbot= new MavBot(hardwareMap, this);

        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");

        gyro.calibrate();

        while(gyro.isCalibrating() && !isStopRequested()){
            telemetry.addData("Status", "Gyro is calibrating, do not touch");
            telemetry.update();
        }

        // wait till driver pushes start button.
        telemetry.addData("Status", "Gyro calibrated... press start to continue.");
        telemetry.update();
        this.waitForStart();

        // Drive three feet forward
        mavbot.measuredDrive(3, MavBot.DistanceUnit.DISTANCE_FT, .3);

        // Turn 90 degrees
        initHeading = gyro.getIntegratedZValue();
        totalHeading = 0;
        while(totalHeading < 90){
            mavbot.motorLeft.setPower(.4);
            mavbot.motorRight.setPower(0);
            totalHeading = gyro.getIntegratedZValue() - initHeading;
            refreshTelemetry();
        }


    }


}
