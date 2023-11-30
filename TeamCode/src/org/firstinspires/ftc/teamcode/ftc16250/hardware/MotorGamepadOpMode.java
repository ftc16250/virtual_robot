package org.firstinspires.ftc.teamcode.ftc16250.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MotorGamepadOpMode extends OpMode {
    ProgrammingBoard4 board = new ProgrammingBoard4();
    @Override
    public void init() {
        board.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            board.setMotorSpeed(0.5);
        }
        else{
            board.setMotorSpeed(0.0);
        }
        telemetry.addData("motor rotations", board.getMotorRotations());
    }
}
