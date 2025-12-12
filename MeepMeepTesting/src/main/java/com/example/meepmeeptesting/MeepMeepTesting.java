package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class MeepMeepTesting {


    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        /* myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, 36, Math.toRadians(135))) // este no

                .turn(Math.toRadians(-130))
                .lineToX(-11)
                .turn(Math.toRadians(90))
                .lineToY(50)
                .turn(Math.toRadians(130))
                .lineToX(-30)
                .turn(Math.toRadians(-90))
                .turn(Math.toRadians(233))
                .lineToX(12)
                .turn(Math.toRadians(83)) */

        //.splineTo(new Vector2d(-11,38),Math.toRadians(45)) .waitSeconds(.5)

       /* myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-50, 48, Math.toRadians(-53))) //este tampoco

                        .waitSeconds(1)
                        .splineTo(new Vector2d(-16,18), Math.toRadians(0))
                       // .waitSeconds(.5)
                        .splineTo(new Vector2d(-10,44), Math.toRadians(78))
                        //.waitSeconds(.5)
                        .splineTo(new Vector2d(-25,28), Math.toRadians(-110))
                        .waitSeconds(1)
                        .splineTo(new Vector2d(12,34), Math.toRadians(95))
                        .splineTo(new Vector2d(12,46), Math.toRadians(95))
                        .splineTo(new Vector2d(-25,28), Math.toRadians(-190))
                        .waitSeconds(1)
                        .splineTo(new Vector2d(35,34), Math.toRadians(95))
                        .splineTo(new Vector2d(35,46), Math.toRadians(95))
                        .splineTo(new Vector2d(-25,28), Math.toRadians(-210)) */


         /* myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-50, 48, Math.toRadians(-53))) //alianza roja

                 .waitSeconds(3)
                 .splineTo(new Vector2d(-13, 13), Math.toRadians(90))
                 //.lineToXLinearHeading(-11, Math.toRadians(90))
                 //.lineToYLinearHeading(20, Math.toRadians(90))
                 .waitSeconds(3)
                .strafeTo(new Vector2d(-11,49))
                .strafeTo(new Vector2d(-31,31))
                .waitSeconds(3)
                .strafeTo(new Vector2d(12,30))
                .strafeTo(new Vector2d(12,48))
                .strafeTo(new Vector2d(-31,31))
                .waitSeconds(3)
                .strafeTo(new Vector2d(36,30))
                .strafeTo(new Vector2d(36,49))
                .strafeTo(new Vector2d(-31,31))

                        .build()); */

        /* myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-50, -48, Math.toRadians(53))) //alianza azul

                .waitSeconds(3)
                .splineTo(new Vector2d(-13, -13), Math.toRadians(-90))
                .waitSeconds(3)
                .strafeTo(new Vector2d(-11,-49))
                .strafeTo(new Vector2d(-31,-31))
                .waitSeconds(3)
                .strafeTo(new Vector2d(12,-30))
                .strafeTo(new Vector2d(12,-48))
                .strafeTo(new Vector2d(-31,-31))
                .waitSeconds(3)
                .strafeTo(new Vector2d(36,-30))
                .strafeTo(new Vector2d(36,-49))
                .strafeTo(new Vector2d(-31,-31))

                .build()); */

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-61, -12, 0)) //alianza azul daira
                .strafeTo(new Vector2d( -19, -26))

                .waitSeconds(4) //shootea

                .splineTo(new Vector2d( -12, -29), Math.toRadians(-90))

                .strafeTo(new Vector2d( -12, -29)) //intake
                .strafeTo(new Vector2d( -12, -46))

                .strafeTo(new Vector2d( -38, -46))

                .waitSeconds(4)//shootea

                .strafeTo(new Vector2d( 12, -29))

                .strafeTo(new Vector2d( 12, -48)) //intake

                .strafeTo(new Vector2d( -38, -46))

                .waitSeconds(4) //shootea

                .strafeTo(new Vector2d( 36, -29))

                .strafeTo(new Vector2d( 36, -48)) //intake

                .strafeTo(new Vector2d( -38, -46))

                .waitSeconds(4) //shootea

                .build());

        /* myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-61, 12, 0)) //alianza roja daira
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
                .strafeTo(new Vector2d( 36, 48))
                .strafeTo(new Vector2d( -38, 46))

                .build()); */

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK) //Credits to Team Juice 16236!!
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}