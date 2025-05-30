package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;
import java.util.stream.IntStream;

import be.kuleuven.gt.gyrodrive.R;

public class EndGame extends AppCompatActivity {

    private int carSkinNumber;
    private String username;
    private static final String POST_URL = "https://studev.groept.be/api/a23PT404/savePlay/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Retrieve the timer value from the intent
        String timerValue = getIntent().getStringExtra("TIMER_VALUE");
        carSkinNumber = getIntent().getIntExtra("CAR_SKIN", 0);
        username = getIntent().getStringExtra("USERNAME");

        // Use the timer value as needed, e.g., display it in a TextView
        TextView timerTextView = findViewById(R.id.timeScore);
        timerTextView.setText("Time taken: " + timerValue);
        int timingInSeconds = convertToSeconds(timerValue);

        //Creating object for database timings
        Player player = new Player(
                username,
                timingInSeconds
        );

        // Entering time in database
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest submitRequest = new StringRequest(
                Request.Method.POST,
                POST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(
                                EndGame.this,
                                "Post request executed",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                EndGame.this,
                                "Unable to place the order" + error,
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) { //NOTE THIS PART: here we are passing the POST parameters to the webservice
            @Override
            protected Map<String, String> getParams() {
                /* Map<String, String> with key value pairs as data load*/
                return player.getPostParameters();
            }
        };

        requestQueue.add(submitRequest);
    }

    /*public int convertToSeconds(String timingHourMinSec){

        String[] arrOfStr = timingHourMinSec.split(":", 0);
        int hours = Integer.parseInt(arrOfStr[0]);
        int minutes = Integer.parseInt(arrOfStr[1]);
        int seconds = Integer.parseInt(arrOfStr[2]);

        return hours * 3600 + minutes * 60 + seconds;
    }*/

    public int convertToSeconds(String timingHourMinSec) {
        int[] timeUnits = {3600, 60, 1};

        return IntStream.range(0, timingHourMinSec.split(":").length)
                .map(i -> Integer.parseInt(timingHourMinSec.split(":")[i]) * timeUnits[i])
                .sum();
    }


    public void onPlayAgainBtn_Clicked(View caller) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("CAR_SKIN", carSkinNumber);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    public void onHomeBtn_clicked(View caller){
        Intent intent = new Intent(this, MenuTest.class);
        intent.putExtra("CAR_SKIN", carSkinNumber);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

}