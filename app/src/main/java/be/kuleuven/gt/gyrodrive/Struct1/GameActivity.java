package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import be.kuleuven.gt.gyrodrive.R;

public class GameActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private InputManager inputManager;

    private SensorEventListener gyroscopeEventListener;

    private FrameLayout container;
    private GamePanel gamePanel;
    private LinearLayout controls;
    private TextView timer;
    private TimerSetUp timerSetUp;
    private static final String TAG1 = "ScreenSize";
    private static final String TAG2 = "TabSize";
    private static final String CARSKIN = "Car Skin";
    private static final String CAR_NR = "CAR_NR";

    private int carSkinNumber;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        container = findViewById(R.id.container);
        controls = findViewById(R.id.controls);
        timer = findViewById(R.id.timer);

        /*timerSetUp = new TimerSetUp(timer);
        timerSetUp.startTimer();*/

        // Get the display metrics of the device
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // Get screen dimensions in pixels
        int screenWidthPixels = displayMetrics.widthPixels;
        Constants.SCREEN_WIDTH = screenWidthPixels;
        int screenHeightPixels = displayMetrics.heightPixels;
        Constants.SCREEN_HEIGHT = screenHeightPixels;
        carSkinNumber = getIntent().getIntExtra("CAR_SKIN", 0);
        username = getIntent().getStringExtra("USERNAME");
        Log.d(CAR_NR, "onCreate: " + carSkinNumber);

        Log.d(CARSKIN, "Car skin resource: " + carSkinNumber);

        Log.d(TAG1, "Screen width: " + screenWidthPixels + " pixels, height: " + screenHeightPixels + " pixels");



        gamePanel = new GamePanel(this, timer, this, carSkinNumber);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        container.addView(gamePanel, layoutParams);


    }

    @Override
    protected void onResume() {
        super.onResume();
        gamePanel.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.pause();
    }

    public void onCalibrateBtn_clicked (View Caller){
        gamePanel.resetAngle();
    }

    @Override
    protected void onStop() {
        super.onStop(); // Call the superclass method to perform default onStop operations
        String timerValue = timer.getText().toString(); // Get the text value from the timer TextView
        Intent intent = new Intent(GameActivity.this, EndGame.class);
        intent.putExtra("TIMER_VALUE", timerValue); // Pass the timer value to the EndGame activity
        Log.d(CAR_NR, "onStop: " + carSkinNumber);
        intent.putExtra("CAR_SKIN", carSkinNumber);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

}


