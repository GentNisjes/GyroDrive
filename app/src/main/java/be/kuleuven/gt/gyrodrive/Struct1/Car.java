package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.widget.ImageView;

import be.kuleuven.gt.gyrodrive.R;
import be.kuleuven.gt.gyrodrive.Struct1.map.Tile;
import be.kuleuven.gt.gyrodrive.Struct1.map.Tilemap;

public class Car implements GameObject{

    private Bitmap carBitmap;
    private float x, y;
    private float previousX, previousY;
    private InputManager inputManager;
    private Double currentAngle;
    private Double measuredAngle;
    private Tilemap tilemap;

    private TimerSetUp timerSetUp;
    private boolean isEnded;

    private String ended;

    private int carSkinNumber;

    public Car(Context context, int xpos, int ypos, InputManager inputManager, Tilemap tilemap, int carSkinNumber) {
        this.carSkinNumber = carSkinNumber;
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), Constants.CAR_SKINS_IG[this.carSkinNumber]);
        this.carBitmap = Bitmap.createScaledBitmap(originalBitmap, Constants.CAR_WIDTH, Constants.CAR_HEIGHT, false);
        this.x = xpos;
        this.y = ypos;
        this.inputManager = inputManager;
        this.tilemap = tilemap;

        currentAngle = 0.00;
        isEnded = false;
    }

    @Override
    public void draw(Canvas canvas) {
        //Logic for drawing the Car under an angle given by currentAngle
        Matrix matrix = new Matrix();
        matrix.postRotate((float) currentAngle.doubleValue() + 90.0f);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(carBitmap, Constants.CAR_WIDTH, Constants.CAR_HEIGHT, true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        canvas.drawBitmap(rotatedBitmap, x, y, null);
    }

    //Collision detection
    public boolean hasCollided (float x, float y){
        return tilemap.getTyleType(x, y) == Tile.TileType.WALL_TILE;
    }

    @Override
    public void update() {
        // Update x and y position of the bitmap based on the gyroscope inputs
        measuredAngle = inputManager.getCurrentAngle();

        double speed;

        // avoiding the car movement to be to sensitive
        if (!(10 > measuredAngle && -10 < measuredAngle)) {
            currentAngle -= 0.08 * measuredAngle;
        }

        // adjusting the car speed based on screen width
        // scaling of the gameActivity is not on point yet
        // so adjusting the speed to compromise for the larger screen
        if (Constants.SCREEN_WIDTH > 2000){
            speed = 3.0;
        } else {
            speed = 1.0;
        }

        // Calculate the new position based on the current angle
        float newX = x + (float) (speed * Math.cos(Math.toRadians(currentAngle)));
        float newY = y + (float) (speed * Math.sin(Math.toRadians(currentAngle)));

        // Check if the new position collides with an impassable tile (e.g., a wall)
        if (!isWall(newX, newY)) {
            if (isEnd(newX, newY)){
                Log.d(ended, "in ending area ");
                isEnded = true;
            }
            // If no collision with impassable tiles (e.g., wall), update the position
            x = newX;
            y = newY;

        } else {
            /*Log.d("WallDetection", "wall touched");*/
            // If collision with impassable tile (e.g., wall), prevent moving into the wall
            // For simplicity, revert the position to its previous value
            x = previousX;
            y = previousY;
        }

        // Constrain the new position within the screen boundaries
        // before passing on to the next position (x and y are the next position)
        float minX = 1;
        float minY = 1;
        float maxX = Constants.SCREEN_WIDTH - Constants.CAR_WIDTH;
        float maxY = Constants.SCREEN_HEIGHT - Constants.CAR_HEIGHT;
        x = Math.max(minX, Math.min(x, maxX));
        y = Math.max(minY, Math.min(y, maxY));

        // Store the previous position for collision detection
        previousX = x;
        previousY = y;

    }

    //classes to determine with the x and y position what tile the car is on
    private boolean isWall(float x, float y) {
        int row = tilemap.getColumnOrRow(y);
        int column = tilemap.getColumnOrRow(x);
        return !tilemap.isTileImpassable(row, column);
    }

    private boolean isEnd(float x, float y) {
        int row = tilemap.getColumnOrRow(y);
        int column = tilemap.getColumnOrRow(x);
        return tilemap.isTileEndArea(row, column);
    }

    public void setEnded (){
        isEnded = false;
    }

    public boolean isEnded(){
        return isEnded;
    }

}




