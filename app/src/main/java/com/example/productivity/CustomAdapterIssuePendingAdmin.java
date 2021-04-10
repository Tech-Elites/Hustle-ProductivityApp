package com.example.productivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterIssuePendingAdmin extends ArrayAdapter<String> {

    public CustomAdapterIssuePendingAdmin(@NonNull Context context, ArrayList<String> arrayList) {

        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_issue_pending_admin, parent, false);
        }

        try{
            String oneText = getItem(position);
            TextView tname = currentItemView.findViewById(R.id.customIssuePendingName);
            tname.setText(oneText);
        }catch (Exception e){
            System.out.println(e);
        }



        return currentItemView;
    }
}