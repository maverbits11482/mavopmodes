package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by henry & tom on 2/19/17.
 */

public class MavBot {

    // Construction.
    // requires a hardwaremap as an argument.
    public MavBot(HardwareMap origMap) {
        // get reference to the hardware map for this robot.
        // this reference should be passed as an argument in the initialize method.
        hardwareMap = origMap;

        // get references to the motors
        motorLeft = hardwareMap.get(DcMotor.class, MOTOR_LEFT_NAME);
        motorRight = hardwareMap.get(DcMotor.class, MOTOR_RIGHT_NAME);

        // reverse direction of right motor
        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // reset encoders.
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set to our default motor mode.
        motorRight.setMode(DEFAULT_MOTOR_MODE);
        motorLeft.setMode(DEFAULT_MOTOR_MODE);

        // set default drive mode.
        currDriveMode = MavBotDriveMode.DRIVE_MODE_TANK;
    }

    // Constants.
    public static final String MOTOR_LEFT_NAME="motorLeft";
    public static final String MOTOR_RIGHT_NAME="motorRight";
    public static final DcMotor.RunMode DEFAULT_MOTOR_MODE = DcMotor.RunMode.RUN_WITHOUT_ENCODER;


    // member variables
    public DcMotor motorLeft;
    public DcMotor motorRight;

    // current drive mode.
    public MavBotDriveMode currDriveMode;

    // MavBot drive modes.
    public enum MavBotDriveMode {
        DRIVE_MODE_TANK,
        DRIVE_MODE_POV
    }

    private HardwareMap hardwareMap;

    // this method processes game input and moves robot
    public void gamepadDrive(Gamepad gamepad) {
        if(currDriveMode == MavBotDriveMode.DRIVE_MODE_TANK) {
            motorLeft.setPower(-gamepad.left_stick_y);
            motorRight.setPower(-gamepad.right_stick_y);
        }

    }

}

