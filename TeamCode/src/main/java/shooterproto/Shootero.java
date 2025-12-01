package shooterproto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Shootero extends LinearOpMode {
    private int Tpos = 1800;
    private DcMotor Torreta = null;
    private DcMotor Shoot1 = null;
    private DcMotor Shoot2 = null;

    public void runOpMode() {
        Torreta = hardwareMap.get(DcMotor.class, "Torreta");
        Shoot1 = hardwareMap.get(DcMotor.class, "Shooter1");
        Shoot2 = hardwareMap.get(DcMotor.class,"Shooter2");

        Torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Shoot1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Shoot2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Shoot1.setDirection(DcMotorSimple.Direction.REVERSE);
        Shoot1.setDirection(DcMotorSimple.Direction.FORWARD);
        runOpMode();
        while(opModeIsActive()){
            if(gamepad2.right_bumper) {
                Tpos += 1;
            }
            else if (gamepad2.left_bumper) {
                Tpos -= 1;
            }
            Torreta.setTargetPosition(Tpos);
            if (gamepad2.right_trigger > 0.5) {
                Shoot1.setPower(0.8);
                Shoot2.setPower(0.8);
            }
            else {
                Shoot1.setPower(0);
                Shoot2.setPower(0);
            }
            if(Tpos == 3600) {
                Tpos = 3600;
            }
            else if (Tpos <= 1) {
                    Tpos = 1;
                }
        }
        }
    }
