package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RedeemCreditsPage extends AppCompatActivity {

    FirebaseUser user;
    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_credits_page);

        user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.points).child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current=Integer.parseInt(snapshot.getValue().toString());
                Toast.makeText(RedeemCreditsPage.this, current+" ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onLeavesClick(View view) {
    }

    public void onCouponsClick(View view) {
    }

    public void onSubClick(View view) {
    }

    public void onAscClick(View view) {
    }
}