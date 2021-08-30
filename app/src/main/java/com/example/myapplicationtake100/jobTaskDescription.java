package com.example.myapplicationtake100;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class jobTaskDescription extends AppCompatActivity {

    private TextView descriptionText, nameText, groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_description);

/*
        descriptionText = findViewById(R.id.DescriptionText);
        nameText  = findViewById(R.id.NameText);
        groupId = findViewById(R.id.GroupId);

        LiveData<String> name = null;
        name.observe(this, nameChange -> {
            descriptionText.setText("Description : " + getIntent().getExtras().getString("jobTaskDescription"));;
            nameText.setText("Name : " + getIntent().getExtras().getString("personName"));
            groupId.setText("Group ID: " + getIntent().getExtras().getString("mongoId"));
        });*/

    }
}