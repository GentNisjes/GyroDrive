package be.kuleuven.gt.gyrodrive.Struct1;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSetUp {

    private Timer timer;
    private TimerTask timerTask;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;
    private TextView timerTextView;
    private Handler handler;

    public TimerSetUp(TextView timerTextView) {
        this.timerTextView = timerTextView;
        handler = new Handler(Looper.getMainLooper());
    }

    public void startTimer() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    updateUI();
                }
            };

            timer = new Timer();
            timer.scheduleAtFixedRate(timerTask, 0, 1000); // Update UI every second
        }
    }

    public void stopTimer() {
        if (isRunning) {
            isRunning = false;
            timer.cancel();
        }
    }

    public void resetTimer() {
        stopTimer();
        elapsedTime = 0;
        updateUI();
    }

    private void updateUI() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (timerTextView != null) {
                    timerTextView.setText(formatTime(elapsedTime));
                }
            }
        });
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
