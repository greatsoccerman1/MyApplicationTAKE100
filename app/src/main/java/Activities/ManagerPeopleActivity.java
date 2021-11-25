package Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplicationtake100.FragmentPersonDescription;
import com.example.myapplicationtake100.R;

import java.text.DateFormat;
import java.util.Calendar;

import Models.getGroupMembersResponse;

public class ManagerPeopleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private LinearLayout peopleLayout;
    public static final String SHARED_PREFS = "sharedPrefs";
    private String userId, groupId;
    private Button addGroupMemberButton;
    private Button deleteGroupMember, refreshButton;
    private TextView dateTextFieldToUpdate;
    private getGroupMembersResponse resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_people);
        //peopleLayout = findViewById(R.id.groupMemberLayout);
        addGroupMemberButton = findViewById(R.id.addGroupMemberButton);
        deleteGroupMember = findViewById(R.id.deleteGroupMember);
        refreshButton = findViewById(R.id.refreshBtn);
        loadData();
        setupAddMemberButton(addGroupMemberButton);
        setupDeleteMemberButton(deleteGroupMember);
        setupRefreshButton(refreshButton);
     }

    private void setupRefreshButton(Button refreshButton) {
        refreshButton.setOnClickListener(v -> {
          FragmentManager fm = getSupportFragmentManager();
            FragmentPersonDescription fragment = (FragmentPersonDescription) fm.findFragmentById(R.id.groupMembersFragment);
            fragment.populateMoneyMadeTV();
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");
    }

    public void switchToOtherTab(Fragment frag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.groupMembersFragment, frag);
        fragmentTransaction.commit();
    }

    public getGroupMembersResponse getGroupMembers(){
        if (resp != null) {
            return resp;
        }
        else {
            return null;
        }
    }

    public void setupDeleteMemberButton(Button deleteGroupMember){
        deleteGroupMember.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }

            });
    }

    public void setupAddMemberButton(Button addGroupMember){
        addGroupMember.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(ManagerPeopleActivity.this, addPersonActivity.class);
                startActivity(i);
            }
        });
    }

    public void setDateTextFieldToUpdate(TextView dateTVToSet){
        dateTextFieldToUpdate = dateTVToSet;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        dateTextFieldToUpdate.setText(currentDate);
    }
}