package shooterproto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ShooterMotor extends LinearOpMode {
    private DcMotor shooterMotor = null;

    public void runOpMode() {
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                    shooterMotor.setPower(.5);

            } else shooterMotor.setPower(0);

            }
        }
    }

