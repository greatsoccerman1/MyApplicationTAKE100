package com.example.myapplicationtake100;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import Activities.ManagerPeopleActivity;
import Models.GetGroupMemberInfoRequest;
import Models.GetGroupMemberInfoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentPersonDescription extends Fragment {

    TextView firstNameText, lastNameText, memberIdText, totalMoneyMadeText, beginngingDateEarningsTV, endDateEarningsTV, earningTVSelected;
    String firstName, lastName, personId, getInfoForPersonId;
    String currentDate;
    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_person_description, container, false);
        firstNameText = v.findViewById(R.id.firstNameText);
        lastNameText = v.findViewById(R.id.lastNameText);
        memberIdText = v.findViewById(R.id.memberIdText);
        totalMoneyMadeText = v.findViewById(R.id.moneyMadeTV);
        beginngingDateEarningsTV = v.findViewById(R.id.beginngingDateEarningsTV);
        endDateEarningsTV = v.findViewById(R.id.endDateEarningsTV);
        return v;
    }

    public void onStart() {
        super.onStart();
        loadData();
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        memberIdText.setText(personId);
        populateMoneyMadeTV();
        setupDatePickerButton(beginngingDateEarningsTV);
        setupDatePickerButton(endDateEarningsTV);

    }

    public void populateMoneyMadeTV() {
        GetGroupMemberInfoRequest getGroupMemberInfoRequest = new GetGroupMemberInfoRequest();
        getGroupMemberInfoRequest.setInfoForPersonId(getInfoForPersonId);
        if (!beginngingDateEarningsTV.getText().toString().equals("Begin Date") && beginngingDateEarningsTV.getText()!=null){
            getGroupMemberInfoRequest.setStartEarningDate(beginngingDateEarningsTV.getText().toString());
        }else{
            getGroupMemberInfoRequest.setStartEarningDate(null);
        }

        if (endDateEarningsTV.getText()!= null && !endDateEarningsTV.getText().toString().equals("End Date")){
            getGroupMemberInfoRequest.setEndEarningDate(endDateEarningsTV.getText().toString());
        }else{
            getGroupMemberInfoRequest.setEndEarningDate(null);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demoapp.hopto.org:80/demo-0.0.1-SNAPSHOT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<GetGroupMemberInfoResponse> call = jsonPlaceHolderApi.getGroupMemberInfo(getGroupMemberInfoRequest);
        call.enqueue(new Callback<GetGroupMemberInfoResponse>() {
            @Override
            public void onResponse(Call<GetGroupMemberInfoResponse> call, Response<GetGroupMemberInfoResponse> response) {
                if (!response.isSuccessful()){

                }else{
                    totalMoneyMadeText.setText(response.body().getTotalIncome().toString());

                }
            }
            @Override
            public void onFailure(Call<GetGroupMemberInfoResponse> call, Throwable t) {
                totalMoneyMadeText.setText(t.getMessage());
            }
        });
    };

    private void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        firstName = sharedPreferences.getString("firstName", "");
        lastName = sharedPreferences.getString("lastName", "");
        getInfoForPersonId = sharedPreferences.getString("getInfoForPersonId", "");
    }



    private void setupDatePickerButton(TextView beginngingDateEarningsTV) {
        beginngingDateEarningsTV.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DialogFragment datePicker = new datePickerFragment();
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
                earningTVSelected = beginngingDateEarningsTV;
                ((ManagerPeopleActivity)getActivity()).setDateTextFieldToUpdate(earningTVSelected);
            }
        });
    }


}