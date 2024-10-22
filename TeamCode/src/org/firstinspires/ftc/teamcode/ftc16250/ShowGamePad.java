package org.firstinspires.ftc.teamcode.ftc16250;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(group = "@Coaches Institute", name = "1.ShowName")
public class ShowGamePad extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Programmer", "Mike");
    }

    @Override
    public void loop() {
        double leftStickY = gamepad1.left_stick_y;
        telemetry.addData("Left Y", leftStickY);
    }
}
