package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MecanumFieldCenTeleOP", group="Linear Opmode")
public class mecanumFieldCenTeleOP extends LinearOpMode {



    //
    // Declare OpMode members.
    private double driveVelocityCap = .8;
    private DcMotor FWDleftDrive = null;
    private DcMotor FWDrightDrive = null;
    private DcMotor BCKleftDrive = null;
    private DcMotor BCKrightDrive = null;

    private GoBildaPinpointDriver Pinpoint=null;


    @Override
    public void runOpMode() {
        //Forward face is towards control hub and expansion hub as of Aug 19 2025
        // Initialize the hardware variables.
        FWDleftDrive  = hardwareMap.get(DcMotor.class, "FWDleftDrive");
        FWDrightDrive = hardwareMap.get(DcMotor.class, "FWDrightDrive");
        BCKleftDrive  = hardwareMap.get(DcMotor.class, "BCKleftDrive");
        BCKrightDrive = hardwareMap.get(DcMotor.class, "BCKrightDrive");
        Pinpoint= hardwareMap.get(GoBildaPinpointDriver.class, "Pinpoint");


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
        Pinpoint.setOffsets(153.77,-58.68, DistanceUnit.MM);
        Pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            Pinpoint.update();
            if (gamepad2.options){
                Pinpoint.recalibrateIMU();
                Pinpoint.resetPosAndIMU();
            }
            // slow down method
            if (gamepad2.right_trigger > .1) {
                driveVelocityCap = .3;

            } else {
                driveVelocityCap = .8;
            }

            //get robot heading from pinpoint
            Pinpoint.update();
            double botheading= Pinpoint.getHeading(AngleUnit.RADIANS);

            //DRIVE Split Arcade

            double fwd = -gamepad2.left_stick_y;
            double strafe = gamepad2.left_stick_x*1.1;
            //Quantity to turn by (turn)
            double turn = gamepad2.right_stick_x;
            double roty =strafe * Math.sin(-botheading) + fwd * Math.cos(-botheading);
            double rotx =strafe * Math.cos(-botheading) - fwd * Math.sin(-botheading);

            rotx = rotx*1.1;
            /**
             * Control a mecanum drive base with three double inputs
             *
             * @param rotx  is the first double X value which represents how the base should strafe
             * @param roty is the only double Y value which represents how the base should drive forward
             * @param Turn    is the second double X value which represents how the base should turn
             */
            //Find the magnitude of the controller's input


            //returns point from +X axis to point (forward, strafe)

            double denominator = Math.max(Math.abs(fwd) + Math.abs(strafe) + Math.abs(turn), 1);
            double frontLeftPower = ((roty + rotx + turn) / denominator)*driveVelocityCap;
            double backLeftPower = ((roty - rotx + turn) / denominator)*driveVelocityCap;
            double frontRightPower = ((roty - rotx - turn) / denominator)*driveVelocityCap;
            double backRightPower = ((roty + rotx - turn )/ denominator)*driveVelocityCap;

            //double vX represents the velocities sent to each motor


            FWDrightDrive.setPower(frontRightPower);
            FWDleftDrive.setPower(frontLeftPower);
            BCKrightDrive.setPower(backRightPower);
            BCKleftDrive.setPower(backLeftPower);


            // Send telemetry data to the driver station
            telemetry.addData("DeadwheelX POS",Pinpoint.getPosX());
            telemetry.addData("DeadwheelY POS",Pinpoint.getPosY());
            telemetry.addData("Heading",Pinpoint.getHeading());
            telemetry.update();
        }
        }
    }


