package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcontroller.external.samples.RobotTeleopMecanumFieldRelativeDrive;

@Config
@Autonomous(name = "MIDDLE_RED_TEST_AUTO", group = "Autonomous")
public class autoThree extends LinearOpMode {
    public class Torreta {
        private DcMotor Torretam;

        public Torreta(HardwareMap hardwareMap) {
            Torretam = hardwareMap.get(DcMotor.class, "torreta");
            Torretam.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Torretam.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        public class SpinTorreta implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Torretam.setPower(0.8);
                    initialized = true;
                }

                double pos = Torretam.getCurrentPosition();
                packet.put("torretaPos", pos);
                if (pos < 3000.0) {
                    return true;
                } else {
                    Torretam.setPower(0);
                    return false;
                }
            }
        }

        public Action SpinTorreta() {
            return new SpinTorreta();
        }

        public class TurnTorreta implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Torretam.setPower(-0.8);
                    initialized = true;
                }

                double pos = Torretam.getCurrentPosition();
                packet.put("torretaPos", pos);
                if (pos > 100.0) {
                    return true;
                } else {
                    Torretam.setPower(0);
                    return false;
                }
            }
        }

        public Action TurnTorreta() {
            return new TurnTorreta();
        }
    }


   /* public class Shooter {
        private DcMotor Shooterm;

        public Shooter(HardwareMap hardwareMap) {
            Shooterm = hardwareMap.get(DcMotor.class, "shooterMotor");
            Shooterm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            Shooterm.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        public class setShooter implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Shooterm.setPower(0.8);
                    initialized = true;
                }

                double pos = Shooterm.getCurrentPosition();
                packet.put("shooterPos", pos);
                if (pos < 3000.0) {
                    return true;
                } else {
                    Shooterm.setPower(0);
                    return false;
                }
            }
        }

        public Action setShooter() {
            return new setShooter();
        }

        public class outShooter implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Shooterm.setPower(-0.8);
                    initialized = true;
                }

                double pos = Shooterm.getCurrentPosition();
                packet.put("shooterPos", pos);
                if (pos > 100.0) {
                    return true;
                } else {
                    Shooterm.setPower(0);
                    return false;
                }
            }
        }

        public Action outShooter() {
            return new outShooter();
        }
    }
*/

    //--------
    //-------

  public class Intake { //tmb es conveyor jej
        private DcMotor Intakem;

        public Intake(HardwareMap hardwareMap) {
            Intakem = hardwareMap.get(DcMotor.class, "intake");
            Intakem.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Intakem.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        public class IntakeOn implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Intakem.setPower(0.8);
                    initialized = true;

                }
                return true;
            }
        }
        public Action IntakeOn() {
            return new IntakeOn();
        }

        public class IntakeOff implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Intakem.setPower(-0.8);
                    initialized = true;
                }

                double pos = Intakem.getCurrentPosition();
                packet.put("intakePos", pos);
                if (pos > 100.0) {
                    return true;
                } else {
                    Intakem.setPower(0);
                    return false;
                }
            }
        }
        public Action IntakeOff() {
            return new IntakeOff();
        }
    }

//---------

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-61, 12, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Intake intake = new Intake(hardwareMap);
        //Shooter shooter = new Shooter(hardwareMap);
        Torreta torreta = new Torreta(hardwareMap);


        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d( -19, 26))
                .waitSeconds(4)
                .splineTo(new Vector2d( -12, 29), Math.toRadians(90))
                .strafeTo(new Vector2d( -12, 29))
                .strafeTo(new Vector2d( -12, 46))
                .strafeTo(new Vector2d( -38, 46))
                .waitSeconds(4)
                .strafeTo(new Vector2d( 12, 29))
                .strafeTo(new Vector2d( 12, 48))
                .strafeTo(new Vector2d( -38, 46))
                .waitSeconds(4)
                .strafeTo(new Vector2d( 36, 29))
                .strafeTo(new Vector2d( 36, 48));

        Action trajectorytry = drive.actionBuilder(new Pose2d(10, -63, Math.toRadians(90)))
                .strafeTo(new Vector2d(-38,46))
                .build();




        int visionOutputPosition = 1;
        waitForStart();

        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Position during Init", position);
            telemetry.update();
        }

        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;


        /* if (startPosition == 1) {
           trajectoryActionChosen = tab1.build();


        } */
    }
  }
