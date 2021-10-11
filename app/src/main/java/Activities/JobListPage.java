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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Models.Jobs;
import Models.MarkJobCompleteRequest;
import Models.MarkJobCompleteResponse;
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
    private Button addJobBtn, markJobCompleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        addJobBtn = findViewById(R.id.addJobButton);
        markJobCompleteBtn = findViewById(R.id.markJobCompleteBtn);
        loadData();
        setupAddJobBtn(addJobBtn);
        setupMarkCompleteBtn(markJobCompleteBtn);
        populateJobLayout();

    }

    private void setupMarkCompleteBtn(Button markJobCompleteBtn) {
        markJobCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              whichButtonIsClicked = "MarkComplete";
              markJobCompleteBtn.setBackgroundColor(Color.argb(255, 0,255,0));
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

    public void populateJobLayout(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://26.164.152.52:8080/demo/")
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
        for (int i = 0; i < jobs.getJobInfo().size(); i++){
            String jobName = jobs.getJobInfo().get(i).getJobName();
            String mongoId = jobs.getJobInfo().get(i).getJobMongoId();
            String jobId = jobs.getJobInfo().get(i).getJobId();
            Button jobButton = new Button(this);
            jobButton.setOnClickListener(new View.OnClickListener() {
                List<Jobs> jobs = new ArrayList<>();
                @Override
                public void onClick(View v) {
                    switch (whichButtonIsClicked) {
                        case "None":
                            noButtonsClicked(jobName, mongoId, jobId);
                            break;
                        case "MarkComplete":
                            markJobComplete(jobId, mongoId);
                            break;
                        case "MarkNeedsWork":
                            markNeedsWork(jobId, mongoId);
                            break;
                    }
                }
            });
            jobButton.setText(jobName);
            jobScrollView.addView(jobButton);
        }
    }

    private void markNeedsWork(String jobId, String mongoId){

    }

    private void markJobComplete(String jobId, String mongoId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://26.164.152.52:8080/demo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarkJobCompleteRequest markJobCompleteRequest = new MarkJobCompleteRequest();
        markJobCompleteRequest.setJobId(jobId);
        markJobCompleteRequest.setGroupId(groupId);
        markJobCompleteRequest.setMongoId(mongoId);

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<MarkJobCompleteResponse> call = jsonPlaceHolderApi.markJobComplete(markJobCompleteRequest);
        call.enqueue(new Callback<MarkJobCompleteResponse>() {
            @Override
            public void onResponse(Call<MarkJobCompleteResponse> call, Response<MarkJobCompleteResponse> response) {
                if (!response.isSuccessful()){

                }else{
                    //Post posts = response.body();

                }
            }

            @Override
            public void onFailure(Call<MarkJobCompleteResponse> call, Throwable t) {

            }
        });
    }

    private void noButtonsClicked(String jobName, String mongoId, String jobId){
        Intent descriptionIntent = new Intent(JobListPage.this, JobTaskActivity.class);
        //  descriptionIntent.putExtra("jobTaskDescription", needsDescriptionMap);
        descriptionIntent.putExtra("jobName", jobName);
        descriptionIntent.putExtra("mongoId", mongoId);
        descriptionIntent.putExtra("jobId", jobId);
        saveData(jobId);
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


