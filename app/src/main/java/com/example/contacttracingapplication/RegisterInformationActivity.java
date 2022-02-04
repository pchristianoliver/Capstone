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

import com.example.contacttracingapplication.Models.BarangayModel;
import com.example.contacttracingapplication.Models.CityModel;
import com.example.contacttracingapplication.Models.ProvinceModel;
import com.example.contacttracingapplication.Models.RegionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterInformationActivity extends AppCompatActivity {

    private final String[] gender = {"Male", "Female"};

    private Spinner regionSpinner, provinceSpinner, citySpinner, barangaySpinner, genderSpinner;
    private EditText firstName, lastName, middleName, birthdate;
    private DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        middleName = findViewById(R.id.middlename);
        birthdate = findViewById(R.id.birthdate);
        provinceSpinner = findViewById(R.id.province);
        regionSpinner = findViewById(R.id.region);
        citySpinner = findViewById(R.id.city);
        barangaySpinner = findViewById(R.id.barangay);
        genderSpinner = findViewById(R.id.spinner);

        try {
            //Reading region
            //Open and read json file
            String jsonStringRegion = openAndReadJsonFile("refregion.json");

            //put json file in json object
            JSONObject objRegion = new JSONObject(jsonStringRegion);
            JSONArray recordsArrayRegion = objRegion.getJSONArray("RECORDS");

            List<RegionModel> regions = new ArrayList<>();
            for (int i = 0; i < recordsArrayRegion.length(); i++) {
                JSONObject jsonObject = recordsArrayRegion.getJSONObject(i);
                regions.add(
                        new RegionModel(
                                Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("psgcCode"),
                                jsonObject.getString("regDesc"),
                                jsonObject.getString("regCode")));
            }

            //Adapter for region
            ArrayAdapter<RegionModel> adapterRegion = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, regions);
            adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            regionSpinner.setAdapter(adapterRegion);
            regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Reading province
            //Open and read json file
            String jsonStringProvince = openAndReadJsonFile("refprovince.json");

            //put json file in json object
            JSONObject objProvince = new JSONObject(jsonStringProvince);
            JSONArray recordsArrayProvince = objProvince.getJSONArray("RECORDS");

            List<ProvinceModel> provinces = new ArrayList<>();
            for (int i = 0; i < recordsArrayProvince.length(); i++) {
                JSONObject jsonObject = recordsArrayProvince.getJSONObject(i);
                provinces.add(
                        new ProvinceModel(
                                Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("psgcCode"),
                                jsonObject.getString("provDesc"),
                                jsonObject.getString("regCode"),
                                jsonObject.getString("provCode")));
            }

            //Adapter for province
            ArrayAdapter<ProvinceModel> adapterProvince = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, provinces);
            adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provinceSpinner.setAdapter(adapterProvince);
            provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Reading city
            //read and open json file
            String jsonStringCity = openAndReadJsonFile("refcitymun.json");

            //put json file in json object
            JSONObject obj = new JSONObject(jsonStringCity);
            JSONArray recordsArray = obj.getJSONArray("RECORDS");

            List<CityModel> cities = new ArrayList<>();
            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject jsonObject = recordsArray.getJSONObject(i);
                cities.add(
                        new CityModel(
                                Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("psgcCode"),
                                jsonObject.getString("citymunDesc"),
                                jsonObject.getString("regDesc"),
                                jsonObject.getString("provCode"),
                                jsonObject.getString("citymunCode")));
            }

            //Adapter for city
            ArrayAdapter<CityModel> adapterCity = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, cities);
            adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(adapterCity);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        //Reading barangay
        try {
            //Open json file
            //read and open json file
            String jsonStringBrgy = openAndReadJsonFile("refbrgy.json");

            //put json file in json object
            JSONObject obj = new JSONObject(jsonStringBrgy);
            JSONArray recordsArray = obj.getJSONArray("RECORDS");

            List<BarangayModel> barangays = new ArrayList<>();
            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject jsonObject = recordsArray.getJSONObject(i);
                barangays.add(
                        new BarangayModel(
                                Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("brgyCode"),
                                jsonObject.getString("brgyDesc"),
                                jsonObject.getString("regCode"),
                                jsonObject.getString("provCode"),
                                jsonObject.getString("citymunCode")));

            }

            //Adapter for barangay
            ArrayAdapter<BarangayModel> adapterBrgy = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, barangays);
            adapterBrgy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            barangaySpinner.setAdapter(adapterBrgy);
            barangaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        initializeGetGender();
        initializeCalendar();
    }

    private String openAndReadJsonFile(String fileName) throws IOException {
        InputStream is = getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String jsonString = new String(buffer, "UTF-8");
        return jsonString;
    }

    private void initializeGetGender() {
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