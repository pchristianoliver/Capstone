package com.example.contacttracingapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacttracingapplication.R;


public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.ViewHolder> {

    private String[] localDataSet;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_symptoms, parent, false);
        return new ViewHolder(view);
    }


    public SymptomAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fever.setText(localDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fever;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fever = itemView.findViewById(R.id.feverCheckbox);


        }
    }
}
