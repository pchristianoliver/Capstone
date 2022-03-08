package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMobileNumberActivity extends AppCompatActivity {

    private Button proceedBtn;
    private EditText mobileNumber;
    SharedPreferences storedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilenumber);

        proceedBtn = findViewById(R.id.proceed);
        mobileNumber = findViewById(R.id.mobileNumber);
        storedData = getSharedPreferences("storedData", Context.MODE_PRIVATE);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNumber = mobileNumber.getText().toString();



                //function for validation (passed all parameters)
                boolean check = validateNumber(inputNumber);
                SharedPreferences.Editor editor = storedData.edit();

                if (check) {
                    Intent intent = new Intent(RegisterMobileNumberActivity.this, OTPActivity.class);
                    intent.putIntegerArrayListExtra("otp", (ArrayList<Integer>) generateSixDigitCode());
                    intent.putExtra("MobileNumber", mobileNumber.getText().toString());
                    startActivity(intent);
                } else
                    mobileNumber.setError("Invalid Mobile Number");


            }
        });


    }

    private ArrayList<Integer> generateSixDigitCode() {
        ArrayList<Integer> sixDigitCode = new ArrayList<Integer>();

        for(int i = 0; i <= 5; i++) {
            sixDigitCode.add(((int)(Math.random() * 9) + 1));
        }
        return sixDigitCode;
    }

    private boolean validateNumber(String inputNumber)
    {
        if ( !inputNumber.matches("[9][0-9]{9}")) { return false; }
        else{ return true; }

    }
}