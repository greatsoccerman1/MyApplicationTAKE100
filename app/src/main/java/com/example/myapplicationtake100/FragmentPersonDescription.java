package com.example.myapplicationtake100;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class FragmentPersonDescription extends Fragment {

    TextView firstNameText, lastNameText, memberIdText;
    String firstName, lastName, memberId;
    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_person_description, container, false);
        firstNameText = v.findViewById(R.id.firstNameText);
        lastNameText = v.findViewById(R.id.lastNameText);
        memberIdText = v.findViewById(R.id.memberIdText);
        //firstNameText.setText("");
        //lastNameText.setText("");
        memberIdText.setText("");

        return v;
    }

    public void onStart() {
        super.onStart();
        loadData();
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        memberIdText.setText(memberId);
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        firstName = sharedPreferences.getString("firstName", "");
        lastName = sharedPreferences.getString("lastName", "");
        memberId = sharedPreferences.getString("memberId", "");
    }
}