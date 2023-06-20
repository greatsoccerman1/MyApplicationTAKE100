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
import java.math.BigDecimal;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        populateJobLayout();
    }

    public void populateJobLayout(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://149.115.2.24:80/demo-0.0.1-SNAPSHOT/")
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
                        Log.d("Logs", "onResponse: " + response.body().toString());
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
            String mongoId = jobs.getJobDetails().get(i).getJobMongoId();
            String jobName = jobs.getJobDetails().get(i).getJobName();
            String jobId = jobs.getJobDetails().get(i).getJobId();
            BigDecimal jobPrice = jobs.getJobDetails().get(i).getJobPrice();
            String jobStatus = jobs.getJobDetails().get(i).getJobStatus();
            ArrayList<String> jobDetails = new ArrayList<>();
            Button jobButton = new Button(this);
            setButtonColor(jobButton, jobStatus);
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
                    descriptionIntent.putExtra("jobId", jobId);
                    descriptionIntent.putExtra("jobPrice", jobPrice);
                    descriptionIntent.putExtra("jobStatus", jobStatus);
                    descriptionIntent.putExtra("groupId", groupId);
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

    public void setButtonColor(Button btn, String jobStatus){
        if (jobStatus.equals("NOT DONE")){
            btn.setBackgroundResource(R.drawable.job_not_done_button_bg);
        }else if(jobStatus.equals("DONE")){
            btn.setBackgroundResource(R.drawable.job_done_button_bg);
        }else {
            btn.setBackgroundResource(R.drawable.my_button_bg);
        }
    }

}
   // scrollListLayout.addView(peopleScrollView);


