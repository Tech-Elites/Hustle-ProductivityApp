package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class EmployeeLanding extends AppCompatActivity {

    TextView exclaimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_landing);
        Toolbar t=findViewById(R.id.notificationToolBar);
        setSupportActionBar(t);
        exclaimation=findViewById(R.id.exclaimationwork);
        exclaimation.setText("");
    }
    public void OnClickNoti(View view)
    {
        Toast.makeText(this, "Hello notifications clicked", Toast.LENGTH_SHORT).show();
        exclaimation.setText("!");
    }


}