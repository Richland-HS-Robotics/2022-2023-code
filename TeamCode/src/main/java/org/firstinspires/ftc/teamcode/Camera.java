package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;


/**
 * The main camera class.
 *
 * This is responsible for all object recognition tasks.
 */
public class Camera {
    private static String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    private static String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };


    private static String VUFORIA_KEY;

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public Camera(HardwareMap hardwareMap, String asset, String[] labels, String vuforia_key) {
        TFOD_MODEL_ASSET = asset;
        LABELS = labels;
        VUFORIA_KEY = vuforia_key;
        initVuforia();
        initTfod(hardwareMap);
    }

    public Camera(HardwareMap hardwareMap, String vuforia_key) {
        VUFORIA_KEY = vuforia_key;
        initVuforia();
        initTfod(hardwareMap);
    }

    /**
     * Run the sample object recognitions.  You should probably not use this,
     * instead use getAllRecognizedObjects or getMostRecognizedObject.
     *
     * @param telemetry - The telemetry variable in opmode.
     */
    public void handleTfod(Telemetry telemetry) {
        if (tfod == null) {
            return;
        }
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions == null) {
            return;
        }

        telemetry.addData("# Objects Detected", updatedRecognitions.size());

        for (Recognition recognition : updatedRecognitions) {
            double col = (recognition.getLeft() + recognition.getRight()) / 2;
            double row = (recognition.getTop() + recognition.getBottom()) / 2;
            double width = Math.abs(recognition.getRight() - recognition.getLeft());
            double height = Math.abs(recognition.getTop() - recognition.getBottom());

            telemetry.addData("", " ");
            telemetry.addData(
                    "Image",
                    "%s (%.0f %% Conf.)",
                    recognition.getLabel(),
                    recognition.getConfidence() * 100
            );
            telemetry.addData("- Position (Row/Col)", "%.0f / %.0f", row, col);
            telemetry.addData("- Size (Width/Height)", "%.0f / %.0f", width, height);
        }
        telemetry.update();
    }

    /**
     * Get the list of recognitions.
     *
     * @return a list of Recognition objects.
     */
    public List<Recognition> getAllRecognizedObjects() {
        return tfod.getUpdatedRecognitions();
    }

    public String getMostRecognizedObject(){
        List<Recognition> objects=getAllRecognizedObjects();
        List<String> labels = new ArrayList<String>();
        for (Recognition re : objects){
            labels.add(re.getLabel());
        }
        return Util.mostCommon(labels);
    }


    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Activate the tensorflow model.
     */
    public void activate() {
        tfod.activate();
    }

    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext
                .getResources()
                .getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        this.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        this.tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);


    }
}
