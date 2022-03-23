package com.example.contacttracingapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.contacttracingapplication.adapters.SymptomAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SymptomActivity extends AppCompatActivity {
    Button submit_button, cancel_btn;
    CheckBox fever_checkbox, dryCough_checkbox, fatigue_checkbox, aches_checkbox,
            runnyNose_checkbox, soreThroat_checkbox, diarrhea_checkbox,
            headache_checkbox, lostOfSmell_checkbox;
    List<String> userSymptoms = new ArrayList<String>();
    String userId = "", userHealthStatusId = "";
    SharedPreferences storedData;
    RecyclerView symptoms_recyclerView;

    String[] title = {
            "Fever",
            "Dry Cough",
            "Fatigue",
            "Aches and Pains",
            "Runny Nose",
            "Sore Throat",
            "Shortness of Breath",
            "Diarrhea"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symptom);

        symptoms_recyclerView = findViewById(R.id.symptomsRecycleView);

        SymptomAdapter adapter = new SymptomAdapter(title);
        symptoms_recyclerView.setAdapter(adapter);
        symptoms_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        submit_button = findViewById(R.id.submitBtn);
        cancel_btn = findViewById(R.id.cancel_btn);

        fever_checkbox = findViewById(R.id.feverCheckbox);

        storedData = getSharedPreferences("storedData", Context.MODE_PRIVATE);
        userId = storedData.getString("userId", "");
        userHealthStatusId = storedData.getString("userHealthStatus", "");

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.userSymptoms.size() == 0)
                {
                    Toast.makeText(SymptomActivity.this, "Nothing was selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (String symptom : adapter.userSymptoms) {
                    SaveUserSymptoms(symptom);
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SymptomActivity.this, MainActivityWithNavigation.class);
                startActivity(intent);
            }
        });
    }

    public void SymptomCheck(View v) {
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            userSymptoms.add(checkBox.getText().toString());
        }
    }

    private String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/";

    public void SaveUserSymptoms(String symptom) {
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("symptomName", symptom);
            userObject.put("userHealthStatusId", userHealthStatusId);
            userObject.put("userId", userId);
            Toast.makeText(this, "Symptoms were added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SymptomActivity.this, MainActivityWithNavigation.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_URL + "Symptoms/" + userId,
                userObject,
                response -> Log.e("Rest Response", "Success"),
                error -> Log.e("Rest Response", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }
}