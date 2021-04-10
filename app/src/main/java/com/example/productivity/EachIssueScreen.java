package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EachIssueScreen extends AppCompatActivity {
    String name,des,link;
    String issueID="";
    Boolean isPresent=false;
    FirebaseUser user;
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_issue_screen);
        p=findViewById(R.id.eachIssuePageProgress);
        p.setVisibility(View.VISIBLE);


        Bundle b = getIntent().getExtras();
        name=b.getString("issuename");
        des=b.getString("issuedes");
        link=b.getString("issueLinks");

        TextView t1=findViewById(R.id.eachIssuePageName);
        TextView t2=findViewById(R.id.eachIssuePageDes);
        findViewById(R.id.applyButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.alreadyAppliedText).setVisibility(View.INVISIBLE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        checkPresence();
        t1.setText(name);
        t2.setText(des);

    }

    void checkPresence(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.issues);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String tempLink="";
                    for(DataSnapshot snapshotSub:snapshot1.getChildren()){
                        if(snapshotSub.getKey().compareTo("link")==0)
                            tempLink=snapshotSub.getValue().toString();
                        if(tempLink.compareTo(link)==0){
                            issueID=snapshot1.getKey();

                            break;
                        }
                    }


                }
                checkIfAlreadyApplied();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void checkIfAlreadyApplied(){

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.issues).child(issueID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String tempLink="";
                    if(snapshot1.getKey().compareTo("applied")==0){
                        for(DataSnapshot snapshotSub:snapshot1.getChildren()){
                            if(snapshotSub.getValue().toString().compareTo(user.getUid())==0)
                            {
                                isPresent=true;

                                break;
                            }
                        }
                    }
                    if(isPresent)
                        break;
                }
                displayButtonOrText();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void displayButtonOrText(){

        if(isPresent){
            findViewById(R.id.alreadyAppliedText).setVisibility(View.VISIBLE);
        }
        else{
            findViewById(R.id.applyButton).setVisibility(View.VISIBLE);
        }
        p.setVisibility(View.INVISIBLE);
    }

    public void eachIssueEmployeeApply(View view) {
        FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.issues).child(issueID).child("applied").push().setValue(user.getUid());
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Success!")
                .setMessage("Applied changes submitted.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}