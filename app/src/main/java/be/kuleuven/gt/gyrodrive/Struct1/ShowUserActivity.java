package be.kuleuven.gt.gyrodrive.Struct1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.kuleuven.gt.gyrodrive.R;

public class ShowUserActivity extends AppCompatActivity {
    private TextView txtLoggedUser;
    private TextView txtTopHeaderUsrShow;
    private EditText editTextTextMultiLine;
    private ImageView imageView2;

    private static final String QUEUE_URL = "https://studev.groept.be/api/a23PT404/selectuser";

    private String NewLoggeddUserUsername; //username
    private String NewLoggeddUserPassword; //password

    public User LoggedUser; //current player, public, so it is accessible from other activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        txtLoggedUser = (TextView) findViewById(R.id.txtLoggedUser);
        txtTopHeaderUsrShow = (TextView) findViewById(R.id.txtTopHeaderUsrShow);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        editTextTextMultiLine = (EditText) findViewById(R.id.editTextTextMultiLine);
        User NewLoggeddUser = (User) getIntent().getParcelableExtra("LoggedUser");
        NewLoggeddUserUsername = NewLoggeddUser.getUsername();
        NewLoggeddUserPassword = NewLoggeddUser.getPassword();

        txtLoggedUser.append("Waiting for server to respond \n credentials to validate: \n" +  "Name: " + NewLoggeddUser.getName() + " Surname: " + NewLoggeddUser.getSurname());
        requestLoggedUser();

    }

    private void processJSONResponse(JSONArray response) {
        //for (int i = 0; i < response.length(); i++) {
            try {
                User JSONquery = new User(response.getJSONObject(0));
                UsersInDatabase.add(JSONquery);
                User user = UsersInDatabase.get(0);

                //correct password
                if (Objects.equals(user.getPassword(), NewLoggeddUserPassword)){
                    LoggedUser = user;
                    txtTopHeaderUsrShow.setText("User Login Successful");
                    txtLoggedUser.setText("Response from database: \n"+ "Name: " + LoggedUser.getName() + " Surname: " + LoggedUser.getSurname());
                    //System.out.println(user.getUsername());
                    //System.out.println(user.getPassword());
                    imageView2.setImageResource(R.drawable.success_icon); //icon, so it is more graphically friendly
                    showLoadingAndNavigate();
                }
                //incorrect password
                else {
                    LoggedUser = null;
                    txtTopHeaderUsrShow.setText("User Login Unsuccessful");
                    txtLoggedUser.setText("Username or password incorrect");
                    //System.out.println(user.getUsername());
                    //System.out.println(user.getPassword());
                    imageView2.setImageResource(R.drawable.unsuccessful_icon); //error icon
                    showLoadingAndNavigate();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}
        //if no user found do this:
        //System.out.println(LoggedUsersInDatabase.toString().length());
        if (UsersInDatabase.toString().length()<3){
            txtTopHeaderUsrShow.setText("User Login Unsuccessful");
            txtLoggedUser.setText("Username or password incorrectd");
            imageView2.setImageResource(R.drawable.unsuccessful_icon);
            showLoadingAndNavigate();

        }

    }

    //should be just one, so list is useless
    private List<User> UsersInDatabase = new ArrayList<>();
    private void requestLoggedUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                //QUEUE_URL + "/" + NewLoggeddUserUsername + "/" + NewLoggeddUserPassword
                QUEUE_URL + "/" + NewLoggeddUserUsername, //to specify json request, mysql is prepared for that
                null,
                response -> {
                      //call function to process the response
                    processJSONResponse(response);


                },
                error -> {
                    Toast.makeText(
                            ShowUserActivity.this,
                            "Unable to communicate with the server",
                            Toast.LENGTH_LONG).show();
                txtTopHeaderUsrShow.setText("User Login Unsuccessful");
                imageView2.setImageResource(R.drawable.unsuccessful_icon);
                showLoadingAndNavigate();

                });
        requestQueue.add(queueRequest);
    }

    private void showLoadingAndNavigate() {
        // Delay navigation to a new activity by 3 seconds
        if (txtTopHeaderUsrShow.getText().toString().equals("User Login Successful")){
            Log.d("Successfully logged in", "should be logged in, txtbox = " + txtTopHeaderUsrShow.getText().toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToMenu();
                }
            }, 3000); // 3000 milliseconds = 3 seconds
        }
        else {
            Log.d("Successfully logged in", "should NOT logged in, txtbox = " + txtTopHeaderUsrShow.getText().toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateToLogIn();
                }
            }, 3000); // 3000 milliseconds = 3 seconds
        }
    }

    private void navigateToMenu() {
        Intent intent = new Intent(this, MenuTest.class);
        intent.putExtra("username", NewLoggeddUserUsername);
        startActivity(intent);
    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void onBtnBack_Clicked(View caller) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

}