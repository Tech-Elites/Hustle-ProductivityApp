package com.example.productivity;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewIssueAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewIssueAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewIssueAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewIssueAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static NewIssueAdmin newInstance(String param1, String param2) {
        NewIssueAdmin fragment = new NewIssueAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    EditText issueName,issueDescription,issueLink;
    Button raiseIssue;
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
        issueName=getView().findViewById(R.id.inputIssueName);
        issueDescription=getView().findViewById(R.id.inputIssueDescription);
        issueLink=getView().findViewById(R.id.inputIssueLink);
        raiseIssue=getView().findViewById(R.id.raiseIssueButton);
        raiseIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickRaiseIssue();
            }
        });
    }

    public void OnClickRaiseIssue()
    {
        String issuenameString,issueDesString,issueLinkString;
        issuenameString=issueName.getText().toString();
        issueDesString=issueDescription.getText().toString();
        issueLinkString=issueLink.getText().toString();
        if(TextUtils.isEmpty(issueDesString)||TextUtils.isEmpty(issueLinkString)||TextUtils.isEmpty(issuenameString))
        {
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Oopss!!")
                    .setMessage("Fill in all the details to proceed")
                    .setPositiveButton("Ok",null)
                    .show();
        }
        else
        {
            newIssue n=new newIssue();
            n.setDes(issueDesString);
            n.setLink(issueLinkString);
            n.setName(issuenameString);
            FirebaseDatabase.getInstance().getReference().child(tagclass.companyName).child(tagclass.teamName).child(tagclass.issues).push().setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getActivity(), "Issue Added!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_issue_admin, container, false);
    }
}