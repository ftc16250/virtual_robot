package org.firstinspires.ftc.teamcode.ftc16250;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp()
public class ArcadeDrive extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;

    @Override
    public void init() {
        telemetry.addData("Programmer", "Mike");
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    double squareWithSign(double x) {
        return x * x * Math.signum(x);
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x / 2;

        double leftSpeed = forward + right;
        double rightSpeed = forward - right;

        forward = squareWithSign(forward);
        right = squareWithSign(right);

        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);
    }
}
