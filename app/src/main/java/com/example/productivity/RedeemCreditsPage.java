package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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



    void showRedBox(int i){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.points).child(user.getUid()).setValue(current-i);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are your sure you want to redeem it with "+i+"C?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    void showLowBox(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Insufficient credits!")
                .setMessage("Please check your credit balance and try again.")
                .setPositiveButton("Yes",null)
                .show();
    }

    public void onLeavesClick(View view) {
        if(current>=1000){
            showRedBox(1000);
        }
        else{
            showLowBox();
        }
    }

    public void onCouponsClick(View view) {
        if(current>=400){
            showRedBox(400);
        }
        else{
            showLowBox();
        }
    }

    public void onSubClick(View view) {
        if(current>=700){
            showRedBox(700);
        }
        else{
            showLowBox();
        }
    }

    public void onAscClick(View view) {
        if(current>=1800){
            showRedBox(1800);
        }
        else{
            showLowBox();
        }
    }
}