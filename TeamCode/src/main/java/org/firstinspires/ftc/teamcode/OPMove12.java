package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;


//no claw
public class OPMove12 extends LinearOpMode {


    //instance variables
    private DcMotor LeftFront = null;
    private DcMotor RightFront = null;
    private DcMotor LeftBack = null;
    private DcMotor RightBack = null;
    private Servo boxFlip = null;
    private DcMotorEx spool = null;
    private DcMotor Sweeper = null;
    private Servo door = null;
    private Servo rack = null;


    //2
    private Telemetry telemetry;
    // constructor
    public OPMove12(OpMode opmode){
        // set up wheels
        hardwareMap = opmode.hardwareMap;
        telemetry = opmode.telemetry;


        door = hardwareMap.servo.get("door");
        LeftFront  = hardwareMap.dcMotor.get("LeftFront");
        RightFront = hardwareMap.dcMotor.get("RightFront");
        LeftBack  = hardwareMap.dcMotor.get("LeftBack");
        RightBack = hardwareMap.dcMotor.get("RightBack");
        spool = hardwareMap.get(DcMotorEx.class, "spool");
        Sweeper = hardwareMap.dcMotor.get("Sweeper");
        rack = hardwareMap.servo.get("Rack");
        boxFlip = hardwareMap.servo.get("box");

        RightFront.setDirection(DcMotor.Direction.FORWARD);
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        RightBack.setDirection(DcMotor.Direction.REVERSE);
        LeftBack.setDirection(DcMotor.Direction.FORWARD);
        Sweeper.setDirection(DcMotor.Direction.FORWARD);

        LeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        spool.setDirection(DcMotorEx.Direction.FORWARD);
        spool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spool.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        hardwareMap = opmode.hardwareMap;
        telemetry = opmode.telemetry;
        //arm motors




    }

    double LF, RF, LB, RB;

    //THIS IS THE METHOD OVERLOADING
    public void backwards(double power, int ms) {
        //left right left right
        LF = RF = LB = RB = power;

        setMotorPower(LF, RF, LB, RB);


        sleep(ms);


        setAllPower(0d);
    }

    //drops the pixels on the backboard
    public void dropPixelsBaBo(){

        //subject to change
        //box drops the pixel
        boxFlip.setPosition(0.20);
        armUp();

        sleep(500);

        openDoor();
        sleep(1500);
        closeDoor();
        sleep(200);
        //box is back in the normal position
        boxFlip.setPosition(0.55);
        sleep(500);
        armDown();
    }
    public void forwards(double power, int ms) {

        setAllPower(power * -1);


        sleep(ms);


        setAllPower(0d);
    }

    // drive right (og)
    public void left(double power, int ms) {

        LF = RF = LB = RB = power;

        setMotorPower(LF * -1, RF, LB * -1, RB);


        sleep(ms);


        setAllPower(0d);
    }
    // drive left (og)
    public void right(double power, int ms) {
        LF = RF = LB = RB = power;
        //front left right back left right
        setMotorPower(LF, RF * -1, LB, RB * -1);


        sleep(ms);


        setAllPower(0d);
    }


    // drive backwakrds



    public void stop(int ms) {
        setAllPower(0d);
        sleep(ms);
    }


    public void turnRight(double power, int ms) {
        LF = RF = LB = RB = power;
        //front left right back left right
        setMotorPower(LF, RF * -1, LB * -1, RB);


        sleep(ms);


        setAllPower(0d);
    }


    public void turnLeft(double power, int ms) {
        LF = RF = LB = RB = power;
        //front left right back left right
        setMotorPower(LF * -1, RF, LB, RB * -1);


        sleep(ms);


        setAllPower(0d);
    }

    public void armUp(){
        telemetry.addData("ARM UP", "true");
        telemetry.update();
        spool.setTargetPosition(1000);
        spool.setDirection(DcMotor.Direction.REVERSE);
        spool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spool.setPower(1);
    }

    public void armDown(){
        telemetry.update();
        spool.setTargetPosition(0);
        spool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spool.setPower(-0.7);
    }

    public void sweeperRun(int ms){
        Sweeper.setPower(1);
        sleep(ms);
        Sweeper.setPower(0);
    }

    public void closeDoor(){
        door.setPosition(0.5);
        if(door.getPosition() == 0.01){
            door.setPosition(0.5);
        }
    }

    public void openDoor(){
        door.setPosition(0.01);
    }

    public void rackDown(){
        rack.setPosition(1.0);
    }

    public void rackUp(){
        rack.setPosition(0.4);
    }

    // Set power to all motors
    public void setAllPower(double p) {
        setMotorPower(p,p,p,p);
    }


    // Set power to motors
    public void setMotorPower(double LF, double RF, double LB, double RB) {
        LeftFront.setPower(LF);
        RightFront.setPower(RF);
        LeftBack.setPower(LB);
        RightBack.setPower(RB);
    }


    //dont put anything here, not a main opmode
    @Override
    public void runOpMode() {


    }


}
