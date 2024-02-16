package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.Telemetry;


//no claw
public class EncoderTester extends LinearOpMode {


    //instance variables
    private DcMotor LeftFront = null;
    private DcMotor RightFront = null;
    private DcMotor LeftBack = null;
    private DcMotor RightBack = null;

    private DcMotorEx spool = null;
    private DcMotor Sweeper = null;
    private Servo door = null;
    private Servo rack = null;

    //circumference in inches
    //plug in the diameter of mm
    private static double circumference = calculateCircumference(100);
    //inches/circumference multiplied by tick count per motor
    //2
    private static double tick_count = 537.6;
    private Telemetry telemetry;
    // constructor
    public EncoderTester(OpMode opmode){
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

    public static double calculateCircumference(int diameter){
        return 2 * ((double) diameter /2) * 3.1415926543589793238462643383279502884197169399;
    }
    //julian wants to be called mr poggierre
    public void setTargetPositions(double inches){
        LeftFront.setTargetPosition(calculateEncoderTicks(inches));
        LeftBack.setTargetPosition(calculateEncoderTicks(inches));
        RightFront.setTargetPosition(calculateEncoderTicks(inches));
        RightBack.setTargetPosition(calculateEncoderTicks(inches));

    }

    public void runToPosition(){
        LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void forwards(double inches, double power){
        power *= -1;
        resetEncoders();
        setTargetPositions(inches);
        runToPosition();
        setAllPower(power);
    }


    public void resetEncoders(){
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    private int calculateEncoderTicks(double inches){
        //              this part gets the ticks per inches, we multiply based on the amount of inches
        return (int) (tick_count/circumference * inches);
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
