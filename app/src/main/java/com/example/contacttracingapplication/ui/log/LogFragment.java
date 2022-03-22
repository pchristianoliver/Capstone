package com.example.contacttracingapplication.ui.log;

import static android.R.layout.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.contacttracingapplication.R;
import com.example.contacttracingapplication.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    SharedPreferences storedData;
    String userId;
    ArrayAdapter<String> adapter;
    List<ListModel> activityList = new ArrayList<ListModel>();
    ListView listView;
    ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());


        storedData = getActivity().getApplicationContext().getSharedPreferences("storedData", Context.MODE_PRIVATE);
        userId = storedData.getString("userId", "");
        GetUserLog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    String API_URL = "https://mclogapi20220308122258.azurewebsites.net/api/";
    JSONArray array = new JSONArray();
    protected void GetUserLog() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL + "ActivityLogs/" + userId,
                null,
                response -> {
                    try {
                        array = new JSONArray(response.toString());
                        ExtractLog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Rest_Error", error.toString())
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void ExtractLog() {
        for(int i = 0; i < array.length(); i++) {
            try {
                GetBuildingName(array.getJSONObject(i).getString("buildingId"), array.getJSONObject(i).getString("activityDate"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    List<String> activityTime = new ArrayList<String>();
    List<String> building = new ArrayList<String>();
    ArrayList<ListModel> listModels = new ArrayList<>();
    ListModel list;
    public void GetBuildingName(String buildingId, String activityDate) {
        List<String> listObject = new ArrayList<String>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_URL + "Buildings/" + buildingId,
                null,
                response -> {
                    try {
                        building.add(response.get("buildingName").toString());
                        activityTime.add(activityDate);
                        list = new ListModel(response.get("buildingName").toString(), activityDate);
                        listModels.add(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<ListModel> list = new ArrayList<ListModel>();
                    ListAdapter adapter = new ListAdapter(getContext(), listModels);
                    ListView listView = (ListView) getActivity().findViewById(R.id.list_view);
                    listView.setAdapter(adapter);
                 },
                error -> Log.e("building", error.toString())
        );
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonObjectRequest);
    }
}