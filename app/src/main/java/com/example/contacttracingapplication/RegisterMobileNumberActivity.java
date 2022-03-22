package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

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
                    intent.putExtra("MobileNumber", mobileNumber.getText().toString());
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