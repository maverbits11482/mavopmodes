package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.Locale;

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

    public static final double MM_TO_IN= 25.4;
    public static final double CM_TO_IN= 2.54;
    public static final double FT_TO_IN= 12;
    public static final double M_TO_IN= .0254;

    // Wheel diameter in inches.
    public static final double WHEEL_DIAMETER = 4.0;

    // Encoder ticks per revolution.
    public static final int ENCODER_TICKS__PER_REV = 1440;

    public static String TAG = "MavBot";

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

    public enum DistanceUnit {
        DISTANCE_MM,
        DISTANCE_CM,
        DISTANCE_M,
        DISTANCE_IN,
        DISTANCE_FT,
    }

    private HardwareMap hardwareMap;

    // this method processes game input and moves robot
    public void gamepadDrive(Gamepad gamepad) {
        if(currDriveMode == MavBotDriveMode.DRIVE_MODE_TANK) {
            motorLeft.setPower(-gamepad.left_stick_y);
            motorRight.setPower(-gamepad.right_stick_y);
        }

    }

    public void measuredDrive (double distance, DistanceUnit unit, double targetPower){
        // local variables.
        double convertedDistance = 0;
        int leftCurrPos;
        int rightCurrPos;
        double revs = 0;
        double encoderTicks = 0;

        // convert distance to inches.
        switch(unit) {
            case DISTANCE_MM:
                convertedDistance = distance / MM_TO_IN;
                break;
            default:
                // use distance.
                convertedDistance = distance;
                break;
            case DISTANCE_CM:
                convertedDistance = distance / CM_TO_IN;
                break;
            case DISTANCE_FT:
                convertedDistance = distance * FT_TO_IN;
                break;
            case DISTANCE_M:
                convertedDistance = distance / M_TO_IN;
                break;
        }

        // convert distance into wheel revolutions.
        revs = convertedDistance / (WHEEL_DIAMETER * Math.PI);

        // convert revolutions into encoder ticks.
        encoderTicks = revs * ENCODER_TICKS__PER_REV;
        RobotLog.vv(TAG, String.format(Locale.getDefault(), "encoderTicks = %d", (int)encoderTicks));

        // get current position of left and right motors.
        leftCurrPos = motorLeft.getCurrentPosition();
        rightCurrPos = motorRight.getCurrentPosition();
        RobotLog.vv(TAG, String.format(Locale.getDefault(), "leftCurrPos = %d", leftCurrPos));
        RobotLog.vv(TAG, String.format(Locale.getDefault(), "rightCurrPos = %d", rightCurrPos));

        // calculate target positions.
        int leftTarget = leftCurrPos + (int)encoderTicks;
        RobotLog.vv(TAG, String.format(Locale.getDefault(), "leftTarget = %d", leftTarget));

        int rightTarget = motorRight.getCurrentPosition() + (int)encoderTicks;
        RobotLog.vv(TAG, String.format(Locale.getDefault(), "rightTarget = %d", rightTarget));

        if( encoderTicks > 0 && leftTarget < leftCurrPos)
        {
            // overflow detected.
            RobotLog.ee(TAG, "measuredDrive: Encoder overflow detected!");

            // reset motor encoder.
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RobotLog.ee(TAG, "measuredDrive: motorLeft encoder was reset.");

            // recalculate target position.
            leftTarget = motorLeft.getCurrentPosition() + (int)encoderTicks;

        }
        else if( encoderTicks > 0 && rightTarget < rightCurrPos)
        {
            // overflow detected.
            RobotLog.ee(TAG, "measuredDrive: Encoder overflow detected!");

            // reset motor encoder.
            motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RobotLog.ee(TAG, "measuredDrive: motorLeft encoder was reset.");

            // recalculate target position.
            rightTarget = motorLeft.getCurrentPosition() + (int)encoderTicks;
        }
        else if(encoderTicks < 0 && rightTarget > rightCurrPos)
        {
            // underflow detected.
            RobotLog.ee(TAG, "measuredDrive: Encoder underflow detected!");

            // to do: reset encoders and recalculate new target values.
            motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            measuredDrive(distance, unit, targetPower);

            // recalculate target position.
            rightTarget = motorLeft.getCurrentPosition() + (int)encoderTicks;
        }
        else if (encoderTicks < 0 && leftTarget > leftCurrPos)
        {
            // underflow detected.
            RobotLog.ee(TAG, "measuredDrive: Encoder underflow detected!");

            // to do: reset encoders and recalculate new target values.
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            measuredDrive(distance, unit, targetPower);

            // recalculate target position.
            leftTarget = motorLeft.getCurrentPosition() + (int)encoderTicks;
        }

        motorLeft.setTargetPosition(leftTarget);
        motorRight.setTargetPosition(rightTarget);

        RobotLog.vv(TAG, String.format(Locale.getDefault(), "%d", rightTarget));
        RobotLog.vv(TAG, String.format(Locale.getDefault(), "%d", leftTarget));

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLeft.setPower(targetPower);
        motorRight.setPower(targetPower);
    }

}

