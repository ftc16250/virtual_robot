package org.firstinspires.ftc.teamcode.ftc16250;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp()
public class MotorControl extends OpMode {
    DcMotor motor;

    @Override
    public void init() {

        telemetry.addData("Programmer", "Mike");
        motor = hardwareMap.get(DcMotor.class,"motor");
    }

    @Override
    public void loop() {
        double leftStickY = -gamepad1.left_stick_y;
        telemetry.addData("Left Y", leftStickY);
        motor.setPower(leftStickY);
    }
}
