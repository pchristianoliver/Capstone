package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    startActivity(intent);
                    Toast.makeText(RegisterPasswordActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                }else{
                    confirmPassword.setError("Password does not match!");
                }
            }
        });
    }
}