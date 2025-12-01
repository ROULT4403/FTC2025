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

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="RAS", group="Linear Opmode")

public class RAS extends LinearOpMode {
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;
    DcMotor torreta = null;
    DcMotor intake = null;
    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    private double driveVelocityCap = 0.95;


    //recently addeed
    DcMotor shoot1 = null;

    DcMotor shoot2 = null;

    //RPM
    double output;
    double startTime = (System.nanoTime() * 1e-9);
    double endTime = (System.nanoTime()*1e-9);
    int endTicks = 0;
    int startTicks = 0;

    // ...esperas un intervalo corto...



   // public void power(double output){
   //     shoot1.setPower(output);
   //     shoot2.setPower(output);
   //}
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
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

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

        odo.setOffsets(-155,75, DistanceUnit.MM);

        // Loop principal de teleop
        while (opModeIsActive()) {

            //power(output);

            odo.update();
            if (gamepad2.options) {
                odo.recalibrateIMU();
                odo.resetPosAndIMU();
            }
            // slow down method
            if (gamepad2.left_bumper) {
                driveVelocityCap = .3;

            } else {
                driveVelocityCap = .95;
            }



            odo.update();
            double botheading = odo.getHeading(AngleUnit.RADIANS);

            double fwd = gamepad2.left_stick_y;
            double strafe = -gamepad2.left_stick_x * 1.1;
            //Quantity to turn by (turn)
            double turn = gamepad2.right_stick_x;
            double roty = strafe * Math.sin(-botheading) + fwd * Math.cos(-botheading);
            double rotx = strafe * Math.cos(-botheading) - fwd * Math.sin(-botheading);

            // Fórmulas para mecanum drive
            double denominator = Math.max(Math.abs(fwd) + Math.abs(strafe) + Math.abs(turn), 1);
            double frontLeftPower = ((roty + rotx + turn) / denominator)*driveVelocityCap;
            double backLeftPower = ((roty - rotx + turn) / denominator)*driveVelocityCap;
            double frontRightPower = ((roty - rotx - turn) / denominator)*driveVelocityCap;
            double backRightPower = ((roty + rotx - turn )/ denominator)*driveVelocityCap;

            // Normalizamos si algún valor > 1


            frontRightMotor.setPower(backLeftPower);
            frontLeftMotor.setPower(backRightPower);
            backRightMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(frontRightPower);

            //RPM
            endTime = (System.nanoTime()*1e-9);
            endTicks = shoot1.getCurrentPosition();
            double cycletime = (endTime-startTime);
            double ticksPerSec = (endTicks - startTicks)/cycletime;
            double rpm = (ticksPerSec / 28)*60;
            startTicks = shoot1.getCurrentPosition();
            startTime = (System.nanoTime()*1e-9);


            torreta.setPower(0.8);
            //Bumpers
            if (gamepad1.right_bumper) {
                tpos += 8;
            } else if (gamepad1.left_bumper) {
                tpos -= 8;
            }
            torreta.setTargetPosition(tpos);
            //triggers
            if (gamepad2.right_trigger > 0.5) {
                intake.setPower(-0.9);

            }else if(gamepad2.left_trigger > 0.5) {
                intake.setPower(0.4);

            }else{
                intake.setPower(0);
            }


            //Buttons
            if (gamepad1.a) {
                shoot1.setPower(.85);
                shoot2.setPower(.85);

            } else if (gamepad1.b) {
                shoot1.setPower(0.70);
                shoot2.setPower(0.70);

            } else if (gamepad1.x) {
                shoot1.setPower(0.65);
                shoot2.setPower(0.65);

            }else if (gamepad1.y) {
                shoot1.setPower(0.5);
                shoot2.setPower(0.5);

            } else {
                shoot1.setPower(0);
                shoot2.setPower(0);
            }


            if (tpos >= 3600) {
                tpos = 3600;
            }




            //Telemetría para ver valores en Driver Station
            telemetry.addData("Front Left", frontLeftPower);
            telemetry.addData("Back Left", backLeftPower);
            telemetry.addData("Back Right", frontRightPower);
            telemetry.addData("Front Right", backRightPower);
            telemetry.addData("bot heading", botheading);
            telemetry.addData("FWD/BCK deadwheel", odo.getEncoderX());
            telemetry.addData("L/R deadwheel", odo.getEncoderY());
            telemetry.addData("Intake", intake.getPower());
            //telemetry.addData("Torreta", torreta.getPower());
            telemetry.addData("Shooter1", shoot1.getPower());
            telemetry.addData("Shooter2", shoot2.getPower());
            telemetry.addData("Torreta", torreta.getTargetPosition());
            telemetry.addData("RPM shooter" , rpm);
            telemetry.addData("ticks per second" , ticksPerSec);
            telemetry.addData("process time" , cycletime);
            telemetry.addData("start ticks" , startTicks);
            telemetry.addData("end ticks" , endTicks);

            telemetry.update();

        }
    }
}