package com.example.myapplicationtake100;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import Activities.ManagerPeopleActivity;
import Models.getGroupMembersRequest;
import Models.getGroupMembersResponse;
import Models.groupMember;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleListFragment extends Fragment {

    private TextView listTextView;
    private LinearLayout peopleScrollView;
    private ScrollView scrollListLayout;
    private ArrayList<String> needsArrayList;
    public static final String SHARED_PREFS = "sharedPrefs";
    private String userId, groupId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_people_list, container, false);
        listTextView = (TextView) v.findViewById(R.id.listTextView);
        peopleScrollView = v.findViewById(R.id.groupMemberListLayout);
        loadData();

        return v;
    }

    public void onStart() {
        super.onStart();
        getGroupMembersResponse resp = ((ManagerPeopleActivity)getActivity()).getGroupMembers();
        getGroupMembersResponse(groupId);
    }


    public void getGroupMembersResponse(String groupId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.180:8080/demo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getGroupMembersRequest request = new getGroupMembersRequest();
        request.setGroupId(groupId);

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<getGroupMembersResponse> call = jsonPlaceHolderApi.getGroupMembers(groupId);
        call.enqueue(new Callback<getGroupMembersResponse>() {
            @Override
            public void onResponse(Call<getGroupMembersResponse> call, Response<getGroupMembersResponse> response) {
                if (!response.isSuccessful()) {

                } else {
                    if (response != null) {
                        getGroupMembersResponse resp = response.body();
                        for (int i =0; i < resp.getGroupMembers().size(); i++) {
                            Button button = new Button(getContext());
                            button.setText(resp.getGroupMembers().get(i).getFirstName());

                            int finalI = i;
                            int finalI1 = i;
                            button.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    dataToSend(resp.getGroupMembers().get(finalI1));
                                    ((ManagerPeopleActivity)getActivity()).switchToOtherTab(new FragmentPersonDescription());
                                }
                            });
                            peopleScrollView.addView(button);
                           // button.setText(response.body()
                        }
                    }else{
                        //  errorText.setText("No results found");
                    }
                }
            }
            @Override
            public void onFailure(Call<getGroupMembersResponse> call, Throwable t) {
                // errorText.setText("No results found");
            }
        });
    }

    public void dataToSend(groupMember resp){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", resp.getFirstName());
        editor.putString("lastName", resp.getLastName());
        editor.putString("memberId", resp.getMemberId());

        editor.apply();
    }


    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");
    }
}