package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Prueba Autonomo" , group = "pruebas")
public class autonomoo extends LinearOpMode {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;


    @Override
    public void runOpMode() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FWDleftDrive");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FWDrightDrive");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BCKleftDrive");
        backRightMotor = hardwareMap.get(DcMotor.class, "BCKrightDrive");



        waitForStart();
        if (opModeIsActive()) {
            foward(.8);

            sleep(5000);

            foward(0);

            sleep(2000);

            right(.8);

            sleep(3000);

            foward(0);
        }
    }
    public void foward(double power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);

    }
    public void right(double power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(power);
    }
}
