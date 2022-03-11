package com.example.contacttracingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etPassword, etUsername;
    private TextView txtRegister;
    SharedPreferences storedData;
    BottomNavigationView bottomNavigationView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.mobileNumber);
        etPassword = findViewById(R.id.editTextTextPassword);
        txtRegister = findViewById(R.id.txtRegisterHere);
        storedData = getSharedPreferences("storedData", Context.MODE_PRIVATE);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterMobileNumberActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AuthenticateUser();
                Intent intent = new Intent(MainActivity.this, MainActivityWithNavigation.class);
                startActivity(intent);
            }
        });
    }

    public void AuthenticateUser() {
        JSONObject credentialObject = new JSONObject();
        String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/Authentication";
        try {
            credentialObject.put("mobileNumber", etUsername.getText().toString());
            credentialObject.put("password", etPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_URL,
                credentialObject,
                response -> {
                    try {
                        RedirectToMainPage(response.get("message").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Rest_Response", error.toString())
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonObjectRequest);
    }

    private void RedirectToMainPage(@NonNull String message) {
        if (message.length() > 5) {
            Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
            // ===================================================================
            /*
                SAVE DATA WHICH ALLOWS ACTIVITIES TO ACCESS AND REUSE VALUES
            */
            SharedPreferences.Editor editor = storedData.edit();
            editor.putString("userId", message);
            editor.commit();
            // ===================================================================

            startActivity(intent);
        }
    }
}


