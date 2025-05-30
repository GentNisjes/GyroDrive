package be.kuleuven.gt.gyrodrive.testCodes;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import be.kuleuven.gt.gyrodrive.R;


public class GyroscopeTesting extends AppCompatActivity {

    private TextView tiltX;
    private TextView tiltY;
    private TextView tiltZ;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    private SensorEventListener gyroscopeEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gyroscope_testing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Setting up the gyroscopeSensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        tiltX = findViewById(R.id.tiltX);
        tiltY = findViewById(R.id.tiltY);
        tiltZ = findViewById(R.id.tiltZ);


        if (gyroscopeSensor == null){
            Toast.makeText(this, "this device has no Gyroscope", Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                String tiltXValue = String.valueOf(event.values[0]);
                String tiltYValue = String.valueOf(event.values[1]);
                String tiltZValue = String.valueOf(event.values[2]);

                /*tiltX.setText(tiltXValue);
                tiltY.setText(tiltYValue);
                tiltZ.setText(tiltZValue);*/

                // Update the TextView with the tilt value, while avoiding ANR or application not responding errors (executing on main thread)
                runOnUiThread(() -> {
                        tiltX.setText(tiltXValue);
                        tiltY.setText(tiltYValue);
                        tiltZ.setText(tiltZValue);
                });

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }
}