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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeFeedPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFeedPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployeeFeedPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeFeedPage.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeFeedPage newInstance(String param1, String param2) {
        EmployeeFeedPage fragment = new EmployeeFeedPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    issueDisplayAdaptor issueDisplayAdaptor;
    ArrayList<issueClass> issueClassArrayList=new ArrayList<>();
    ListView issueList;
    ProgressBar adminFeedProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        issueList=getView().findViewById(R.id.issueListEmployee);
        adminFeedProgressbar=getView().findViewById(R.id.progressBarEmployeeFeed);
        adminFeedProgressbar.setVisibility(View.VISIBLE);
        fillTheListView();

//        issueList.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getActivity(), "CLICKED", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(getActivity(), EachIssueScreen.class);
////                        intent.putExtra("issuename",issueClassArrayList.get(position).getIssueName());
////                        intent.putExtra("issuedes",issueClassArrayList.get(position).getIssueDes());
////                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
//                    }
//                }
//        );

    }

    void fillTheListView()
    {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child("issues");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getActivity(), "Here", Toast.LENGTH_SHORT).show();

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    issueClass i=new issueClass();
                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                    {
                        String tempDes,tempName;


                        if(snapshot2.getKey().compareTo("des")==0)
                        {
                            tempDes=snapshot2.getValue().toString();

                            i.setIssueDes(tempDes);
                        }
                        if(snapshot2.getKey().compareTo("name")==0)
                        {
                            tempName=snapshot2.getValue().toString();

                            i.setIssueName(tempName);
                        }

                    }
                    issueClassArrayList.add(i);
                }
                adminFeedProgressbar.setVisibility(View.INVISIBLE);
                issueDisplayAdaptor=new issueDisplayAdaptor(getActivity(),issueClassArrayList);
                issueList.setAdapter(issueDisplayAdaptor);
                Toast.makeText(getActivity(), "READY3", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_feed_page, container, false);
    }
}