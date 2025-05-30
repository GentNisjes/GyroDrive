package be.kuleuven.gt.gyrodrive.testCodes;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import be.kuleuven.gt.gyrodrive.R;
import be.kuleuven.gt.gyrodrive.Struct1.InputManager;

public class Magnetometer extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gameRotationSensor;

    private SensorEventListener gameRotationSensorListener;
    private TextView angleText;
    private ImageView car;

    private ProgressBar gauge;

    float[] orientation = new float[3];

    private float[] rMatrix = new float[9];

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

    private void convertToDegrees(float[] vector){
        for (int i = 0; i < vector.length; i++){
            //from rad to deg
            vector[i] = Math.round(Math.toDegrees(vector[i]));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_magnetometer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //progress bar default initiation
        ProgressBar gauge=(ProgressBar)findViewById(R.id.gauge); // initiate the progress bar
        gauge.setMax(100); // 100 maximum value for the progress value
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            gauge.setMin(-100);
        }
        gauge.setProgress(0); // 50 default progress value for the progress bar


        //Setting up the magnetometerSensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        angleText = findViewById(R.id.angleText);
        car = findViewById(R.id.car);

        if (gameRotationSensor == null){
            Toast.makeText(this, "This device has no magnetometer", Toast.LENGTH_SHORT).show();
            finish();
        }
        gameRotationSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // Extract the rotation around the z-axis
                angleText.setText( String.valueOf((orientation[1]/*-0.080f*/)+0.0f) + "°");
                angleText.setRotation((orientation[1])+0.0f);



                System.out.println("rads " + event.values[0]);
                //currentAngle = Math.toDegrees(event.values[0]*2);
                //call the function
                calculateAngles(orientation, event.values);
                //set the progress bar
                gauge.setProgress( (int) orientation[1]);
                System.out.println("degrees " + orientation[1]);


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gameRotationSensorListener, gameRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gameRotationSensorListener);
    }
}
