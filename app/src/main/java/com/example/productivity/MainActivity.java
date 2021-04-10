package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null)
        {
            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
            FirebaseDatabase.getInstance().getReference().child("Arc Solutions").child("Team Alpha").child("teamlead").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    userinfo uu=task.getResult().getValue(userinfo.class);

                    if(uu.getUserid().compareTo(u.getUid())==0)
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent i = new Intent(MainActivity.this,TeamLeadLanding.class);
                        finish();
                        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());


                    }
                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent i = new Intent(MainActivity.this, EmployeeLanding.class);
                        finish();
                        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                    }

                }
            });
        }
        else
        {
            Intent i = new Intent(MainActivity.this, LoginPage.class);
            finish();
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

        }
    }

}