package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeLanding extends AppCompatActivity {

    TextView exclaimation;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,LoginPage.class));
                return  true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_landing);
        Toolbar t=findViewById(R.id.notificationToolBar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_baseline_menu_open_24);
        t.setOverflowIcon(drawable);
        setSupportActionBar(t);

        exclaimation=findViewById(R.id.exclaimationwork);
        exclaimation.setText("");

        BottomNavigationView bottomNavigationView = findViewById(R.id.employeeBottomTabBar);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    public void OnClickNoti(View view)
    {
        Toast.makeText(this, "Hello notifications clicked", Toast.LENGTH_SHORT).show();
        exclaimation.setText("!");
    }


}