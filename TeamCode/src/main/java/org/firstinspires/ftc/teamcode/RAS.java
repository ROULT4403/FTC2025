package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Looper;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="RAS", group="Linear Opmode")

public class RAS extends LinearOpMode {
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;
    DcMotor torreta = null;
    DcMotor intake = null;


    //recently addeed
    DcMotor shoot1 = null;

    DcMotor shoot2 = null;




    private int tpos = 0;
    // might be take out:


    @Override
    public void runOpMode() {
        //Inicialización de motores
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FWDleftDrive");
        frontRightMotor = hardwareMap.get(DcMotor.class, "FWDrightDrive");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BCKleftDrive");
        backRightMotor = hardwareMap.get(DcMotor.class, "BCKrightDrive");





        torreta = hardwareMap.get(DcMotor.class, "torreta");
        shoot1 = hardwareMap.get(DcMotor.class, "shooter1");
        shoot2 = hardwareMap.get(DcMotor.class, "shooter2");
        intake = hardwareMap.get(DcMotor.class, "intake");

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        shoot1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoot1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shoot1.setDirection(DcMotorSimple.Direction.FORWARD);

        shoot2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoot2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoot2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shoot2.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);


        torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        torreta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        torreta.setTargetPosition(tpos);
        torreta.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // Invertimos un lado para que todos avancen hacia adelante
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        // Espera a que empiece el match
        waitForStart();

        // Loop principal de teleop
        while (opModeIsActive()) {

            // Lectura de los joysticks
            double y = gamepad2.left_stick_y;  // Adelante / atrás (invertido porque arriba es negativo)
            double x = -gamepad2.left_stick_x * 1.1; // Strafe (con pequeño ajuste)
            double rx = -gamepad2.right_stick_x; // Rotación

            // Fórmulas para mecanum drive
            double frontLeftPower = y + x + rx;
            double backLeftPower = y - x + rx;
            double frontRightPower = y - x - rx;
            double backRightPower = y + x - rx;

            // Normalizamos si algún valor > 1

            double max = (Math.abs(y) + Math.abs(x) + Math.abs(rx));
            frontLeftMotor.setPower(frontLeftPower / max);
            backLeftMotor.setPower(backLeftPower / max);
            frontRightMotor.setPower(frontRightPower / max);
            backRightMotor.setPower(backRightPower / max);



            torreta.setPower(0.8);
            //Bumpers
            if (gamepad2.right_bumper) {
                tpos -= 4;
            } else if (gamepad2.left_bumper) {
                tpos += 4;
            }
            torreta.setTargetPosition(tpos);
            //triggers
            if (gamepad2.right_trigger > 0.5) {
                intake.setPower(-0.9);

            }else if(gamepad2.left_trigger > 0.5) {
                intake.setPower(0.3);
                shoot1.setPower(-0.3);
                shoot2.setPower(-0.3);

            }else{
                intake.setPower(0);
            }


            //Buttons
            if (gamepad2.a) {
                shoot1.setPower(1);
                shoot2.setPower(1);


            } else if (gamepad2.b) {
                shoot1.setPower(0.95);
                shoot2.setPower(0.95);

            } else if (gamepad2.x) {
                shoot1.setPower(0.8);
                shoot2.setPower(0.8);

            }else if (gamepad2.y) {
                shoot1.setPower(0.7);
                shoot2.setPower(0.7);

            } else {
                shoot1.setPower(.5);
                shoot2.setPower(.5);
            }


            if (tpos >= 3600) {
                tpos = 3600;
            }





            //Telemetría para ver valores en Driver Station
            telemetry.addData("Front Left", frontLeftPower / max);
            telemetry.addData("Back Left", backLeftPower / max);
            telemetry.addData("Front Right", frontRightPower / max);
            telemetry.addData("Back Right", backRightPower / max);
            telemetry.addData("Intake", intake.getPower());
            //telemetry.addData("Torreta", torreta.getPower());
            telemetry.addData("Shooter1", shoot1.getPower());
            telemetry.addData("Shooter2", shoot2.getPower());
            telemetry.addData("Torreta", torreta.getTargetPosition());
            telemetry.update();

        }
    }
}
