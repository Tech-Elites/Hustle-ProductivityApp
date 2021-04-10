package com.example.productivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teamLeadIssueInfo extends AppCompatActivity {

    TextView iName,iDes,iLink;
    ListView listOfSubmissions;
    ArrayList<String> nameList=new ArrayList<>();
    ArrayList<String> userIdLists=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_lead_issue_info);
        Intent intent =getIntent();
        String link=intent.getStringExtra("link");
        iName=findViewById(R.id.issueNameTeamLeadIssueInfo);
        iDes=findViewById(R.id.issueDesTeamLeadIssueInfo);
        iLink=findViewById(R.id.issueLinkTeamLeadIssueInfo);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,nameList);
        listOfSubmissions=findViewById(R.id.listOfNamesLead);
        listOfSubmissions.setAdapter(arrayAdapter);
        //iLink.setText(link);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("issues");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    int flag=0;
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
                            for(DataSnapshot snapshot3:snapshot2.getChildren())
                            {

                                userIdLists.add(snapshot3.getValue().toString());
                                //Toast.makeText(teamLeadIssueInfo.this, ""+snapshot3.getValue().toString(), Toast.LENGTH_SHORT).show();
                            }
                            //check the size here to find if anyone applied or not
                        }
                        if(snapshot2.getKey().compareTo("completed")==0)
                        {
                            //the issue is completed and show cloed message
                        }
                    }
                    if(flag==1)
                    {
                        iName.setText(name);
                        iDes.setText(des);
                        iLink.setText(link);
                        for(int i=0;i<userIdLists.size();i++)
                        {
                            Toast.makeText(teamLeadIssueInfo.this, "here "+userIdLists.get(i), Toast.LENGTH_SHORT).show();
                        }
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
                                        nameList.add(name);
                                        arrayAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

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
}