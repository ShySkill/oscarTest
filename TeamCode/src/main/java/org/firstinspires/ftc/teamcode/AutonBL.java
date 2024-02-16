package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
//DOES NOT INCLUDE CLAW

@Autonomous(name="Auton Blue Left (BL)")
public class AutonBL extends LinearOpMode{

    //stuff to change
    boolean park = true;
    boolean farSide = false;

    public void runOpMode() throws InterruptedException{
        //init the necessary objects
        OPMove12 karel = new OPMove12(this);
        GetColorBlue color = new GetColorBlue();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(color);

        final int cameraWidth = 1184;
        final int cameraHeight = 656;

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });



        //view the webcam now, you cant check camera stream once the game is initialized.
        waitForStart();

        telemetry.addData("Status", "Running now");
        //goes through the pipeline and checks which ones are true.
        boolean isOnRight = color.getLatestResultR();
        boolean isOnMiddle = color.getLatestResultM();
        boolean isOnLeft = color.getLatestResultL();
        String result = color.debugging();

        System.out.println("right: " + isOnRight + "middle: " + isOnMiddle + "left: " + isOnLeft);
        //obj is on right
        if(isOnRight){
            //this means the object is on the right
            telemetry.addData("Status:", "Object is on the right" + result);
            telemetry.update();
            placePurplePixel(true, false, false, karel);
            if (park) parkInBackZone(3, karel); landYellowWhitePixel(3, karel);

        }
        //obj is in the middle
        if(isOnMiddle){
            //this means that its on the middle
            telemetry.addData("Status:", "Object is on the middle" + result);
            telemetry.update();
            placePurplePixel(false, false, true, karel);
            if (park) parkInBackZone(2, karel); landYellowWhitePixel(2, karel);
        }
        //obj is in the middle
        if(isOnLeft){
            //this means that its on the left
            telemetry.addData("Status:", "Object is on the left" + result);
            telemetry.update();
            placePurplePixel(false, true, false, karel);
            if (park) parkInBackZone(1, karel); landYellowWhitePixel(1, karel);
        }



        //waits apon crash
        telemetry.addData("Status", "GG");
        telemetry.update();
        sleep(1000);
    }
    public void placePurplePixel(boolean isRight, boolean isLeft, boolean isMiddle, OPMove12 karel){
        if(isRight){
            karel.forwards(0.5, 800);
            karel.stop(1000);
            karel.rackDown();
            karel.stop(1000);
            karel.rackUp();
            karel.stop(500);
            karel.left(0.5, 200);
            karel.stop(1000);
            karel.forwards(0.5, 1000);
            karel.stop(1000);
            karel.turnRight(0.5, 515);
            karel.sleep(1000);
        }

        if(isLeft){
            karel.forwards(0.5, 850);
            karel.stop(500);
            karel.turnLeft(0.5, 1100);
            karel.stop(500);
            karel.right(0.5, 300);
            karel.rackDown();
            karel.stop(500);
            karel.rackUp();
            karel.left(0.5, 300);
            karel.sleep(500);
            karel.turnRight(0.5, 100);
            karel.sleep(200);
            karel.backwards(0.5, 900);
            karel.sleep(500);
            karel.turnLeft(0.5, 450);
        }
        if(isMiddle){
            karel.left(0.5, 300);
            karel.sleep(1500);
            karel.forwards(0.5, 1050);
            karel.rackDown();
            karel.sleep(1500);
            karel.rackUp();
            karel.left(0.5, 250);
            karel.sleep(1500);
            karel.forwards(0.5, 800);
            karel.sleep(1500);
            karel.turnRight(0.5, 450);

        }
    }

    //make the robot go almost to the end at certain speed, then change and go slowly into the wall, then do the rest.
    public void parkInBackZone(int spikemarkloc, OPMove12 karel){

        telemetry.addData("Parking in back zone for pos", spikemarkloc);
        telemetry.update();
        if(farSide) {
            karel.backwards(0.5, 2500);
        }else{
            karel.backwards(0.5, 1350);
        }
        karel.sleep(500);
        karel.backwards(0.2, 2000);
    }


    public void landYellowWhitePixel(int spikemarkloc, OPMove12 karel){

        karel.sleep(100);
        //moves right to hit the board softly to adjust itself
        karel.right(0.3, 1000);
        karel.sleep(500);
        //moves slightly away from the board crease
        karel.left(0.5, 200);
        karel.sleep(500);
        //aligns with the y value of the board
        karel.forwards(0.5, 400);
        karel.sleep(500);
        //moves in position
        //pos to strafe
        switch (spikemarkloc){
            //if its on the right
            case 1:
                //if its on the left
                telemetry.addData("Status:", "placing on left");
                karel.right(0.5, 1100);
            case 2:
                //if its on the middle
                telemetry.addData("Status:", "placing on middle");
                karel.right(0.5, 700);

            case 3:
                //if its on the right
                telemetry.addData("Status:", "placing on right");
                karel.right(0.5, 400);
        }

        //pos to strafe
        karel.sleep(500);
        karel.backwards(0.2, 1500);
        karel.sleep(200);
        karel.forwards(0.1, 200);
        karel.dropPixelsBaBo();
        karel.sleep(400);
        //parks
        karel.left(0.5, 900);
    }

}

