package com.example.contacttracingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Logs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        SharedPreferences storedData = getApplicationContext().getSharedPreferences("storedData", Context.MODE_PRIVATE);
        String UserId = storedData.getString("userId", "");
        GetUserLog(UserId);
        // gg
    }

    private void GetUserLog(String userId) {
        String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/ActivityLogs/" + userId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> HandleResponseObject(response),
                error -> Log.e("Rest_Error", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }

    //  CHECK THE VALUE OF [jsonArray] IT RETURNS JSONARRAY OF A LOGGED IN USER
    public JSONArray HandleResponseObject(JSONArray jsonArray){
        return jsonArray;
    }
}