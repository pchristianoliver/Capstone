package com.example.contacttracingapplication.ui.home;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.contacttracingapplication.MainActivityWithNavigation;
import com.example.contacttracingapplication.R;
import com.example.contacttracingapplication.SymptomActivity;
import com.example.contacttracingapplication.databinding.FragmentHomeBinding;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView username, temperature;
    Button generateQR_button, healthCheck_button;
    QRGEncoder qrgEncoder;
    ImageView qrView;
    Bitmap bitmap;
    String userId;
    SharedPreferences storedData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        username = root.findViewById(R.id.user_name);
        temperature = root.findViewById(R.id.user_temperature);
        generateQR_button = root.findViewById(R.id.generateqr_btn);
        healthCheck_button = root.findViewById(R.id.healthcheck_btn);
        qrView = root.findViewById(R.id.qrView);


        return root;


    }


    @Override
    public void onStart() {
        super.onStart();
        storedData = getActivity().getApplicationContext().getSharedPreferences("storedData", Context.MODE_PRIVATE);
        userId = storedData.getString("userId", "");

        GetUserFullName();

        generateQR_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateQRCode();
            }
        });

        healthCheck_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(view.getContext(),
                        SymptomActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void GenerateQRCode() {
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;

        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        JSONObject userObject = new JSONObject();
        try {
            userObject.put("userId", userId);
            userObject.put("temperature", temperature.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        qrView.setVisibility(View.VISIBLE);
        qrgEncoder = new QRGEncoder(userObject.toString(), null, QRGContents.Type.TEXT, dimen);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrView.setImageBitmap(bitmap);
            SharedPreferences.Editor editor = storedData.edit();
            editor.putString("temperature", temperature.getText().toString());
            editor.commit();
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }

    public void GetUserFullName() {
        String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/Users/" + userId;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    try {
                        username.setText(response.get("firstName").toString() + " " + response.get("lastName").toString());
                        Log.e("GetUserFullName: ", response.get("firstName").toString());
                        SharedPreferences.Editor editor = storedData.edit();
                        editor.putString("name", username.getText().toString());
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Rest_Response", error.toString())
        );
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonObjectRequest);
    }


}