package org.firstinspires.ftc.teamcode;
import android.view.accessibility.AccessibilityRecord;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// Arm and Wrist target positions for each state

@Config
@Autonomous(name = "UPPER_RED_TEST_AUTO", group = "Autonomous")
public class autoOne extends LinearOpMode {
    private static final int TORRETA_POSITION_RIGHT_HIGH = -600; //tenemos q cambiar estas posiciones de la torreta a q este bien
    private static final int TORRETA_POSITION_RIGHT_LOW = -300;
    private static final int TORRETA_POSITION_LEFT_HIGH = 600;
    private static final int TORRETA_POSITION_LEFT_LOW = 300;


    //----------- mecanism 1
    public class Torreta {
        private DcMotor torreta;

        public Torreta(HardwareMap hardwareMap) {
            torreta = hardwareMap.get(DcMotor.class, "torreta");
            torreta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            torreta.setTargetPosition(TORRETA_POSITION_LEFT_HIGH); //checar
            //torreta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            torreta.setPower(0.8);
        }

        //accion 1 CHECK
        public class rightHigh implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    torreta.setPower(0.8);
                    initialized = true;
                }

                double pos = torreta.getCurrentPosition();
                packet.put("liftPos", pos);
                torreta.setTargetPosition(TORRETA_POSITION_RIGHT_HIGH);
                return false;
            }
        }

        public Action rightHigh() {
            return new Torreta.rightHigh();
        }

        //accion 2 CHECK
        public class rightLow implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    torreta.setPower(0.8);
                    initialized = true;
                }

                double pos = torreta.getCurrentPosition();
                packet.put("liftPos", pos);
                torreta.setTargetPosition(TORRETA_POSITION_RIGHT_LOW);
                return false;
            }
        }

        public Action rightLow() {
            return new Torreta.rightLow();
        }

        //accion 3 CHECK
        public class leftHigh implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    torreta.setPower(0.8);
                    initialized = true;
                }

                double pos = torreta.getCurrentPosition();
                packet.put("liftPos", pos); //bueno pero q es liftpos y lo cambio a torretapos o q pedito
                torreta.setTargetPosition(TORRETA_POSITION_LEFT_HIGH);
                return false;
            }
        }

        public Action leftHigh() {
            return new Torreta.leftHigh();
        }

        //accion 4 CHECK
        public class leftLow implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    torreta.setPower(0.8);
                    initialized = true;
                }

                double pos = torreta.getCurrentPosition();
                packet.put("liftPos", pos); //hmmm
                torreta.setTargetPosition(TORRETA_POSITION_LEFT_LOW);
                return false;
            }
        }

        public Action leftLow() {
            return new Torreta.leftLow();
        }
    }


    //----------- mecanism 2
    public class Intake { //tmb es el conveyor
        private DcMotor intake;

        public Intake(HardwareMap hardwareMap) {
            intake = hardwareMap.get(DcMotor.class, "intake");
            intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //checa si es brake o float
            intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            intake.setPower(0.8);
        }

        //accion 1
        public class intakeOn implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    intake.setPower(0.8);
                    initialized = true;

                }
                return true;
            }
        }

        public Action IntakeOn() {
            return new Intake.intakeOn();
        }
    }


    //accion 2
    /* public class intakeOff implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                intake.setPower(-0.8);
                initialized = true;
            }

            double pos = intake.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > 100.0) {
                return true;
            } else {
                intake.setPower(0);
                return false;
            }
        }
    }

    public Action IntakeOff() {
        return new Intake.intakeOff();
    }
} */

    //accion 1
   /* public class intakeIn implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) { //ayuda a hacerlo de potencias
            if (!initialized) {
                intake.setPower(0.8);
                initialized = true;
            }

            double pos = intake.getCurrentPosition();
            packet.put("liftPos", pos);
            intake.setPower(0);
            return false;
        }
    }

    public Action intakeIn() {
        return new intakeIn();
    }
} */



    //----------- mecanism 3
    public class Shooter {
        private DcMotor shooter;

        public Shooter(HardwareMap hardwareMap) {
            shooter = hardwareMap.get(DcMotor.class, "shooter1");
            shooter = hardwareMap.get(DcMotor.class, "shooter2");
            shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        //accion 1
        public class distanceOne implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    shooter.setPower(0.5);
                    initialized = true;

                }
                return true;
            }
        }

        public Action distanceOne() {
            return new Shooter.distanceOne();
        }


        //accion 2
        public class distanceTwo implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    shooter.setPower(0.8);
                    initialized = true;

                }
                return true;
            }
        }

        public Action distanceTwo() {
            return new Shooter.distanceTwo();
        }


        //accion 3
        public class shooterOff implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    shooter.setPower(0);
                    initialized = true;

                }
                return true;
            }
        }

        public Action shooterOff() {
            return new Shooter.shooterOff();
        }
    }

    //route
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-50, 48, Math.toRadians(53));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Torreta torreta = new Torreta(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);

        Action traject1 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-13, 13), Math.toRadians(90))
                .build();

        Action traject2 = drive.actionBuilder(new Pose2d(-13, 13, 90))
                .waitSeconds(3)
                //shoots
                .build();

        Action traject3 = drive.actionBuilder(new Pose2d(-19, -26, 0)) //desde aqui no esta cambiado nada
                .splineTo(new Vector2d( -12, -29), Math.toRadians(-90))
                //intake starts
                .strafeTo(new Vector2d( -12, -29))
                .strafeTo(new Vector2d( -12, -46))
                .build();

        Action traject4 = drive.actionBuilder(new Pose2d(-12, -46, 0))
                .strafeTo(new Vector2d( -38, -46))
                .build();

        Action traject5 = drive.actionBuilder(new Pose2d(-38, -46, 0))
                .waitSeconds(3)
                //shoots
                .build();

        Action traject6 = drive.actionBuilder(new Pose2d(-38, -46, 0))
                .strafeTo(new Vector2d( 12, -29))
                //intake starts
                .strafeTo(new Vector2d( 12, -48))
                .build();

        Action traject7 = drive.actionBuilder(new Pose2d(12, -48, 0))
                .strafeTo(new Vector2d( -38, -46))
                .build();

        Action traject8 = drive.actionBuilder(new Pose2d(-38, -46, 0))
                .waitSeconds(3)
                //shoots
                .build();

        Action traject9 = drive.actionBuilder(new Pose2d(-38, -46, 0))
                .strafeTo(new Vector2d( 36, -29))
                //empieza intake
                .strafeTo(new Vector2d( 36, -48))
                .build();

        Action traject10 = drive.actionBuilder(new Pose2d(36, -48, 0))
                .strafeTo(new Vector2d( -38, -46))
                .build();

        Action traject11 = drive.actionBuilder(new Pose2d(-38, -46, 0))
                .waitSeconds(1) //shoots
                .build();
// end of route


        // vision here that outputs position
        int visionOutputPosition = 1;
        waitForStart();

        Actions.runBlocking(new SequentialAction(traject1));

        Actions.runBlocking(new SequentialAction(
                traject2,
                // torreta.leftHigh(),
                shooter.distanceTwo()
        ));  //shoots

        Actions.runBlocking(shooter.shooterOff());

       /* Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        traject3,
                        shooter.shooterOff(),
                        intake.IntakeOn()
                )
                ));//intake

        Actions.runBlocking(new SequentialAction(traject4));

        Actions.runBlocking(new SequentialAction(
                traject5,
               // torreta.leftLow(),
                shooter.distanceOne()
        ));  //shoots

        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        traject6,
                        shooter.shooterOff(),
                        intake.IntakeOn()
                )
        ));//intake

        Actions.runBlocking(new SequentialAction(traject7));

        Actions.runBlocking(new SequentialAction(
                traject8,
               // torreta.leftLow(),
                shooter.distanceOne()
        ));  //shoots

        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                        traject9,
                        shooter.shooterOff(),
                        intake.IntakeOn()
                )
        ));//intake

        Actions.runBlocking(new SequentialAction(traject10));

        //Actions.runBlocking(new SequentialAction(traject11)); */



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


    }
}