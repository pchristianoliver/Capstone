package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                String inputNumber = mobileNumber.getText().toString();
                Intent intent = new Intent(RegisterMobileNumberActivity.this, OTPActivity.class);


                //function for validation (passed all parameters)
                boolean check = validateNumber(inputNumber);
                if (check == true) {
                    startActivity(intent);
                } else
                    mobileNumber.setError("Invalid Mobile Number");


            }
        });


    }

    private boolean validateNumber(String inputNumber)
    {
        if ( !inputNumber.matches("[9][0-9]{9}")) { return false; }
        else{ return true; }

    }
}