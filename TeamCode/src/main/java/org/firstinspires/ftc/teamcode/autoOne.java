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
@Autonomous(name = "UPPER_RED_TEST_AUTO", group = "Autonomous")
public class autoOne extends LinearOpMode {
    public class Torreta {
        private DcMotor Torretam;

        public Torreta(HardwareMap hardwareMap) {
            Torretam = hardwareMap.get(DcMotor.class, "torretaMotor");
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


    public class Shooter {
        private DcMotor Shooterm;

        public Shooter(HardwareMap hardwareMap) {
            Shooterm = hardwareMap.get(DcMotor.class, "shooterMotor");
            Shooterm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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



    public class Intake { //tmb es conveyor jej
        private DcMotor Intakem;

        public Intake(HardwareMap hardwareMap) {
            Intakem = hardwareMap.get(DcMotor.class, "intakeMotor");
            Intakem.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Intakem.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        //-----
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


        //------

        public class IntakeIn implements Action { //no se si esta bien checalo ahorita
            private boolean initialized = true;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Intakem.setPower(0);
                    initialized = false;

                }
                return false;
            }
        }
    }

       /* public class IntakeIn implements Action { //cambialo a turn on power and turn off power
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    Intakem.setPower(0);
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


        public Action IntakeIn() {
            return new IntakeIn();
        }
    } */


    //------


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-50, 45, Math.toRadians(210));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Torreta torreta = new Torreta(hardwareMap);


        int visionOutputPosition = 1;

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .waitSeconds(3)
                .splineTo(new Vector2d(-13, 13), Math.toRadians(90))
                .waitSeconds(3)
                .strafeTo(new Vector2d(-11, 49))
                .strafeTo(new Vector2d(-31, 31))
                .waitSeconds(3)
                .strafeTo(new Vector2d(12, 30))
                .strafeTo(new Vector2d(12, 48))
                .strafeTo(new Vector2d(-31, 31))
                .waitSeconds(3)
                .strafeTo(new Vector2d(36, 30))
                .strafeTo(new Vector2d(36, 49));

        Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .strafeTo(new Vector2d(-31, 31))
                .build();


        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Position during Init", position);
            telemetry.update();
        }

        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        if (startPosition == 1) {
            trajectoryActionChosen = tab1.build();


        }
    }

}











