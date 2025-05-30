package be.kuleuven.gt.gyrodrive.Struct1;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.gt.gyrodrive.R;

public class ShowLeaderBoardActivity extends AppCompatActivity {

    private TextView tempText;

    private RecyclerView LeaderBoardView;
    private static final String QUEUE_URL = "https://studev.groept.be/api/a23PT404/selectAllPlays";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_leaderboard);
        LeaderBoardView = findViewById( R.id.leaderBoardView);
        LeaderBoardAdapter adapter = new LeaderBoardAdapter( records );
        LeaderBoardView.setAdapter( adapter );
        LeaderBoardView.setLayoutManager( new LinearLayoutManager( this ));
        requestLeaderBoard();
    }

    private void processJSONResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                Play JSONquery = new Play(response.getJSONObject(i));
                records.add(JSONquery);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //lets fill our records list with records
    private List<Play> records = new ArrayList<>();
    private void requestLeaderBoard() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                QUEUE_URL,
                null,
                response -> {

                    processJSONResponse(response);
                    LeaderBoardView.getAdapter().notifyDataSetChanged();
                },
                error -> Toast.makeText(
                        ShowLeaderBoardActivity.this,
                        "Unable to communicate with the server",
                        Toast.LENGTH_LONG).show());
        requestQueue.add(queueRequest);
    }

}