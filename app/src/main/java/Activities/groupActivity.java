package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.R;

import java.util.ArrayList;
import java.util.HashMap;

import Models.groupInfo;

public class groupActivity extends AppCompatActivity {

    LinearLayout groupLayout;
    HashMap<String, String> groupHashmap = new HashMap<>();
    ArrayList<groupInfo> groupInfoList = new ArrayList<>();
    private Boolean isOwner = false;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String userIdSaved = "userId";
    public static final String groupIdSaved = "groupId";
    public static final String isOwnerSaved = "isOwner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        groupLayout = findViewById(R.id.groupLayout);
        groupInfoList = (ArrayList<groupInfo>) getIntent().getExtras().getSerializable("groupInfo");
        String userId = getIntent().getExtras().get("userId").toString();

       //groupHashmap = (HashMap<String,String>)getIntent().getExtras().getSerializable("groups");
        if (groupInfoList != null && groupInfoList.size() > 0) {
            for (int i = 0; i < groupInfoList.size(); i++) {
                Button button = new Button(this);
                button.setText(groupInfoList.get(i).getGroupName());
                String ownerId = groupInfoList.get(i).getGroupOwnerId();
                String groupId = groupInfoList.get(i).getGroupCode();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = null;
                        if (userId.equals(ownerId)) {
                            isOwner = true;
                            i = new Intent(groupActivity.this, ManagerPage.class);
                        } else {
                            isOwner = false;
                            i = new Intent(groupActivity.this, EmployeeActivity.class);
                        }
                        i.putExtra("groupSelected", button.getText().toString());
                        saveData(userId, groupId, isOwner);
                        startActivity(i);
                        //i.putExtra("groupSelectedCode", groupHashmap.get(key));
                    }
                });
                groupLayout.addView(button);
            }
        }else{
            Button button = new Button(this);
            button.setText("Have no group thing here");
            groupLayout.addView(button);
        }
    }

    public void saveData(String userId, String groupId, boolean isOwner){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userIdSaved, userId);
        editor.putString(groupIdSaved, groupId);
        editor.putBoolean(isOwnerSaved, isOwner);

        editor.apply();
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }
}