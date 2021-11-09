package com.example.myapplicationtake100;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Activities.ManagerPage;
import Models.RegisterNewAccountRequest;
import Models.RegisterNewAccountResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register_new_account extends AppCompatActivity {

    TextView groupName, firstName, lastName, password, confirmPassword, warningTextView, userName;
    Button submitButton;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_account);
        groupName = findViewById(R.id.registerGroupName);
        firstName = findViewById(R.id.registerFirstName);
        lastName = findViewById(R.id.registerLastName);
        password = findViewById(R.id.registerPassword);
        userName = findViewById(R.id.userNameTextView);
        confirmPassword = findViewById(R.id.registerConfirmPassword);
        submitButton = findViewById(R.id.registerSubmitButton);
        warningTextView = findViewById(R.id.warningTextView);
        setupSubmitButton(submitButton);
    }

    public void setupSubmitButton(Button submitButton) {
        if (confirmPassword.getText().toString().equals(password.getText().toString())) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.146:8080/demo-0.0.1-SNAPSHOT/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    RegisterNewAccountRequest registerNewAccountReq = new RegisterNewAccountRequest();
                    registerNewAccountReq.setGroupName(groupName.getText().toString());
                    registerNewAccountReq.setFirstName(firstName.getText().toString());
                    registerNewAccountReq.setPassword(password.getText().toString());
                    registerNewAccountReq.setUserName(userName.getText().toString());

                    Call<RegisterNewAccountResponse> call = jsonPlaceHolderApi.addNewAccount(registerNewAccountReq);
                    call.enqueue(new Callback<RegisterNewAccountResponse>() {
                        @Override
                        public void onResponse(Call<RegisterNewAccountResponse> call, Response<RegisterNewAccountResponse> response) {
                            if (!response.isSuccessful()) {
                                warningTextView.setText(response.body().getRegisterNewAccountStatus());
                            } else {
                                String reqStatus = response.body().getRegisterNewAccountStatus();
                                if (reqStatus.equals("OK")) {
                                    saveData(response.body().getUserId(), response.body().getGroupId());
                                    Intent i = new Intent(register_new_account.this, ManagerPage.class);
                                    startActivity(i);
                                    finish();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterNewAccountResponse> call, Throwable t) {
                            warningTextView.setText("Something went wrong internally please try again");
                        }
                    });
                }
            });
        } else {
            warningTextView.setText("Passwords Do Not Match");
        }
    }

    public void saveData(String userId, String groupId){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.putString("groupId", groupId);
        editor.apply();
    }
}