package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EachIssueScreen extends AppCompatActivity {
    String name,des,link;
    Boolean isPresent=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_issue_screen);



        Bundle b = getIntent().getExtras();
        name=b.getString("issuename");
        des=b.getString("issuedes");
        link=b.getString("issueLinks");

        TextView t1=findViewById(R.id.eachIssuePageName);
        TextView t2=findViewById(R.id.eachIssuePageDes);
        findViewById(R.id.applyButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.alreadyAppliedText).setVisibility(View.INVISIBLE);
        checkPresence();
        t1.setText(name);
        t2.setText(des);
    }

    void checkPresence(){
//        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.members);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot snapshot1:snapshot.getChildren())
//                {
//                    String name="",uid="",email="";
//                    for(DataSnapshot snapshotSub:snapshot1.getChildren()){
//                        if(snapshotSub.getKey().compareTo("name")==0)
//                            name=snapshotSub.getValue().toString();
//                        if(snapshotSub.getKey().compareTo("userid")==0)
//                            uid=snapshotSub.getValue().toString();
//                        if(snapshotSub.getKey().compareTo("email")==0)
//                            email=snapshotSub.getValue().toString();
//                    }
//                    UserDetailClass temp = new UserDetailClass(name, email, uid);
//                    System.out.println("AEJK"+name+email+uid);
//                    if(i%2==0){
//                        usersLeft.add(temp);
//                        i++;
//                    }
//                    else{
//                        usersRight.add(temp);
//                        i++;
//                    }
//                }
//                readPoints();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void eachIssueEmployeeApply(View view) {

    }
}