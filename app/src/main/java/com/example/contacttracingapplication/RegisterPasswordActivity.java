package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPasswordActivity extends AppCompatActivity {

    private EditText password, confirmPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.registerBtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    Intent intent = new Intent(RegisterPasswordActivity.this, MainActivity.class);
                    Register();
                    startActivity(intent);
                    Toast.makeText(RegisterPasswordActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                }else{
                    confirmPassword.setError("Password does not match!");
                }
            }
        });
    }

    private String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/";
    public void Register() {
        JSONObject userObject = null;
        try {
            userObject = new JSONObject(getIntent().getStringExtra("UserInformation"));
            userObject.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("Register: ", userObject.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_URL + "Users",
                userObject,
                response -> Log.e("Rest Response", response.toString()),
                error -> Log.e("Rest Response", error.toString())
        );

        requestQueue.add(jsonObjectRequest);
    }

}