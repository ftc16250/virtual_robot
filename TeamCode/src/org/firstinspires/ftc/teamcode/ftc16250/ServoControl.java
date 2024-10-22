package org.firstinspires.ftc.teamcode.ftc16250;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class ServoControl extends OpMode {
    Servo servo;

    @Override
    public void init() {

        telemetry.addData("Programmer", "Mike");
        servo = hardwareMap.get(Servo.class,"servo");
    }

    @Override
    public void loop() {
        double leftStickY = gamepad1.left_stick_y;
        telemetry.addData("Left Y", leftStickY);
        servo.setPosition(leftStickY);
    }
}
