package com.example.contacttracingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainMenuActivity extends AppCompatActivity {
    ImageView qrCodeView;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Button button;
    TextView temperature;
    private TextView userCompleteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        qrCodeView = findViewById(R.id.qrCodeView);
        userCompleteName = findViewById(R.id.UserCompleteName);
        temperature = findViewById(R.id.temperature);
        button = findViewById(R.id.button);
        SharedPreferences storedData = getApplicationContext().getSharedPreferences("storedData", Context.MODE_PRIVATE);
        String UserId = storedData.getString("userId", ""); // RETRIEVE VALUES FROM SHAREDPREFERENCES

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateQRCode(UserId);
            }
        });


        GetUsersName(UserId);
    }

    public void GenerateQRCode(String userId) {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int x = point.x;
        int y = point.y;
        int width = point.x;
        int height = point.y;

        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        JSONObject userObject = new JSONObject();
        try {
            userObject.put("userId", userId);
            userObject.put("temp", temperature.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        qrgEncoder = new QRGEncoder(userObject.toString(), null, QRGContents.Type.TEXT, dimen);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }

    /* RETRIEVE USER'S NAME */
    private void GetUsersName(String id) {
        String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/Users/" + id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    try {
                        userCompleteName.setText(response.get("firstName").toString() +" "+ response.get("lastName").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Rest_Response", error.toString())
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonObjectRequest);
    }
}