package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by maverbits11482 on 2/27/17.
 */

@TeleOp(name = "GyroTest", group = "maverbits")
public class GyroTest extends LinearOpMode {

    public void runOpMode(){

        // create gyro from Gyrosensor class.
        GyroSensor gyro;

        // assign object to gyro.
        gyro = hardwareMap.get(GyroSensor.class, "gyro");

        // cast the gyro as a ModernRoboticsI2cGyro.
        ModernRoboticsI2cGyro modrobGyro = (ModernRoboticsI2cGyro)gyro;


        // calibrate gyro.
        modrobGyro.calibrate();

        while(gyro.isCalibrating() && opModeIsActive()){

            //display telemetry
            telemetry.addData("Status", "Gyro is calibrating, do not touch");
            telemetry.update();

        }

        telemetry.addData("Status", "calibration complete.");
        telemetry.addData("", "Press Start to Continue.");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){

            // return the Z axis of gyro.
            telemetry.addData("heading", gyro.getHeading());

            // return total Z value of gyro.
            telemetry.addData("Total value:", modrobGyro.getIntegratedZValue());
            telemetry.update();


        }

    }

}
