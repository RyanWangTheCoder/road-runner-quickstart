package org.firstinspires.ftc.teamcode.vision;

import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
@TeleOp(name="LimeLightTest", group="Teleop")
public class LimeLightTest  extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();

        Limelight3A limelight = new Limelight3A();
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);
        String TAG = "LimteLight";

        waitForStart();
        /*
         * Starts polling for data.
         */
        limelight.start();
        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("Botpose", botpose.toString());

                    Log.d(TAG, result.toString());

                    dashboardTelemetry.addData("tx", result.getTx());
                    dashboardTelemetry.addData("ty", result.getTy());
                    dashboardTelemetry.addData("Botpose", botpose.toString());
                }
            }
            else {
                Log.d(TAG, "NULL result");
                Log.d(TAG, "" + limelight.isConnected() + " time " + limelight.getTimeSinceLastUpdate());
                limelight.reloadPipeline();
                Log.d(TAG, "" + limelight.getStatus());
                Log.d(TAG, "" + limelight.isRunning());

                telemetry.addData("NULL", "result");

                dashboardTelemetry.addData("NULL", "result");
            }
            telemetry.update();
            dashboardTelemetry.update();
        }
    }
}
