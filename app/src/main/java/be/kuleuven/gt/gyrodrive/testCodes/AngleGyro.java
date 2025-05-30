package be.kuleuven.gt.gyrodrive.testCodes;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import be.kuleuven.gt.gyrodrive.R;
import be.kuleuven.gt.gyrodrive.Struct1.ScreenUtils;

public class AngleGyro extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private TextView angleText;
    private ImageView car;

    private double initialOrientation;

    private GyroscopeReader gyroscopeReader;

    private ScreenUtils su;

    private SensorEventListener gyroscopeEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_angle_gyro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting screen size
        ScreenUtils.logScreenSize(this);

        //Setting up the gyroscopeSensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        gyroscopeReader = new GyroscopeReader(this);

        angleText = findViewById(R.id.angleText);
        car = findViewById(R.id.car);


        if (gyroscopeSensor == null){
            Toast.makeText(this, "this device has no Gyroscope", Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                angleText.setText( String.valueOf(gyroscopeReader.getCurrentAngle()));
                car.setRotation((float) - gyroscopeReader.getCurrentAngle());
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void onCalibrateBtn_clicked (View Caller){
        gyroscopeReader.startReading();
        /*initialOrientation = gyroscopeReader.getCurrentAngle();*/
        /*gyroscopeReader.stopReading();*/
        gyroscopeReader.setCurrentAngle(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        gyroscopeReader.startReading();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);

        gyroscopeReader.stopReading();
    }
}