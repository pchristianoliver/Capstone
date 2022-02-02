package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OTPActivity extends AppCompatActivity {

    private EditText otp1,otp2,otp3,otp4,ot5,otp6;
    private Button proceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        proceedBtn = findViewById(R.id.proceed);
        otp1 = findViewById(R.id.edDigit1);
        otp2 = findViewById(R.id.edDigit2);
        otp3 = findViewById(R.id.edDigit3);
        otp4 = findViewById(R.id.edDigit4);
        ot5 = findViewById(R.id.edDigit5);
        otp6 = findViewById(R.id.edDigit6);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActivity.this, RegisterInformationActivity.class );
                startActivity(intent);
            }
        });
    }
}