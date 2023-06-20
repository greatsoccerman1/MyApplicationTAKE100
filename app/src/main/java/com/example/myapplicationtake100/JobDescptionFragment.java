package com.example.myapplicationtake100;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import Activities.JobTaskActivity;
import Models.RemoveNeed;
import Models.changeJobStatusResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class JobDescptionFragment extends Fragment {

    TextView descriptionTV, nameTV, mongoIdTV;

    String task, taskDescription, taskMongoId;
    Button removeButton, editButton, markJobCompleteButton;
    ArrayList<String> needsArrayList;
    public static final String SHARED_PREFS = "sharedPrefs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_descption, container, false);
        descriptionTV = v.findViewById(R.id.descriptionTextView);
        nameTV = v.findViewById(R.id.nameTextView);
        mongoIdTV = v.findViewById(R.id.mongoIdTextView);
        removeButton = v.findViewById(R.id.removeNeedButton);
        editButton = v.findViewById(R.id.editButton);
        markJobCompleteButton = v.findViewById(R.id.completeJobButton);



        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDescription(taskMongoId);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markJobComplete(taskMongoId);
            }
        });



        return v;
    }

   @Override
    public void onStart() {
       super.onStart();
       loadData();
       descriptionTV.setText("");
       mongoIdTV.setText("");
       nameTV.setText("");
        descriptionTV.setText("Need Description: " + taskDescription);
        mongoIdTV.setText("MongoId: " + taskMongoId);
        nameTV.setText("Need: " + task);
   }

    private void removeDescription(String mongoId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.180:8080/demo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<RemoveNeed> call = jsonPlaceHolderApi.getRemoveNeed(mongoId);
        call.enqueue(new Callback<RemoveNeed>() {
            @Override
            public void onResponse(Call<RemoveNeed> call, Response<RemoveNeed> response) {
                if (response.isSuccessful()){

                    ((JobTaskActivity)getActivity()).switchToOtherTab(new TaskListFragment());
                }
            }

            @Override
            public void onFailure(Call<RemoveNeed> call, Throwable t) {

            }
        });
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        task = sharedPreferences.getString("task", "");
        taskDescription = sharedPreferences.getString("taskDescription", "");
        taskMongoId = sharedPreferences.getString("taskMongoId", "");
    }

    private void markJobComplete(String mongoId){
        markJobCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.180:8080/demo/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                Call<changeJobStatusResponse> call = jsonPlaceHolderApi.markJobComplete(mongoId, null);
                call.enqueue(new Callback<changeJobStatusResponse>() {
                    @Override
                    public void onResponse(Call<changeJobStatusResponse> call, Response<changeJobStatusResponse> response) {
                        if (response.isSuccessful()){

                            ((JobTaskActivity)getActivity()).switchToOtherTab(new TaskListFragment());
                        }
                    }

                    @Override
                    public void onFailure(Call<changeJobStatusResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}