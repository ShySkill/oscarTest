package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

/*
    stuff to learn:

    programming a basic motor
    programming a drivetrain to strafe
    programming in auton
    learn how to use encoders
    learn how to set up cameras
    programming servos
    program strafing / turning
    learn how to use a switch case
    use a boolean to toggle
    learn primitive java and the basics
    split drive, tank drive, arcade drive
    learn more about the controller libraries
    learn more about ftc libraries
 */

@TeleOp(name="Current Teleop - Scrims 2-10-24")


public class TheTeleop extends LinearOpMode {
    //motors (view from back in terms of L & R)
    private DcMotor RightFront = null;
    //0
    private DcMotor LeftFront = null;
    //1
    private DcMotor RightBack = null;
    //2,
    private DcMotor LeftBack = null;

    private Servo door = null;
    private Servo rack = null;
    private DcMotor Sweeper = null;
    //3
    private DcMotorEx spool = null;
    private Servo drone = null;
    private Servo boxFlip;
    private static boolean armUp = false;
    private static boolean sweeperOn = false;

    public void runOpMode() throws InterruptedException {

        RightFront = hardwareMap.dcMotor.get("RightFront");
        LeftFront = hardwareMap.dcMotor.get("LeftFront");
        RightBack = hardwareMap.dcMotor.get("RightBack");
        LeftBack = hardwareMap.dcMotor.get("LeftBack");

        spool = hardwareMap.get(DcMotorEx.class, "spool");

        door = hardwareMap.servo.get("door");
        drone = hardwareMap.servo.get("drone");
        Sweeper = hardwareMap.dcMotor.get("Sweeper");
        rack = hardwareMap.servo.get("Rack");

        //expansion port 4
        boxFlip = hardwareMap.servo.get("box");

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());



        //eveLYN-UX Mint
        RightFront.setDirection(DcMotor.Direction.REVERSE);
        LeftFront.setDirection(DcMotor.Direction.FORWARD);
        RightBack.setDirection(DcMotor.Direction.FORWARD);
        LeftBack.setDirection(DcMotor.Direction.REVERSE);
        Sweeper.setDirection(DcMotor.Direction.REVERSE);

        spool.setDirection(DcMotorEx.Direction.FORWARD);
        spool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spool.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        //init all the telemetry for ftc dashboard:
        telemetry.addData("Strafing left", "true");
        telemetry.update();
        telemetry.addData("Strafing right", "true");
        telemetry.update();
        telemetry.addData("Moving forward", "true");
        telemetry.update();
        telemetry.addData("Moving backward", "true");
        telemetry.update();
        telemetry.addData("Turned right", "true");
        telemetry.update();
        telemetry.addData("Turned left", "true");
        telemetry.update();



        waitForStart();
        drone.setPosition(-1);

