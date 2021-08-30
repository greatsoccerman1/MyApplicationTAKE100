package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Models.JobTasks;
import Models.Jobs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobListPage extends AppCompatActivity {
    private LinearLayout jobScrollView;
    private HashMap<String, String> needsDescriptionMap;
    private String userId, groupId;
    private boolean isOwner = false;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        loadData();
        populateJobLayout();

    }

    public void populateJobLayout(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.180:8080/demo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        if (isOwner){
            userId = "00";
        }
        Call<Jobs> call = jsonPlaceHolderApi.getJobs(groupId, userId);
        call.enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                if (!response.isSuccessful()) {
                } else {
                    if (response != null) {
                        Log.d("Logs", "onResponse: " + response.body());
                       populateTable(response.body());
                    }else{
                       // errorText.setText("No results found");

                    }
                }
            }
            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {
                //errorText.setText("No results found");
            }
        });
    };



    public void populateTable(Jobs jobs){
        jobScrollView =  findViewById(R.id.groupMemberListLayout);

        //populating buttons with job names
        for (int i = 0; i < jobs.getJobDetails().size(); i++){
            String jobName = jobs.getJobDetails().get(i).getJobName();
            String mongoId = jobs.getJobDetails().get(i).getJobMongoId();
            ArrayList<String> jobDetails = new ArrayList<>();
            Button jobButton = new Button(this);
            jobButton.setOnClickListener(new View.OnClickListener() {
                List<JobTasks> jobForDescription = new ArrayList<>();
                @Override
                public void onClick(View v) {
                    needsDescriptionMap = new HashMap<>();
                    for (int i = 0; i < jobs.getJobDetails().size(); i++){
                        if (jobs.getJobDetails().get(i).getJobName().equals(jobButton.getText())){
                            jobForDescription = jobs.getJobDetails().get(i).getJobTasks();
                        }
                    }
                    Intent descriptionIntent = new Intent(JobListPage.this, JobTaskActivity.class);
                  //  descriptionIntent.putExtra("jobTaskDescription", needsDescriptionMap);
                    descriptionIntent.putExtra("jobDetails", jobDetails);
                    descriptionIntent.putExtra("jobName", jobName);
                    descriptionIntent.putExtra("mongoId", mongoId);
                    descriptionIntent.putExtra("jobForDescription", (Serializable) jobForDescription);
                    startActivity(descriptionIntent);
                }
            });
            jobButton.setText(jobName);
            jobScrollView.addView(jobButton);
        }
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");
        isOwner = sharedPreferences.getBoolean("isOwner", false);

    }

}
   // scrollListLayout.addView(peopleScrollView);


