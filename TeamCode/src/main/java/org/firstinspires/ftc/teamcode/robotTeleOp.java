package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class robotTeleOp extends LinearOpMode {
    int Tpos= 1800;
    DcMotor Torreta = null;
    DcMotor Shoot1 = null;
    DcMotor Shoot2 = null;
    @Override
    public void runOpMode() {


        Torreta=hardwareMap.get(DcMotor.class,"Torreta");
        Shoot1=hardwareMap.get(DcMotor.class,"Shoot1");
        Shoot2=hardwareMap.get(DcMotor.class,"Shoot2");

        Torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Shoot1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Shoot2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Torreta.setTargetPosition(Tpos);
        Torreta.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Shoot1.setDirection(DcMotorSimple.Direction.REVERSE);
        Shoot2.setDirection(DcMotorSimple.Direction.FORWARD);


        waitForStart();

        while (opModeIsActive()){

        if (gamepad2.right_bumper){
             Tpos+=1;
            }
        else if(gamepad2.left_bumper) {
            Tpos -= 1;
            }
        Torreta.setTargetPosition(Tpos);

        if (gamepad2.right_trigger<0.5) {
            Shoot1.setPower(0.8);
            Shoot2.setPower(0.8);
            }
        else {
            Shoot1.setPower(0);
            Shoot2.setPower(0);
             }
        if (Tpos>=3600) {
            Tpos = 3600;
        }
        else if (Tpos<=1){
            Tpos=1;
        }

        }

    }
}