        //makes sure that the timer is only reset once per button press.
        while (opModeIsActive()) {
            //ticks per revolution: 537.6
            telemetry.update();

            if (!gamepad1.dpad_left) {
                //STRAFE LEFT
                if (gamepad1.left_stick_x < 0 && !(gamepad1.left_stick_x > 0)) {
                    //only resets the timer once
                    telemetry.addData("Strafing left", "true");
                    telemetry.update();
                    RightFront.setPower(0.5);
                    LeftFront.setPower(-0.5);
                    RightBack.setPower(0.5);
                    LeftBack.setPower(-0.5);
                }
                //STRAFE RIGHT
                // the right wheels need to go outwards and the left wheels need to go inwards
                else if (gamepad1.left_stick_x > 0 && !(gamepad1.left_stick_x < 0)) {
                    telemetry.addData("Strafing right", "true");
                    telemetry.update();
                    LeftFront.setPower(0.5);
                    RightFront.setPower(-0.5);
                    LeftBack.setPower(0.5);
                    RightBack.setPower(-0.5);
                }
                //robot moving FORWARD (completely)
                //gamepad 1 is positive and not negative
                else if (gamepad1.left_stick_y < 0 && !(gamepad1.left_stick_y > 0)) {
                    //currently the robot has the front wheels moving inward and the back wheels going forward
                    telemetry.addData("Moving forward", "true");
                    telemetry.update();
                    RightFront.setPower(-0.5);
                    LeftFront.setPower(-0.5);
                    RightBack.setPower(-0.5);
                    LeftBack.setPower(-0.5);

                }
                //robot is moving REVERSE (completely)
                //gamepad 1 is negative and not positive
                else if (gamepad1.left_stick_y > 0 && !(gamepad1.left_stick_y < 0)) {
                    telemetry.addData("Moving backward", "true");
                    telemetry.update();
                    RightFront.setPower(0.5);
                    LeftFront.setPower(0.5);
                    RightBack.setPower(0.5);
                    LeftBack.setPower(0.5);
                }
                /*
                 */
                //FOR THE RIGHT STICK
                // the right stick is negative and not positive
                //left front and left back need to be going backwards, and right front and right back need to be going forward
                //TURN RIGHT
                //right stick is to the left
                else if (gamepad1.right_stick_x < 0 && !(gamepad1.right_stick_x > 0)) {
                    telemetry.addData("Turned right", "true");
                    telemetry.update();
                    LeftFront.setPower(0.4);
                    RightFront.setPower(-0.4);
                    LeftBack.setPower(-0.4);
                    RightBack.setPower(0.4);
                }
                // the right stick is positive and and not negative
                //left front and left back need to be going backwards, and right front and right back need to be going forward

                //TURN LEFT
                //right stick is to the right since its in the positives
                else if (gamepad1.right_stick_x > 0 && !(gamepad1.right_stick_x < 0)) {
                    telemetry.addData("Turned left", "true");
                    telemetry.update();
                    LeftFront.setPower(-0.4);
                    RightFront.setPower(0.4);
                    LeftBack.setPower(0.4);
                    RightBack.setPower(-0.4);
                }

                else {
                    //this signifies that a button has been let go or there is nothing being pressed.
                    //i being 0 again says that, okay now you can reset the timer again if another button is pressed.
                    telemetry.addData("Waiting", "true");
                    telemetry.update();
                    RightFront.setPower(0);
                    LeftFront.setPower(0);
                    RightBack.setPower(0);
                    LeftBack.setPower(0);
                }

                //ARM DOWN


                //closes the door
                if(gamepad1.right_bumper){
                    door.setPosition(0.5);
                    telemetry.addData("julian:", door.getPosition());
                    telemetry.update();
                }
                //opens the door
                if(gamepad1.left_bumper){
                    door.setPosition(0);
                    telemetry.addData("julian:", door.getPosition());
                    telemetry.update();
                }

                if(gamepad1.left_trigger > 0 && !spool.isBusy()) {
                    //moves the arm up
                    telemetry.addData("ARM UP", "true");
                    telemetry.update();
                    boxFlip.setPosition(0.20);
                    spool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    armUp = true;
                    //tickcount is 537
                    //1460 is about 2.5 revolutions or whatever 1460/537 is
                    //537 encoder ticks = 360 degrees on a motor
                    spool.setTargetPosition(1460);
                    spool.setDirection(DcMotor.Direction.REVERSE);
                    spool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    spool.setPower(1.5);
                }

                if(gamepad1.right_trigger > 0 && !spool.isBusy()){
                    //moves the arm down
                    telemetry.addData("rt working", true);
                    telemetry.update();
                    telemetry.update();
                    boxFlip.setPosition(0.55);
                    armUp = false;
                    spool.setTargetPosition(0);
                    spool.setDirection(DcMotor.Direction.FORWARD);
                    spool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    spool.setPower(-0.7);
                }

                if(gamepad1.dpad_right) {
                    //box is in dropping pixel position
                    boxFlip.setPosition(0.20);
                }
                if(gamepad1.dpad_left){
                    //box is in intake/picking up position.
                    //greater the pos - farther the angle
                    //pos > 0.5 == obtuse angle
                    boxFlip.setPosition(0.55);
                }

                if(gamepad1.left_stick_button){
                    telemetry.addData("box pos", boxFlip.getPosition());
                }

                if(gamepad1.a){
                    Sweeper.setDirection(DcMotor.Direction.FORWARD);
                    Sweeper.setPower(8);
                }else{
                    Sweeper.setDirection(DcMotor.Direction.REVERSE);
                    Sweeper.setPower(0);
                }

                if(gamepad1.x && !sweeperOn) {
                    Sweeper.setDirection(DcMotor.Direction.REVERSE);
                    Sweeper.setPower(8);
                    sweeperOn = true;
                } else if(gamepad1.x && sweeperOn){
                    Sweeper.setDirection(DcMotor.Direction.REVERSE);
                    Sweeper.setPower(0);
                    sweeperOn = false;
                }
                //rack is all the way down
                if(gamepad1.dpad_up){
                    rack.setPosition(1.0);
                }
                //rack is all the way up.
                if(gamepad1.dpad_down){
                    rack.setPosition(0.45);
                }
                //drone thing
                if (gamepad1.b) {
                    drone.setPosition(-0.5);
                    telemetry.addData("drone launched", "true");
                    telemetry.update();
                }
            }
        }
    }
}

