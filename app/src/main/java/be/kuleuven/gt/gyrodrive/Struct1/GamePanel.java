package be.kuleuven.gt.gyrodrive.Struct1;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import be.kuleuven.gt.gyrodrive.R;
import be.kuleuven.gt.gyrodrive.Struct1.map.SpriteSheet;
import be.kuleuven.gt.gyrodrive.Struct1.map.Tilemap;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Car car;
    private Point carPoint;

    //instantiations for the Gyro sensor
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private InputManager inputManager;
    private SensorEventListener gyroscopeEventListener;
    private Tilemap tilemap;

    private TextView timer;
    private TimerSetUp timerSetUp;

    private GameActivity gameActivity;

    private int carSkinNumber;

    public GamePanel(Context context, TextView timer, GameActivity gameActivity, int carSkinNumber) {
        super(context);
        this.gameActivity = gameActivity;

        //mainThread setup
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        thread = new MainThread(surfaceHolder, this);

        //Setting up the gyroscopeSensor
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        inputManager = new InputManager(context);
        inputManager.startReading();

        //timer stuff
        this.timer = timer;
        timerSetUp = new TimerSetUp(timer);
        timerSetUp.startTimer();

        //skin stuff
        this.carSkinNumber = carSkinNumber;

        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    update();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        // map setup
        SpriteSheet spriteSheet = new SpriteSheet(context);
        tilemap = new Tilemap(spriteSheet);

        // car setup
        car = new Car(this.getContext(), 0, 0, inputManager, tilemap, carSkinNumber);

        System.out.println("width " + Constants.SCREEN_WIDTH + " height " + Constants.SCREEN_HEIGHT);
        setFocusable(true);

    }

    // implementations of the interface SurfaceHolder.CallBack
    // activating and using the MainThread class logic and object
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    // resume and pausing method
    public void resume(){
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause(){
        sensorManager.unregisterListener(gyroscopeEventListener);
    }

    // updating drawing screen for game logic
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        tilemap.draw(canvas);
        car.draw(canvas);
    }

    public void update() {
        car.update();
        if (car.isEnded()){
            timerSetUp.stopTimer();
            gameActivity.finish();
        }
    }


    public void resetAngle(){
        inputManager.setCurrentAngle(0);
    }
    public boolean getEndState (){
        return car.isEnded();
    }


}
