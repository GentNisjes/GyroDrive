package be.kuleuven.gt.gyrodrive.Struct1;

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

import be.kuleuven.gt.gyrodrive.testCodes.Magnetometer;

public class InputManager implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gameRotationSensor;
    private long lastTimestamp;
    private double currentAngle;
    public float[] orientation = new float[3];
    private static final double DAMPING_FACTOR = 0.9;

    public InputManager(Context context) {
        // Initialize sensor manager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    public void startReading() {
        if (gameRotationSensor != null) {
            // Register sensor listener
            sensorManager.registerListener(this, gameRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
            lastTimestamp = System.nanoTime();
        }
    }

    public void stopReading() {
        // Unregister sensor listener
        sensorManager.unregisterListener(this);
    }


    //new hope ☺
    //
    public float[] rMatrix = new float[9];
    /**
     * @param result the array of Euler angles in the order: yaw, roll, pitch
     * @param rVector the rotation vector
     */
    public void calculateAngles(float[] result, float[] rVector){
        //caculate rotation matrix from rotation vector first
        SensorManager.getRotationMatrixFromVector(rMatrix, rVector);

        //calculate Euler angles now
        SensorManager.getOrientation(rMatrix, result);

        //The results are in radians, need to convert it to degrees
        convertToDegrees(result);
    }

    public void convertToDegrees(float[] vector){
        for (int i = 0; i < vector.length; i++){
            vector[i] = Math.round(Math.toDegrees(vector[i]));
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            /*// Calculate time difference since last sensor event
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

            // Adjust angle (keep it within range -180 to 180 degrees)
            currentAngle = adjustAngle(currentAngle);*/
            //new try

            //calling functions to make from rotational vector angles of individuals axes in degrees

            calculateAngles(orientation, event.values);
            //index one was tested for Realme10, that should mean axes X, but not sure from datasheets and documentation
            currentAngle = orientation[1] ;

            System.out.println("angleX " + orientation[1]);
            //System.out.println("angleY " + orientation[2]);
            //System.out.println("angleZ " + orientation[0]);
            //2 - Z; 1 -


        }

        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    private double adjustAngle(double angle) {
        // Keep angle within range -180 to 180 degrees
        while (angle > 180) {
            angle -= 180;
        }
        while (angle < -180) {
            angle += 180;
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

