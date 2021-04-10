package com.example.productivity;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class issueDisplayAdaptor extends ArrayAdapter<issueClass>
{

    issueDisplayAdaptor(Context c, ArrayList<issueClass> n)
    {
        super(c,0,n);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.customer_issue, parent, false);
        }
        issueClass s=getItem(position);
        TextView name=view.findViewById(R.id.issueName);
        TextView des=view.findViewById(R.id.issueDes);
        des.setMovementMethod(new ScrollingMovementMethod());
        des.setText(s.getIssueDes());
        name.setText(s.getIssueName());
        return view;
    }
}