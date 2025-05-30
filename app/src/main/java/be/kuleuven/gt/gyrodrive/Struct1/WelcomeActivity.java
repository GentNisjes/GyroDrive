package be.kuleuven.gt.gyrodrive.Struct1;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import be.kuleuven.gt.gyrodrive.R;


public class WelcomeActivity extends AppCompatActivity {
    private Button btnExit;
    private Button btnLogin;
    private Button btnRegister;
    private EditText username;
    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
//        btnExit = (Button) findViewById(R.id.btnExit);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
    }

    public void onBtnLogin_Clicked(View Caller) {
        // create new user from data given from form in GUI


        EditText username = (EditText) findViewById(R.id.txtUsername);
        EditText password = (EditText)  findViewById(R.id.txtPassword);


        //new instance of user
        User loggedUser = new User(
                Integer.parseInt("0"),
                username.getText().toString(),
                "",
                "",
                "",
                "",
                "",
                password.getText().toString()
        );
        Intent intent = new Intent(this, ShowUserActivity.class);
        intent.putExtra("LoggedUser", loggedUser);
        startActivity(intent);
    }

    public void onBtnRegister_Clicked(View caller) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onBtnGame_Clicked(View caller) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onBtnLeaderBoard_Clicked(View caller) {
        Intent intent = new Intent(this, ShowLeaderBoardActivity.class);
        startActivity(intent);

    }
}