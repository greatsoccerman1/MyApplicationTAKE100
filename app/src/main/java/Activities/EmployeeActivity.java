package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import java.io.Serializable;

import Models.Jobs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmployeeActivity extends AppCompatActivity  {

    private Button jobsButton;
    private Button addButton;
    private String userId, groupId;
    private TextView errorText;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobsButton = findViewById(R.id.jobButton);
        addButton = findViewById(R.id.addButton);
        errorText = findViewById(R.id.errorText);
        setJobsButton(jobsButton);
        setAddButton(addButton);
        loadData();
    }

    private void setAddButton(Button addButton){
            //ToDo allow employees to send request to add new jobs to the manager. This should be able to be turned on and off.
    }

    private void setJobsButton(Button jobsButton){
        jobsButton.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.180:8080/demo/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            Call<Jobs> call = jsonPlaceHolderApi.getJobs(groupId, userId);
            call.enqueue(new Callback<Jobs>() {
                @Override
                public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                    if (!response.isSuccessful()) {
                    } else {
                        if (response != null) {
                            Intent i = new Intent(EmployeeActivity.this, JobListPage.class);
                            i.putExtra("jobs", (Serializable) response.body());
                            startActivity(i);
                        }else{
                            errorText.setText("No results found");
                        }
                    }
                }
                @Override
                public void onFailure(Call<Jobs> call, Throwable t) {
                    errorText.setText("No results found");
                }
            });
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");

    }
}

