package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class OTPActivity extends AppCompatActivity {

    private EditText otp1,otp2,otp3,otp4,otp5,otp6;
    private Button proceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        Intent intent=getIntent();
        /*
            [sixDigitCode] ito yung sesend sa user
        */
        ArrayList<Integer> sixDigitCode = intent.getIntegerArrayListExtra("otp");

        // TO CHECK LANG YUNG GENERATED 6 DIGIT CODE
        //  CHECK [Log.e] for OUTPUT

        for(Integer digit: sixDigitCode) {
            Log.e("_", String.valueOf(digit));
        }

        proceedBtn = findViewById(R.id.proceed);
        otp1 = findViewById(R.id.edDigit1);
        otp2 = findViewById(R.id.edDigit2);
        otp3 = findViewById(R.id.edDigit3);
        otp4 = findViewById(R.id.edDigit4);
        otp5 = findViewById(R.id.edDigit5);
        otp6 = findViewById(R.id.edDigit6);

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp1.getText().toString().equals("")) {
                    otp1.requestFocus();
                } else {
                    otp2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp2.getText().toString().equals("")) {
                    otp2.requestFocus();
                } else {
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp3.getText().toString().equals("")) {
                    otp3.requestFocus();
                } else {
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp4.getText().toString().equals("")) {
                    otp4.requestFocus();
                } else {
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp5.getText().toString().equals("")) {
                    otp5.requestFocus();
                } else {
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp1.getText().toString().equals(sixDigitCode.get(0).toString()) && otp2.getText().toString().equals(sixDigitCode.get(1).toString()) && otp3.getText().toString().equals(sixDigitCode.get(2).toString()) && otp4.getText().toString().equals(sixDigitCode.get(3).toString()) && otp5.getText().toString().equals(sixDigitCode.get(4).toString()) && otp6.getText().toString().equals(sixDigitCode.get(5).toString()))
                {
                    Toast.makeText(OTPActivity.this, "ang galing mo pia", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OTPActivity.this, RegisterInformationActivity.class );
                    startActivity(intent);
                }
                else {
                    Toast.makeText(OTPActivity.this, "mali ka kapatid", Toast.LENGTH_SHORT).show();

                }
            }

        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActivity.this, RegisterInformationActivity.class );
                startActivity(intent);
            }
        });
    }
}