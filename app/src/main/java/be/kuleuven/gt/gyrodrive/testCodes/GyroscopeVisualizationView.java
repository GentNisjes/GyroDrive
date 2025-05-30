package be.kuleuven.gt.gyrodrive.testCodes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GyroscopeVisualizationView extends View {
    private Paint paint;
    private float angle; // Store the orientation angle (e.g., around the z-axis)

    public GyroscopeVisualizationView(Context context) {
        super(context);
        init();
    }

    public GyroscopeVisualizationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GyroscopeVisualizationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        // Calculate the length of the line based on the angle value (scaled to fit within the view)
        float maxLength = Math.min(centerX, centerY) * 0.8f; // Use 80% of the minimum dimension
        float length = angle * maxLength / 90f; // Scale the angle to fit within the maximum length

        // Draw the horizontal line
        canvas.drawLine(centerX - length, centerY, centerX + length, centerY, paint);
    }

    public void setAngle(float angle) {
        this.angle = angle;
        invalidate(); // Redraw the view with the updated angle
    }
}
