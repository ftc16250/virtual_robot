package org.firstinspires.ftc.teamcode.ftc16250;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class ServoButtons extends OpMode {
    Servo servo;

    @Override
    public void init() {

        telemetry.addData("Programmer", "Mike");
        servo = hardwareMap.get(Servo.class,"servo");
    }

    @Override
    public void loop() {
        if(gamepad1.y) {
            servo.setPosition(0.5);
        }
        else if(gamepad1.b){
            servo.setPosition(0.0);
        }
    }
}
