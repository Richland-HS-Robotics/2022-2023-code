/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a
 * 'program' that runs in either the autonomous or the teleop period of an FTC
 * match. The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode class is
 * instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two
 * wheeled robot It includes all the skeletal structure that all linear OpModes
 * contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code
 * folder with a new name. Remove or comment out the @Disabled line to add this
 * opmode to the Driver Station OpMode list
 */

@TeleOp(name = "RobotTest", group = "Linear Opmode")

public class RobotTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor upDown = null;
    private Servo grabber = null;
    private DcMotor linearSlideLeft = null;
    private DcMotor linearSlideRight = null;
    private double servoPosition =0.0;
    private double armPosition = 0.0; // arm starts down

    public void initialize() {
        // Initialize the hardware variables. Note that the strings used here as
        // parameters to 'get' must correspond to the names assigned during the
        // robot configuration step (using the FTC Robot Controller app on the
        // phone).
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        upDown = hardwareMap.get(DcMotor.class, "upDown");
        grabber = hardwareMap.get(Servo.class, "grabServo");
        linearSlideLeft = hardwareMap.get(DcMotor.class, "linearSlideLeft");
        linearSlideRight = hardwareMap.get(DcMotor.class, "linearSlideRight");

        // grabber.setPosition(0.0);
        // grabber.setDirection(Servo.Direction.REVERSE);

        // To drive forward, most robots need the motor on one side to be
        // reversed, because the axles point in opposite directions. Pushing the
        // left stick forward MUST make robot go forward. So adjust these two
        // lines based on your first test drive. Note: The settings here assume
        // direct drive on left and right wheels. Gear Reduction or 90 Deg
        // drives may require direction flips
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // upDown.setDirection(DcMotor.Direction.FORWARD);
        linearSlideLeft.setDirection(DcMotor.Direction.FORWARD);
        // this needs to be reverse because the motors are flipped
        linearSlideRight.setDirection(DcMotor.Direction.REVERSE);
    }

    // This is the main gameloop for the remote control gamemode. It waits for
    // the driver to press PLAY and then runs until he presses STOP.
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialize();// initialize motors

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            handleDriving();
            handleLifting();
            handleGrabbing();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    // Handle grabbing.
    public void handleGrabbing() {
        grabber.setPosition(servoPosition);
        servoPosition+=0.1;
        if (gamepad1.x) {
            telemetry.addData("Keypad: ","X");
            telemetry.update();
            grabber.setPosition(1.0); // close
        }
        if (gamepad1.a) {
            grabber.setPosition(0.6); // medium
        }
        if (gamepad1.b) {  // open
            grabber.setPosition(0.0); //
        }

    }

    // Handle lifting.
    //
    // Lifting is controlled with the right joystick y axis.
    public void handleLifting() {
        // set the power for the lifter motor, based on the left and right trigger.
        armPosition+=gamepad1.right_stick_y;
        upDown.setPower(gamepad1.right_stick_y);
        linearSlideLeft.setPower(gamepad1.right_stick_y);
        linearSlideRight.setPower(gamepad1.right_stick_y);
        
        // if (gamepad1.right_stick_y > 0) { // up
        //     linearSlideLeft.setPower(0.6);
        //     linearSlideRight.setPower(0.6);
        // } else {
        //     linearSlideLeft.setPower(0);
        //     linearSlideRight.setPower(0);
        // }
    }

    // Handle driving the robot.
    //
    // Gamepad left stick is responsible for forward/back and strafing
    // left/right, and the right stick is responsible for turning.
    public void handleDriving() {
        double y = gamepad1.left_stick_y; // foward/back
        double x = gamepad1.left_stick_x; // strafe
        double rx = -gamepad1.right_stick_x; // rotation

        // Find the correct power for each motor
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = -((y + x + rx) / denominator);
        double backLeftPower = -((y - x + rx) / denominator);
        double frontRightPower = ((y - x - rx) / denominator);
        double backRightPower = ((y + x - rx) / denominator);

        // Set the power of each motor
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

}
