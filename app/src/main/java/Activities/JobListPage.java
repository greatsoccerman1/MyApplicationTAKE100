package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Models.Jobs;
import Models.MarkJobCompleteRequest;
import Models.MarkJobCompleteResponse;
import Models.RemoveJobResponse;
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
    private String whichButtonIsClicked = "None";
    public static final String SHARED_PREFS = "sharedPrefs";
    private Button addJobBtn, markJobCompleteBtn, removeJobBtn, markJobIncompleteBtn;
    private Jobs mainJobs = new Jobs();
    private int refreshRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        addJobBtn = findViewById(R.id.addJobButton);
        markJobCompleteBtn = findViewById(R.id.markJobCompleteBtn);
        removeJobBtn = findViewById(R.id.removeJobButton);
        markJobIncompleteBtn = findViewById(R.id.markJobInCompleteBtn);
        loadData();
        setupAddJobBtn(addJobBtn);
        setupMarkCompleteBtn(markJobCompleteBtn, markJobIncompleteBtn);
        setupJobInCompleteBtn(markJobIncompleteBtn, markJobCompleteBtn);
        populateJobLayout();
        setupRemoveJobBtn(removeJobBtn);

    }

    private void setupJobInCompleteBtn(Button markJobIncompleteBtn, Button markJobCompleteBtn) {
        markJobIncompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichButtonIsClicked = "MarkInComplete";
                markJobIncompleteBtn.setBackgroundColor(Color.argb(255, 0,255,0));
                markJobCompleteBtn.setBackgroundColor(Color.argb(255, 255,0,255));
                populateTable(mainJobs, "DONE");
            }
        });
    }

    private void setupMarkCompleteBtn(Button markJobCompleteBtn, Button markJobIncompleteBtn) {
        markJobCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              whichButtonIsClicked = "MarkComplete";
              markJobCompleteBtn.setBackgroundColor(Color.argb(255, 0,255,0));
              markJobIncompleteBtn.setBackgroundColor(Color.argb(255, 255,0,255));
                populateTable(mainJobs, "NOT DONE");
            }
        });
    }

    private void setupAddJobBtn(Button addJobBtn) {
        addJobBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(JobListPage.this, AddJobActivity.class);
                startActivity(i);
            }
        });
    }

    private void setupRemoveJobBtn(Button removeJobBtn){
       // whichButtonIsClicked = "RemoveJob";
    }

    public void populateJobLayout(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demoapp.hopto.org:80/demo-0.0.1-SNAPSHOT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        if (isOwner){
           // userId = "00";
        }
        Call<Jobs> call = jsonPlaceHolderApi.getJobs(groupId, userId);
        call.enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                if (!response.isSuccessful()) {
                } else {
                    if (response != null) {
                        Log.d("Logs", "onResponse: " + response.body());
                        mainJobs = response.body();
                       populateTable(mainJobs, "NOT DONE");

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



    public void populateTable(Jobs jobs, String filter){
        jobScrollView =  findViewById(R.id.groupMemberListLayout);
        jobScrollView.removeAllViews();

        //populating buttons with job names
        for (int i = 0; i < jobs.getJobInfo().size(); i++){
            String jobName = jobs.getJobInfo().get(i).getJobName();
            int refreshRate = jobs.getJobInfo().get(i).getRefreshRate();
            String jobId = jobs.getJobInfo().get(i).getJobId();
            String jobStatus = jobs.getJobInfo().get(i).getJobStatus();
            BigDecimal jobPrice = jobs.getJobInfo().get(i).getJobPrice();
            Button jobButton = new Button(this);

            if (jobStatus.equals("NOT DONE")) {
                jobButton.setBackgroundResource(R.drawable.job_not_done_button_bg);
            } else if (jobStatus.equals("DONE")) {
                jobButton.setBackgroundResource(R.drawable.job_done_button_bg);
            } else {
                jobButton.setBackgroundResource(R.drawable.my_button_bg);
            }

            jobButton.setOnClickListener(new View.OnClickListener() {
                List<Jobs> jobs = new ArrayList<>();
                @Override
                public void onClick(View v) {
                    switch (whichButtonIsClicked) {
                        case "None":
                            noButtonsClicked(jobName, refreshRate, jobId, jobPrice, jobStatus);
                            break;
                        case "MarkComplete":
                            markJobComplete(jobId, refreshRate, jobButton, jobPrice, "DONE");
                            break;
                        case "MarkNeedsWork":
                            markNeedsWork(jobId);
                            break;
                        case "MarkInComplete":
                            markJobComplete(jobId, refreshRate, jobButton, jobPrice, "NOT DONE");
                            break;
                   /*     case "RemoveJob":
                            removeJob(groupId, jobId);
                            jobButton.setVisibility(View.GONE);
                            break;*/
                    }
                }
            });
            jobButton.setText(jobName);
            if (jobStatus.equals(filter)){
                jobScrollView.addView(jobButton);
            }

        }
    }

    private void markNeedsWork(String jobId){

    }

    private void markJobComplete(String jobId, int refreshRate, Button jobButton, BigDecimal price, String jobStatus) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demoapp.hopto.org:80/demo-0.0.1-SNAPSHOT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarkJobCompleteRequest markJobCompleteRequest = new MarkJobCompleteRequest();
        markJobCompleteRequest.setJobId(jobId);
        markJobCompleteRequest.setGroupId(groupId);
        markJobCompleteRequest.setRefreshRate(refreshRate);
        markJobCompleteRequest.setPrice(price);
        markJobCompleteRequest.setPersonId(userId);
        markJobCompleteRequest.setJobStatus(jobStatus);

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<MarkJobCompleteResponse> call = jsonPlaceHolderApi.markJobComplete(markJobCompleteRequest);
        call.enqueue(new Callback<MarkJobCompleteResponse>() {
            @Override
            public void onResponse(Call<MarkJobCompleteResponse> call, Response<MarkJobCompleteResponse> response) {
                if (!response.isSuccessful()) {

                } else {
                    //Post posts = response.body();
                    jobButton.setBackgroundResource(R.drawable.job_done_button_bg);
                }
            }

            @Override
            public void onFailure(Call<MarkJobCompleteResponse> call, Throwable t) {

            }
        });
    }

    private void removeJob(String groupId, String jobId){
        removeJobBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://demoapp.hopto.org:80/demo-0.0.1-SNAPSHOT/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                if (isOwner){
                    // userId = "00";
                }
                Call<RemoveJobResponse> call = jsonPlaceHolderApi.removeJob(groupId, jobId);
                call.enqueue(new Callback<RemoveJobResponse>() {
                    @Override
                    public void onResponse(Call<RemoveJobResponse> call, Response<RemoveJobResponse> response) {
                        if (!response.isSuccessful()) {
                        } else {
                            if (response != null) {
                                Log.d("Logs", "onResponse: " + response.body());

                            }else{
                                // errorText.setText("No results found");

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<RemoveJobResponse> call, Throwable t) {
                        //errorText.setText("No results found");
                    }
                });
            }
        });
    }

    private void noButtonsClicked(String jobName, int refreshRate, String jobId, BigDecimal jobPrice, String jobStatus){
        Intent descriptionIntent = new Intent(JobListPage.this, JobTaskActivity.class);
        //  descriptionIntent.putExtra("jobTaskDescription", needsDescriptionMap);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jobName", jobName);
        editor.putInt("refreshRate", refreshRate);
        editor.putString("jobId", jobId);
        editor.putFloat("jobPrice", jobPrice.floatValue());
        editor.putString("jobStatus", jobStatus);
        editor.apply();
        startActivity(descriptionIntent);
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");
        isOwner = sharedPreferences.getBoolean("isOwner", false);
    }

    private void saveData(String jobId){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jobId", jobId);
        editor.apply();
    }




}
   // scrollListLayout.addView(peopleScrollView);


