package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMobileNumberActivity extends AppCompatActivity {

    private Button proceedBtn;
    private EditText mobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilenumber);

        proceedBtn = findViewById(R.id.proceed);
        mobileNumber = findViewById(R.id.mobileNumber);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMobileNumberActivity.this, OTPActivity.class);
                if (TextUtils.isEmpty(mobileNumber.getText().toString())) {
                    mobileNumber.setError("Invalid Mobile Number");
                } else
                    startActivity(intent);
            }
        });


    }
}