package com.example.contacttracingapplication.ui.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contacttracingapplication.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListModel> {
    public ListAdapter(Context context, ArrayList<ListModel> activityLogList) {
        super(context, R.layout.list_item, activityLogList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListModel model = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView establishment = convertView.findViewById(R.id.establishment_text);
        TextView date = convertView.findViewById(R.id.date_text);

        establishment.setText(model.establishment);
        date.setText(model.date);

        return convertView;
    }

}
