package com.example.myapplicationtake100;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

public class JobInfo extends Fragment {

    private TextView refreshTV, jobNameTV, priceTV, statusTV;
    private String jobName, jobId, jobStatus;
    private BigDecimal jobPrice;
    private int refreshRate;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_job_info, container, false);
        refreshTV = v.findViewById(R.id.refreshRate);
        jobNameTV = v.findViewById(R.id.jobNameTV);
        priceTV = v.findViewById(R.id.priceTV);
        statusTV = v.findViewById(R.id.jobStatusTV);
        loadData();
        jobNameTV.setText(jobName);
        priceTV.setText(jobPrice.toString());
       // refreshTV.setText(refreshRate);
        statusTV.setText(jobStatus);

        return v;
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        jobName = sharedPreferences.getString("jobName", "");
        jobId = sharedPreferences.getString("jobId", "");
        jobStatus = sharedPreferences.getString("jobStatus", "");
        jobPrice = new BigDecimal(sharedPreferences.getFloat("jobPrice", 0));
        refreshRate = sharedPreferences.getInt("refreshRate", 0);
    }
}