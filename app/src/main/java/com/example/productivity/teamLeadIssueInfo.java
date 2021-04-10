package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teamLeadIssueInfo extends AppCompatActivity {

    TextView iName,iDes,iLink,headingApplication;
    ListView listOfSubmissions;
    ArrayList<String> nameList=new ArrayList<>();
    ArrayList<String> userIdLists=new ArrayList<>();
    ArrayList<String> finaluserIdLists=new ArrayList<>();
    CustomAdapterIssuePendingAdmin arrayAdapter;
    String link;
    String key="";
    TextView noApplications;
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_lead_issue_info);
        p=findViewById(R.id.eachIssueAppliedProgress);
        p.setVisibility(View.VISIBLE);

        Intent intent =getIntent();
        noApplications=findViewById(R.id.t10);

        link=intent.getStringExtra("link");
        iName=findViewById(R.id.issueNameTeamLeadIssueInfo);
        iDes=findViewById(R.id.issueDesTeamLeadIssueInfo);
        headingApplication=findViewById(R.id.textView9);
        iLink=findViewById(R.id.issueLinkTeamLeadIssueInfo);
        arrayAdapter=new CustomAdapterIssuePendingAdmin(this, nameList);
        listOfSubmissions=findViewById(R.id.listOfNamesLead);
        listOfSubmissions.setAdapter(arrayAdapter);
        listOfSubmissions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickElementsInList(position);
            }
        });
        //iLink.setText(link);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("issues");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    int flag=0,flag1=0;
                    String name="",des="";
                    userIdLists.clear();
                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                    {
                        if(snapshot2.getKey().compareTo("link")==0)
                        {
                            if(snapshot2.getValue().toString().compareTo(link)==0)
                            {
                                flag=1;
                            }
                        }
                        if(snapshot2.getKey().compareTo("name")==0)
                        {
                            name=snapshot2.getValue().toString();
                        }
                        if(snapshot2.getKey().compareTo("des")==0)
                        {
                            des=snapshot2.getValue().toString();
                        }
                        if(snapshot2.getKey().compareTo("applied")==0)
                        {
                            flag1=1;
                            for(DataSnapshot snapshot3:snapshot2.getChildren())
                            {

                                userIdLists.add(snapshot3.getValue().toString());

                                //Toast.makeText(teamLeadIssueInfo.this, ""+snapshot3.getValue().toString(), Toast.LENGTH_SHORT).show();
                            }


                            //check the size here to find if anyone applied or not
                        }

                    }

                    if(flag==1)
                    {
                        iName.setText(name);
                        iDes.setText(des);
                        iLink.setText(link);

                        if(flag1==0)
                        {
                            noApplications.setVisibility(View.VISIBLE);
                        }
                        break;


                    }
                }

                fillList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void OnClickElementsInList(int i)
    {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are u sure?")
                .setMessage("You are about the accept the submission by "+nameList.get(i)+"\nProceed???")
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int tempPoint;
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("points");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot snapshot1:snapshot.getChildren())
                                {
                                    if(snapshot1.getKey().compareTo(finaluserIdLists.get(i))==0)
                                    {
                                        int point=Integer.parseInt(snapshot1.getValue().toString());
                                        //Toast.makeText(teamLeadIssueInfo.this, ""+point, Toast.LENGTH_SHORT).show();
                                        //temporary increment-
                                        point+=100;
                                        FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("points").child(finaluserIdLists.get(i)).setValue(point)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    getKey();
                                                }
                                            }
                                        });


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
    void getKey()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("issues");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int flag3=0;
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    int flag = 0, flag1 = 0;
                    String name = "", des = "";
                    userIdLists.clear();
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        if (snapshot2.getKey().compareTo("link") == 0) {
                            if (snapshot2.getValue().toString().compareTo(link) == 0) {
                                flag = 1;
                                key=snapshot1.getKey();
                            }
                        }
                        if(flag==1)
                        {
                            break;
                        }
                    }
                    if(flag==1)
                    {
                        break;
                    }
                }
                DeleteNode(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void DeleteNode(String keyy)
    {

        FirebaseDatabase.getInstance().getReference()
                .child(tagclass.companyName).child(tagclass.teamName).child("issues").child(keyy).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(teamLeadIssueInfo.this,TeamLeadLanding.class);
                            finish();
                            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(teamLeadIssueInfo.this).toBundle());
                            //enter your code what you want excute                     after remove value in firebase.
                        } else {
                            //enter msg or enter your code which you want to show in case of value is not remove properly or removed failed.


                        }
                    }
                });
    }



    void fillList()
    {
        finaluserIdLists.clear();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("members");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {

                    int flag=0;
                    String name="";
                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                    {
                        if(snapshot2.getKey().compareTo("userid")==0)
                        {
                            for(int i=0;i<userIdLists.size();i++)
                            {

                                if(snapshot2.getValue().toString().compareTo(userIdLists.get(i))==0)
                                {
                                    finaluserIdLists.add(userIdLists.get(i));
                                    flag=1;
                                }
                            }
                        }
                        if(snapshot2.getKey().compareTo("name")==0)
                        {
                            name=snapshot2.getValue().toString();
                        }
                    }
                    if(flag==1)
                    {
                        headingApplication.setText("Applications so far-");
                        nameList.add(name);
                        arrayAdapter=new CustomAdapterIssuePendingAdmin(teamLeadIssueInfo.this, nameList);
                        listOfSubmissions.setAdapter(arrayAdapter);
                    }
                }
                p.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}