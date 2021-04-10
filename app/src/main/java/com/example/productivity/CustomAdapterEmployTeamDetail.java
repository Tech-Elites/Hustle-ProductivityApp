package com.example.productivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class CustomAdapterEmployTeamDetail extends ArrayAdapter<UserDetailClass> {

    // invoke the suitable constructor of the ArrayAdapter class
    public CustomAdapterEmployTeamDetail(@NonNull Context context, ArrayList<UserDetailClass> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_employee_team_details, parent, false);
        }

        UserDetailClass currentUser = getItem(position);

        TextView tname = currentItemView.findViewById(R.id.customEmployeeTeamDetName);
        TextView temail = currentItemView.findViewById(R.id.customEmployeeTeamDetEmail);
        TextView tpoints = currentItemView.findViewById(R.id.customEmployeeTeamDetPoints);

        tname.setText(currentUser.getName());
        temail.setText(currentUser.getEmail());
        tpoints.setText(currentUser.getPoints());

        return currentItemView;
    }
}