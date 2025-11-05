package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class URSS extends LinearOpMode {
    DcMotor arm = null;
    DcMotor intake = null;

    @Override
    public void runOpMode() {

        intake = hardwareMap.get(DcMotor.class, "shooter");
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        waitForStart();

        if (gamepad1.a) {
            if (intake.getPower() == 0.8) {
                intake.setPower(0);
            } else {
                intake.setPower(.8);
            }

        } else if (gamepad1.b) {
            if (intake.getPower() == -0.8) {
                intake.setPower(0);
            } else {
                intake.setPower(-0.8);
            }

        }

    }
}
