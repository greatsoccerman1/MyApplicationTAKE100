package com.example.myapplicationtake100;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Activities.AddTaskActivity;
import Activities.JobTaskActivity;
import Models.GetTasksRequest;
import Models.JobTasks;
import Models.JobTasksResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaskListFragment extends Fragment {

    private LinearLayout tasksScrollView;
    private String userId, groupId;
    private Boolean isOwner;
    private ArrayList<String> needsArrayList;
    private HashMap<String, String> descriptionNeedMap;
    private List<JobTasks> jobForDescription;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_needs, container, false);
        tasksScrollView =  v.findViewById(R.id.groupMemberListLayout);
        needsArrayList = getActivity().getIntent().getExtras().getStringArrayList("personNeeds");
        descriptionNeedMap = new HashMap<>();
        jobForDescription = (List<JobTasks>) getActivity().getIntent().getSerializableExtra("jobForDescription");
        loadData();
        return v;
    }

   @Override
    public void onStart() {
       super.onStart();
       populateJobLayout();
       /*for (int i = 0; i < jobForDescription.size(); i++) {
           Button button = new Button(getContext());
           button.setText(jobForDescription.get(i).getTask());
           final int numberToPass = i;
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   JobDescptionFragment frag = new JobDescptionFragment();
                   Bundle bundle = new Bundle();
                   String task = button.getText().toString();
                   String taskDescription = jobForDescription.get(numberToPass).getTaskDescription();
                   String taskMongoId = jobForDescription.get(numberToPass).getTaskMongoId();
                   saveData(task, taskDescription, taskMongoId);
                   frag.setArguments(bundle);
                   ((JobTaskActivity)getActivity()).setNeedForDescriptionFrag(button.getText().toString());
                   ((JobTaskActivity)getActivity()).switchToOtherTab(frag);
               }
           });
           tasksScrollView.addView(button);
       }*/
    }

    public void saveData(String task, String taskDescription, String taskMongoId){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("task", task);
        editor.putString("taskDescription", taskDescription);
        editor.putString("taskMongoId", taskMongoId);

        editor.apply();
    }

    public void populateJobLayout(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demoapp.hopto.org:8443/demo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        if (isOwner){
         //   userId = "00";
        }

        GetTasksRequest getTasksRequest = new GetTasksRequest();
        getTasksRequest.setJobId(getActivity().getIntent().getExtras().getString("jobId"));
        getTasksRequest.setUserId(userId);
        Call<JobTasksResponse> call = jsonPlaceHolderApi.getTasks(getTasksRequest);
        call.enqueue(new Callback<JobTasksResponse>() {
            @Override
            public void onResponse(Call<JobTasksResponse> call, Response<JobTasksResponse> response) {
                if (!response.isSuccessful()) {
                    int i =0;
                } else {
                    if (response != null) {
                        populateTask(response.body());
                    }else{
                        int i =0;
                        // errorText.setText("No results found");

                    }
                }
            }
            @Override
            public void onFailure(Call<JobTasksResponse> call, Throwable t) {
                //errorText.setText("No results found");
            }
        });
    };

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");
        isOwner = sharedPreferences.getBoolean("isOwner", false);

    }

    public void populateTask(JobTasksResponse resp) {
        tasksScrollView.removeAllViews();
        // tasksScrollView = new LinearLayout(getContext());
        if (resp.getJobTasks().size() > 0 && resp.getJobTasks() != null) {
            for (int i = 0; i < resp.getJobTasks().size(); i++) {
                Button button = new Button(getContext());
                button.setText(resp.getJobTasks().get(i).getTask());
                if (resp.getJobTasks().get(i).getTaskStatus().equals("NotDone")) {
                    button.setBackgroundResource(R.drawable.job_not_done_button_bg);
                } else if (resp.getJobTasks().get(i).getTaskStatus().equals("Done")) {
                    button.setBackgroundResource(R.drawable.job_done_button_bg);
                } else {
                    button.setBackgroundResource(R.drawable.my_button_bg);
                }

                int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String task = resp.getJobTasks().get(finalI).getTask();
                        String taskDescription = resp.getJobTasks().get(finalI).getTaskDescription();
                        String taskMongoId = resp.getJobTasks().get(finalI).getTaskMongoId();
                        saveData(task, taskDescription, taskMongoId);
                        JobDescptionFragment frag = new JobDescptionFragment();
                        ((JobTaskActivity) getActivity()).switchToOtherTab(frag);

                    }
                });
                tasksScrollView.addView(button);
            }
        }else{
            AlertDialog.Builder noTaskAlertDialog = new AlertDialog.Builder(getContext());
            noTaskAlertDialog.setTitle("No Jobs");
            noTaskAlertDialog.setMessage("There are no task assigned.");
            noTaskAlertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    closeDialog();
                }
            });
            AlertDialog alert1 =  noTaskAlertDialog.create();

            alert1.show();
        }
    }

    private void closeDialog() {
        Intent i = new Intent(getActivity().getApplicationContext(), AddTaskActivity.class);
        startActivity(i);
    }
}

