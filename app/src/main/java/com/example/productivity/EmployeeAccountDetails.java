package com.example.productivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeAccountDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeAccountDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployeeAccountDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeAccountDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeAccountDetails newInstance(String param1, String param2) {
        EmployeeAccountDetails fragment = new EmployeeAccountDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    CustomAdapterIssuePendingAdmin customAdapterIssuePendingAdmin;
    ArrayList<String> issueNames=new ArrayList<String>();
    ListView lPending;
    TextView noOfCredits,nameOfTheEmployee,noOfPendingApplications;
    String name,credits,pending,userId;
    int count=0;
    Button redeemButton;
    ProgressBar p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_account_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        p=getView().findViewById(R.id.employeeAccountDetailsProgress);
        p.setVisibility(View.VISIBLE);
        redeemButton=getView().findViewById(R.id.redeemButton);
        redeemButton.setVisibility(View.INVISIBLE);
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRedeem();
            }
        });
        noOfCredits=getView().findViewById(R.id.noOfCreditsAccountDetails);
        nameOfTheEmployee=getView().findViewById(R.id.nameOfTheEmployeeAccountDetails);
        noOfPendingApplications=getView().findViewById(R.id.noofPendingAppliAccountDetails);
        lPending=getView().findViewById(R.id.listViewForPendingApplications);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            userId=user.getUid();
            fillTheBasicInfo();
        }




    }

    public void onClickRedeem(){
        Intent i = new Intent(getActivity(),RedeemCreditsPage.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    void fillTheBasicInfo()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("members");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    userinfo u= snapshot1.getValue(userinfo.class);
                    if(u.getUserid().compareTo(userId)==0)
                    {
                        name=u.getName();
                        fillTheCredits();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void fillTheCredits()
    {
        FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.points).child(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        credits=task.getResult().getValue().toString();
                        fillTheNoPending();
                    }
                });
    }
    void fillTheNoPending()
    {
        issueNames.clear();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.issues);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    int flag=0;
                    String temp="";
                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                    {

                        if(snapshot2.getKey().compareTo("applied")==0)
                        {
                            for(DataSnapshot snapshot3:snapshot2.getChildren())
                            {
                                if(userId.compareTo(snapshot3.getValue().toString())==0)
                                {
                                    count++;
                                    flag=1;
                                    break;
                                }
                            }
                        }
                        if(snapshot2.getKey().compareTo("name")==0)
                        {
                            temp=snapshot2.getValue().toString();
                        }

                    }
                    if(flag==1)
                    {
                        issueNames.add(temp);
                    }
                }
                nameOfTheEmployee.setText(name);
                noOfCredits.setText("Credits- "+credits);
                noOfPendingApplications.setText("Pending- "+Integer.toString(count));
                try {
                    customAdapterIssuePendingAdmin=new CustomAdapterIssuePendingAdmin(getActivity(),issueNames);
                    lPending.setAdapter(customAdapterIssuePendingAdmin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                p.setVisibility(View.INVISIBLE);
                redeemButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}