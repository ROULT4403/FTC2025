package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MecanumTeleOP", group="Linear Opmode")
public class mecanumTeleOP extends LinearOpMode {



    //
    // Declare OpMode members.
    private double driveVelocityCap = .8;
    private DcMotor FWDleftDrive = null;
    private DcMotor FWDrightDrive = null;
    private DcMotor BCKleftDrive = null;
    private DcMotor BCKrightDrive = null;


    @Override
    public void runOpMode() {
        //Forward face is towards control hub and expansion hub as of Aug 19 2025
        // Initialize the hardware variables.
        FWDleftDrive  = hardwareMap.get(DcMotor.class, "FWDleftDrive");
        FWDrightDrive = hardwareMap.get(DcMotor.class, "FWDrightDrive");
        BCKleftDrive  = hardwareMap.get(DcMotor.class, "BCKleftDrive");
        BCKrightDrive = hardwareMap.get(DcMotor.class, "BCKrightDrive");


        // Stop and reset encoders
        FWDleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FWDrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BCKleftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BCKrightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set motors to use encoders
        FWDleftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FWDrightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BCKleftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BCKrightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set motor direction
        FWDleftDrive.setDirection(DcMotor.Direction.REVERSE);
        FWDrightDrive.setDirection(DcMotor.Direction.FORWARD);
        BCKleftDrive.setDirection(DcMotor.Direction.REVERSE);
        BCKrightDrive.setDirection(DcMotor.Direction.FORWARD);

        //Set zero power behavior
        FWDleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FWDrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BCKleftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BCKrightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            // slow down method
            if (gamepad2.right_trigger > .1) {
                driveVelocityCap = .3;

            } else {
                driveVelocityCap = .8;
            }

            //DRIVE Split Arcade
            double fwd = -gamepad2.left_stick_y;
            double strafe = gamepad2.left_stick_x*1.1;
            //Quantity to turn by (turn)
            double turn = gamepad2.right_stick_x;


            /**
             * Control a mecanum drive base with three double inputs
             *
             * @param Strafe  is the first double X value which represents how the base should strafe
             * @param Forward is the only double Y value which represents how the base should drive forward
             * @param Turn    is the second double X value which represents how the base should turn
             */
            //Find the magnitude of the controller's input


            //returns point from +X axis to point (forward, strafe)

            double denominator = Math.max(Math.abs(fwd) + Math.abs(strafe) + Math.abs(turn), 1);
            double frontLeftPower = ((fwd + strafe + turn) / denominator)*driveVelocityCap;
            double backLeftPower = ((fwd - strafe + turn) / denominator)*driveVelocityCap;
            double frontRightPower = ((fwd - strafe - turn) / denominator)*driveVelocityCap;
            double backRightPower = ((fwd + strafe - turn )/ denominator)*driveVelocityCap;

            //double vX represents the velocities sent to each motor


            FWDrightDrive.setPower(frontRightPower);
            FWDleftDrive.setPower(frontLeftPower);
            BCKrightDrive.setPower(backRightPower);
            BCKleftDrive.setPower(backLeftPower);


            // Send telemetry data to the driver station
            telemetry.update();
        }
        }
    }


