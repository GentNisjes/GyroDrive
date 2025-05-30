package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import be.kuleuven.gt.gyrodrive.R;

public class GameMenu extends AppCompatActivity {

    private ImageView selectedCar;
    private Button switchCar;
    private int selectedCarNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        selectedCar = findViewById(R.id.selectedCar);
        switchCar = findViewById(R.id.switchCarLeft);
        selectedCarNumber = 0;
        selectedCar.setImageResource(Constants.CAR_SKINS[selectedCarNumber]);
    }

    public void onBtn_SwitchCarLeft (View Caller){
        if (selectedCarNumber > 0){
            selectedCarNumber -= 1;
            selectedCar.setImageResource(Constants.CAR_SKINS[selectedCarNumber]);
        }
    }

    public void onBtn_SwitchCarRight (View Caller){
        if (selectedCarNumber < Constants.CAR_SKINS.length - 1){
            selectedCarNumber += 1;
            selectedCar.setImageResource(Constants.CAR_SKINS[selectedCarNumber]);
        }
    }

    public void onBtn_PlayClicked (View Caller){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("CAR_SKIN", selectedCarNumber); // Pass the timer value to the EndGame activity
        startActivity(intent);
    }

}