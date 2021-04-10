package com.example.productivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link employeeTeamDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class employeeTeamDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public employeeTeamDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment employeeTeamDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static employeeTeamDetails newInstance(String param1, String param2) {
        employeeTeamDetails fragment = new employeeTeamDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_employee_team_details, container, false);
    }

    ArrayList<UserDetailClass> usersLeft;
    ArrayList<UserDetailClass> usersRight;
    ListView listViewLeft, listViewRight;
    CustomAdapterEmployTeamDetail adapterLeft, adapterRight;
    int i;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usersLeft = new ArrayList<>();
        usersRight = new ArrayList<>();
        getDetailsFromDB();
        i=0;
    }

    void getDetailsFromDB(){
        try {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.members);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        String name="",uid="",email="";
                        for(DataSnapshot snapshotSub:snapshot1.getChildren()){
                            if(snapshotSub.getKey().compareTo("name")==0)
                                name=snapshotSub.getValue().toString();
                            if(snapshotSub.getKey().compareTo("userid")==0)
                                uid=snapshotSub.getValue().toString();
                            if(snapshotSub.getKey().compareTo("email")==0)
                                email=snapshotSub.getValue().toString();
                        }
                        UserDetailClass temp = new UserDetailClass(name, email, uid);
                        System.out.println("AEJK"+name+email+uid);
                        if(i%2==0){
                            usersLeft.add(temp);
                            i++;
                        }
                        else{
                            usersRight.add(temp);
                            i++;
                        }
                    }
                    readPoints();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    void readPoints(){
        try {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.points);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {

                        for(int i=0; i<usersRight.size(); i++){
                            if(usersRight.get(i).uid.compareTo(snapshot1.getKey().toString())==0){
                                usersRight.get(i).points=Integer.parseInt(snapshot1.getValue().toString());
                                System.out.println("AEJK"+usersRight.get(i).uid+ usersRight.get(i).points);
                                usersRight.get(i).setPoints(Integer.parseInt(snapshot1.getValue().toString()));
                                break;
                            }
                        }
                        for(int i=0; i<usersLeft.size(); i++){
                            if(usersLeft.get(i).uid.compareTo(snapshot1.getKey().toString())==0){
                                usersLeft.get(i).points=Integer.parseInt(snapshot1.getValue().toString());
                                System.out.println("AEJK"+usersLeft.get(i).uid+ usersLeft.get(i).points);
                                usersLeft.get(i).setPoints(Integer.parseInt(snapshot1.getValue().toString()));
                                break;
                            }
                        }

                    }

                    buildListView();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    void buildListView(){
        listViewLeft = getView().findViewById(R.id.EmployeeDetailsPageListLeft);
        listViewRight = getView().findViewById(R.id.EmployeeDetailsPageListRight);
        adapterLeft = new CustomAdapterEmployTeamDetail(getActivity(), usersLeft);
        adapterRight = new CustomAdapterEmployTeamDetail(getActivity(), usersRight);

        listViewLeft.setAdapter(adapterLeft);
        listViewRight.setAdapter(adapterRight);
    }
}