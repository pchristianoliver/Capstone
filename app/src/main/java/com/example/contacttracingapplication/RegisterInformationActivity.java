package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contacttracingapplication.Models.RegionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterInformationActivity extends AppCompatActivity {

    private final String[] gender = {"Male", "Female"};

    private Spinner regionSpinner;
    private EditText firstName, lastName, middleName, birthdate;
    private DatePickerDialog.OnDateSetListener setListener;
    private Spinner genderSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        middleName = findViewById(R.id.middlename);
        birthdate = findViewById(R.id.birthdate);
        regionSpinner = findViewById(R.id.region);
        genderSpinner = (Spinner) findViewById(R.id.spinner);


        InputStream is = null;
        try {
            //Open json file
            is = getAssets().open("refregion.json");
            int size = is.available();
            byte[] buffer = new byte[size];

            //Read json file
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");

            //put json file in json object
            JSONObject obj = new JSONObject(jsonString);
            JSONArray recordsArray = obj.getJSONArray("RECORDS");

            List<RegionModel> regions = new ArrayList<>();
            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject jsonObject = recordsArray.getJSONObject(i);
                regions.add(
                        new RegionModel(
                                Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("psgcCode"),
                                jsonObject.getString("regDesc"),
                                jsonObject.getString("regCode")));
            }

            //Adapter for region
            ArrayAdapter<RegionModel> adapter = new ArrayAdapter<RegionModel>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, regions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            regionSpinner.setAdapter(adapter);
            regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        //Adapter for gender
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapterGender);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initializeCalendar();
    }

    private void initializeCalendar() {
        //this is for birthdate
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterInformationActivity.this, android.R.style.Theme_Translucent, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                datePickerDialog.show();

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                birthdate.setText(date);

            }
        };

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        birthdate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

}