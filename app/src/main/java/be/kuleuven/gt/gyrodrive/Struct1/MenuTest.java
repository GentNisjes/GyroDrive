package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

import be.kuleuven.gt.gyrodrive.R;

public class MenuTest extends AppCompatActivity {

    private ImageButton redBtn, greenBtn, blueBtn;
    private Button playBtn, mapSwitchBtn, leaderboardBtn;
    private TextView chooseColorText, username;
    private ImageView carView;
    private int selectedCarNumber;

    private String usernamePlayer;
    private User userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        redBtn = findViewById(R.id.redBtn);
        blueBtn = findViewById(R.id.blueBtn);
        greenBtn = findViewById(R.id.greenBtn);
        carView = findViewById(R.id.carView);
        username = findViewById(R.id.username);

        selectedCarNumber = getIntent().getIntExtra("CAR_SKIN", 0);
        carView.setImageResource(Constants.CAR_SKINS[selectedCarNumber]);


        if (!Objects.equals(getIntent().getStringExtra("username"), null)){
            usernamePlayer = getIntent().getStringExtra("username");
        }
        else {
            usernamePlayer = getIntent().getStringExtra("USERNAME");
        }
        username.setText(String.format("Welcome %s", usernamePlayer));

    }

    /*public void onBtn_greenBtn (View Caller){
            carView.setImageResource(Constants.CAR_SKINS[selectedCarNumber]);
        }*/

    public void onBtn_redBtn (View Caller){
        carView.setImageResource(Constants.CAR_SKINS[0]);
        selectedCarNumber = 0;
    }

    public void onBtn_blueBtn (View Caller){
        carView.setImageResource(Constants.CAR_SKINS[1]);
        selectedCarNumber = 1;
    }

    public void onBtn_greenBtn (View Caller){
        carView.setImageResource(Constants.CAR_SKINS[2]);
        selectedCarNumber = 2;
    }


    public void onBtn_PlayClicked (View Caller){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("CAR_SKIN", selectedCarNumber);
        intent.putExtra("USERNAME", usernamePlayer);
        startActivity(intent);
    }

    public void onExitBtn_Clicked (View Caller){
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void onBtnLeaderBoard_Clicked(View caller) {
        Intent intent = new Intent(this, ShowLeaderBoardActivity.class);
        startActivity(intent);

    }

    public void onBtn_SwitchMapClicked (View Caller) {
        //temporary till the feature is not done
        Toast.makeText(
                MenuTest.this,
                "Work in progress",
                Toast.LENGTH_SHORT).show();
    }
}