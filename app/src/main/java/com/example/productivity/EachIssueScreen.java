package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EachIssueScreen extends AppCompatActivity {
    String name,des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_issue_screen);

        Bundle b = getIntent().getExtras();
        name=b.getString("issuename");
        des=b.getString("issuedes");

        TextView t1=findViewById(R.id.eachIssuePageName);
        TextView t2=findViewById(R.id.eachIssuePageDes);
        t1.setText(name);
        t2.setText(des);
    }
}