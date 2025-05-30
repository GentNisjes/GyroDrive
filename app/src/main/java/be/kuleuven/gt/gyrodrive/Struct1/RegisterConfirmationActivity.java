package be.kuleuven.gt.gyrodrive.Struct1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import be.kuleuven.gt.gyrodrive.R;

public class RegisterConfirmationActivity extends AppCompatActivity {

    private TextView txtInfo;
    private Button btnQueue;
    private static final String POST_URL = "https://studev.groept.be/api/a23PT404/register/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        User NewRegisteredUser = (User) getIntent().getParcelableExtra("NewRegisteredUser");
        //progress of the registration
        txtInfo.setText("Response from database: \n"+ "Name: " + NewRegisteredUser.getName() + " Surname: " + NewRegisteredUser.getSurname());


        ProgressDialog progressDialog = new ProgressDialog(RegisterConfirmationActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest submitRequest = new StringRequest(
                Request.Method.POST,
                POST_URL,
                response -> {
                    progressDialog.dismiss();
                    Toast.makeText(
                            RegisterConfirmationActivity.this,
                            "Post request executed",
                            Toast.LENGTH_SHORT).show();
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(
                            RegisterConfirmationActivity.this,
                            "Unable to place the order" + error,
                            Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                /* Map<String, String> with key value pairs as data load */
                return NewRegisteredUser.getPostParameters();
            }
        };
        //call all functions wee need to start
        progressDialog.show();
        requestQueue.add(submitRequest);
        showLoadingAndNavigate();

    }

    private void showLoadingAndNavigate() {
        // Delay navigation to a new activity by 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToLogin();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


    public void onBtnExit_Clicked(View caller) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}


