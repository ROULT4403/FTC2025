package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="RAS", group="Linear Opmode")

public class RAS extends LinearOpMode {


    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;
    DcMotor torreta = null;
    DcMotor intake = null;
    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    private double driveVelocityCap = 1;

    Servo palito = null;

    //recently addeed
    DcMotor shoot1 = null;

    DcMotor shoot2 = null;

    //PID
    double current_error = 0;
    double current_time = 0;

    double k_p = 0.9;

    double p = 0;

    double i = 0;

    double d = 0;

    double k_i = 0;

    double max_i = 0;

    double k_d = 0;

    double output = 0;
    //The variable startime is used as currentime

    double previous_error = 0;
    double previous_time = 0;

    int targetRPM = 0;

    private Limelight3A limelight;

    //RPM
    double startTime = (System.nanoTime() * 1e-9);
    double endTime = (System.nanoTime() * 1e-9);
    int endTicks = 0;
    int startTicks = 0;

    private int tpos = 0;

    @Override
    public void runOpMode() {

        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.
         */
        limelight.start();

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
        palito = hardwareMap.get(Servo.class, "palito");

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

        odo.setOffsets(-155, 75, DistanceUnit.MM);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        odo.resetPosAndIMU();


        // Loop principal de teleop
        while (opModeIsActive()) {

            //power(output);

            odo.update();
            if (gamepad2.options) {
                odo.recalibrateIMU();
            }
            // slow down method
            if (gamepad2.left_bumper) {
                driveVelocityCap = .4;

            } else {
                driveVelocityCap = 1;
            }

            if (gamepad1.right_trigger > 0.5) {
                palito.setPosition(1);

            } else {
                palito.setPosition(0);
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
            double frontLeftPower = ((roty + rotx + turn) / denominator) * driveVelocityCap;
            double backLeftPower = ((roty - rotx + turn) / denominator) * driveVelocityCap;
            double frontRightPower = ((roty - rotx - turn) / denominator) * driveVelocityCap;
            double backRightPower = ((roty + rotx - turn) / denominator) * driveVelocityCap;

            // Normalizamos si algún valor > 1


            frontRightMotor.setPower(backLeftPower);
            frontLeftMotor.setPower(backRightPower);
            backRightMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(frontRightPower);

            LLResult result = limelight.getLatestResult();
            //RPM
            endTime = (System.nanoTime() * 1e-9);
            endTicks = shoot1.getCurrentPosition();
            double cycletime = (endTime - startTime);
            double ticksPerSec = (endTicks - startTicks) / cycletime;
            double rpm = (ticksPerSec / 28) * 60;
            startTicks = shoot1.getCurrentPosition();
            startTime = (System.nanoTime() * 1e-9);


            torreta.setPower(0.8);
            //Bumpers
            if (gamepad1.right_bumper) {
                tpos += 18;
            } else if (gamepad1.left_bumper) {
                tpos -= 18;
            }
            torreta.setTargetPosition(tpos);

            if (tpos<=-1400){
                tpos=-1399;
            }
            else if (tpos>=1500){
                tpos=1499;
            }

            if (result.getTx()>0.1){
                tpos+=(int)((result.getTx()*3));
            }
            else if (result.getTx()<-0.1){
                tpos+=(int)((result.getTx()*3));
            }

            //triggers
            if (gamepad2.right_trigger > 0.5) {
                intake.setPower(-0.9);

            } else if (gamepad2.left_trigger > 0.5) {
                intake.setPower(0.5);

            } else {
                intake.setPower(0);
            }


            //Buttons
            if (gamepad1.b) {
                targetRPM = (int) (-85.38684*(result.getTy())+3476.78737);

            }
            else if (gamepad1.a) {
                targetRPM = 4100;

            }
            else if (gamepad1.y) {
                targetRPM = 2500;

            } else {
                targetRPM = 0;
            }


            if (tpos >= 3600) {
                tpos = 3600;
            }

            if (targetRPM > 0) {
                current_time = startTime;
                current_error = targetRPM - rpm;

                p = k_p * current_error;

                i += k_i * (current_error * (current_time - startTicks));


                if (i > max_i) {
                    i = max_i;
                } else if (i < -max_i) {
                    i = -max_i;
                }

                d = k_d * (current_error - previous_error) / (current_time - previous_time);

                output = p + i + d;
                shoot1.setPower(output);
                shoot2.setPower(output);
            } else if (targetRPM == 0) {
                shoot1.setPower(0);
                shoot2.setPower(0);
            }


            previous_error = current_error;
            previous_time = current_time;


            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("Botpose", botpose.toString());


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
                    telemetry.addData("RPM shooter", rpm);
                    telemetry.addData("Target RPM shooter", targetRPM);
                    telemetry.addData("Error:", current_error);
                    telemetry.addData("ticks per second", ticksPerSec);
                    telemetry.addData("process time", cycletime);
                    telemetry.addData("start ticks", startTicks);
                    telemetry.addData("end ticks", endTicks);

                    telemetry.update();

                }
            }
        }
    }
}