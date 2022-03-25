package com.example.contacttracingapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacttracingapplication.Models.BarangayModel;
import com.example.contacttracingapplication.Models.CityModel;
import com.example.contacttracingapplication.Models.ProvinceModel;
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

    private Spinner regionSpinner, provinceSpinner, citySpinner, barangaySpinner, genderSpinner;
    private EditText firstNameEt, lastNameEt, middleNameEt, birthdateEt, ageEt;
    private DatePickerDialog.OnDateSetListener setListener;
    private Button proceedBtn;

    private String UserId;
    private String API_URL = "https://mclogapi20220325102303.azurewebsites.net/api///";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_information);

        SharedPreferences storedData = getApplicationContext().getSharedPreferences("storedData", Context.MODE_PRIVATE);
        UserId = storedData.getString("userId", ""); // RETRIEVE VALUES FROM SHAREDPREFERENCES

        firstNameEt = findViewById(R.id.firstname);
        lastNameEt = findViewById(R.id.lastname);
        middleNameEt = findViewById(R.id.middlename);
        birthdateEt = findViewById(R.id.birthdate);
        provinceSpinner = findViewById(R.id.province);
        regionSpinner = findViewById(R.id.region);
        citySpinner = findViewById(R.id.city);
        barangaySpinner = findViewById(R.id.barangay);
        genderSpinner = findViewById(R.id.gender_spinner);
        proceedBtn = findViewById(R.id.proceed);
        ageEt = findViewById(R.id.age);

        initializeGetGender();
        initializeCalendar();
        initializeGetAddress();



        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEt.getText().toString();
                String lastName = lastNameEt.getText().toString();
                String middleName = middleNameEt.getText().toString();
                String birthdate = birthdateEt.getText().toString();

                boolean checkInfo = validateInfo(firstName,lastName,middleName,birthdate);

                if (checkInfo == true){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    RegisterUserInformation();
                }
                else
                    Toast.makeText(getApplicationContext(),"Data not valid",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RegisterUserInformation(){
        Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        Spinner provinceSpinner = findViewById(R.id.province);
        Spinner regionSpinner = findViewById(R.id.region);
        Spinner citySpinner = findViewById(R.id.city);
        Spinner barangaySpinner = findViewById(R.id.barangay);

        JSONObject userObject = new JSONObject();
        try {
            userObject.put("firstName", firstNameEt.getText().toString());
            userObject.put("lastName", lastNameEt.getText().toString());
            userObject.put("middleName", middleNameEt.getText().toString());
            userObject.put("birthDate", "1234");
            userObject.put("gender", genderSpinner.getSelectedItem().toString());
            userObject.put("province", provinceSpinner.getSelectedItem().toString());
            userObject.put("region", regionSpinner.getSelectedItem().toString());
            userObject.put("city", citySpinner.getSelectedItem().toString());
            userObject.put("baranggay", barangaySpinner.getSelectedItem().toString());
            userObject.put("phoneNumber", getIntent().getStringExtra("MobileNumber"));
            userObject.put("age", 12);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(RegisterInformationActivity.this, RegisterPasswordActivity.class);
        intent.putExtra("UserInformation", userObject.toString());
        startActivity(intent);
    }

    private Boolean validateInfo(String firstName, String lastName, String middleName, String birthdate){
        if(firstName.length()==0){
            firstNameEt.requestFocus();
            firstNameEt.setError("Field cannot be empty.");
            return false;
        }
        else if(!firstName.matches("[a-zA-Z]+([\\s][a-zA-Z]+)*")){
            firstNameEt.requestFocus();
            firstNameEt.setError("Enter only alphabetical character.");
            return false;
        }
        else if(lastName.length()==0){
            lastNameEt.requestFocus();
            lastNameEt.setError("Field cannot be empty.");
            return false;
        }
        else if(!lastName.matches("[a-zA-Z]+([\\s][a-zA-Z]+)*")){
            lastNameEt.requestFocus();
            lastNameEt.setError("Enter only alphabetical character.");
            return false;
        }
        else if(!middleName.matches("^[a-zA-Z]+$|^$")) {
            middleNameEt.requestFocus();
            middleNameEt.setError("Enter only alphabetical character.");
            return false;
        }
        else if (!birthdate.matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$")){
            birthdateEt.requestFocus();
            birthdateEt.setError("Field cannot be empty.");
            return false;
        }
        else
            return true;
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

        birthdateEt.setOnClickListener(new View.OnClickListener() {
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
                birthdateEt.setText(date);

            }
        };

        birthdateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        birthdateEt.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void initializeGetAddress() {
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
            final String[] regionCode = {"-1"};
            ArrayAdapter<RegionModel> adapterRegion = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, regions);
            adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            regionSpinner.setAdapter(adapterRegion);
            regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        RegionModel region = (RegionModel) parent.getItemAtPosition(position);
                        regionCode[0] = region.getRegCode();
                        //Reading province
                        //Open and read json file
                        String jsonStringProvince = null;

                        jsonStringProvince = openAndReadJsonFile("refprovince.json");


                        //put json file in json object
                        JSONObject objProvince = new JSONObject(jsonStringProvince);
                        JSONArray recordsArrayProvince = objProvince.getJSONArray("RECORDS");

                        List<ProvinceModel> provinces = new ArrayList<>();
                        for (int i = 0; i < recordsArrayProvince.length(); i++) {
                            JSONObject jsonObject = recordsArrayProvince.getJSONObject(i);
                            if (jsonObject.getString("regCode").equals(regionCode[0])) {
                                provinces.add(
                                        new ProvinceModel(
                                                Integer.parseInt(jsonObject.getString("id")),
                                                jsonObject.getString("psgcCode"),
                                                jsonObject.getString("provDesc"),
                                                jsonObject.getString("regCode"),
                                                jsonObject.getString("provCode")));
                            }
                        }

                        //Adapter for province
                        final String[] provCode = {"-1"};
                        ArrayAdapter<ProvinceModel> adapterProvince = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, provinces);
                        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        provinceSpinner.setAdapter(adapterProvince);
                        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    ProvinceModel province = (ProvinceModel) parent.getItemAtPosition(position);
                                    provCode[0] = province.getProvCode();

                                    //Reading city
                                    //read and open json file
                                    String jsonStringCity = null;
                                    jsonStringCity = openAndReadJsonFile("refcitymun.json");


                                    //put json file in json object
                                    JSONObject obj = new JSONObject(jsonStringCity);
                                    JSONArray recordsArray = obj.getJSONArray("RECORDS");

                                    List<CityModel> cities = new ArrayList<>();
                                    for (int i = 0; i < recordsArray.length(); i++) {
                                        JSONObject jsonObject = recordsArray.getJSONObject(i);
                                        if (jsonObject.getString("provCode").equals(provCode[0])) {
                                            cities.add(
                                                    new CityModel(
                                                            Integer.parseInt(jsonObject.getString("id")),
                                                            jsonObject.getString("psgcCode"),
                                                            jsonObject.getString("citymunDesc"),
                                                            jsonObject.getString("regDesc"),
                                                            jsonObject.getString("provCode"),
                                                            jsonObject.getString("citymunCode")));
                                        }
                                    }


                                    //Adapter for city
                                    final String[] cityCode = {"-1"};
                                    ArrayAdapter adapterCity = new ArrayAdapter<>(RegisterInformationActivity.this, android.R.layout.simple_spinner_item, cities);
                                    adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    citySpinner.setAdapter(adapterCity);
                                    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            CityModel city = (CityModel) parent.getItemAtPosition(position);
                                            cityCode[0] = city.getCitymunCode();

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
                                                    if (jsonObject.getString("citymunCode").equals(cityCode[0])) {


                                                        barangays.add(
                                                                new BarangayModel(
                                                                        Integer.parseInt(jsonObject.getString("id")),
                                                                        jsonObject.getString("brgyCode"),
                                                                        jsonObject.getString("brgyDesc"),
                                                                        jsonObject.getString("regCode"),
                                                                        jsonObject.getString("provCode"),
                                                                        jsonObject.getString("citymunCode")));

                                                    }
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


                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}