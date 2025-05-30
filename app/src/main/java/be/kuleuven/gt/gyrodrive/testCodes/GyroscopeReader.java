package be.kuleuven.gt.gyrodrive.testCodes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

public class GyroscopeReader implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private long lastTimestamp;
    private double currentAngle;

    private static final double DAMPING_FACTOR = 0.9;
    public GyroscopeReader(Context context) {
        // Initialize sensor manager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // Initialize gyroscope sensor
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void startReading() {
        if (gyroscopeSensor != null) {
            // Register sensor listener
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
            lastTimestamp = System.nanoTime();
        }
    }

    public void stopReading() {
        // Unregister sensor listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Calculate time difference since last sensor event
            long currentTime = System.nanoTime();
            long timeDifference = currentTime - lastTimestamp;
            lastTimestamp = currentTime;

            // Convert nanoseconds to seconds
            double deltaTime = (double) timeDifference / 1_000_000_000.0;

            // Integrate angular velocity to obtain angle (simplified method)
            // Angle (in radians) = angular velocity * time
            double angularVelocityZ = event.values[2];
            double deltaAngle = angularVelocityZ * deltaTime;
            currentAngle += Math.toDegrees(deltaAngle);

            // Adjust angle (e.g., keep it within range -180 to 180 degrees)
            currentAngle = adjustAngle(currentAngle);

            // Use currentAngle for steering input in the racing game
            // For example:
            // steeringInput = currentAngle * steeringSensitivity;
            // updateVehicleSteering(steeringInput);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    private double adjustAngle(double angle) {
        // Keep angle within range -180 to 180 degrees
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(double currentAngle) {
        this.currentAngle = currentAngle;
    }
}
