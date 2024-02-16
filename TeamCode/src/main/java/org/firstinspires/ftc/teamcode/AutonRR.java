package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
//DOES NOT INCLUDE CLAW

@Autonomous(name="Auton Red Right (RR)")
public class AutonRR extends LinearOpMode{

    //stuff to change

    boolean park = true;
    boolean farSide = false;

    public void runOpMode() throws InterruptedException{
        //init the necessary objects
        OPMove12 karel = new OPMove12(this);
        GetColorRed color = new GetColorRed();

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
            if (park) parkInBackZone(6, karel); landYellowWhitePixel(6, karel);

        }
        //obj is in the middle
        if(isOnMiddle){
            //this means that its on the middle
            telemetry.addData("Status:", "Object is on the middle" + result);
            telemetry.update();
            placePurplePixel(false, false, true, karel);
            if (park) parkInBackZone(5, karel); landYellowWhitePixel(5, karel);
        }
        //obj is in the middle
        if(isOnLeft){
            //this means that its on the left
            telemetry.addData("Status:", "Object is on the left" + result);
            telemetry.update();
            placePurplePixel(false, true, false, karel);
            if (park) parkInBackZone(4, karel); landYellowWhitePixel(4, karel);
        }



        //waits apon crash
        telemetry.addData("Status", "MOGU EZ NO RE");
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
            karel.turnLeft(0.5, 515);
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
            karel.turnRight(0.5, 450);
        }
        if(isMiddle){
            karel.forwards(0.5, 1150);
            karel.sleep(500);
            karel.turnRight(0.5, 450);
            karel.rackDown();
            karel.sleep(1500);
            karel.rackUp();
            karel.left(0.5, 150);
            karel.sleep(1500);
            //turn a full 180 and then park
            karel.turnLeft(0.5, 1100);
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
        karel.left(0.3, 1000);
        karel.sleep(500);
        //moves slightly away from the board crease
        karel.right(0.5, 200);
        karel.sleep(500);
        //aligns with the y value of the board
        karel.forwards(0.5, 400);
        karel.sleep(500);
        //moves in position
        switch (spikemarkloc){
            //if its on the right
            case 4:
                //if its on the left
                telemetry.addData("Status:", "placing on left");
                karel.left(0.5, 400);
            case 5:
                //if its on the middle
                telemetry.addData("Status:", "placing on middle");
                karel.left(0.5, 700);

            case 6:
                //if its on the right
                telemetry.addData("Status:", "placing on right");
                karel.left(0.5, 1100);
        }
        karel.sleep(500);
        karel.backwards(0.2, 1500);
        karel.sleep(200);
        karel.forwards(0.1, 200);
        karel.dropPixelsBaBo();
        karel.sleep(400);
        //parks
        karel.right(0.5, 900);
    }

}

