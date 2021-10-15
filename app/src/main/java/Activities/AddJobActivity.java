package Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import Models.AddJobRequest;
import Models.AddJobResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddJobActivity extends AppCompatActivity {

    private Button addJob;
    private TextView jobName, jobPrice;
    private EditText refreshRateDaysView, refreshRateMonthsView;
    private String groupId;
    private int refreshRate, refreshRateDays, refreshRateMonths;
    private Spinner refreshRateSpinner, jobStatusSpinner;
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        jobName = findViewById(R.id.jobName);
        addJob = findViewById(R.id.addJobBtn);
        jobPrice = findViewById(R.id.jobPriceTV);
        refreshRateSpinner = findViewById(R.id.refreshRateSpinner);
        refreshRateDaysView = findViewById(R.id.jobRefreshRateDays);
        refreshRateMonthsView = findViewById(R.id.jobRefreshRateMonths);
        jobStatusSpinner = findViewById(R.id.jobStatusSpinner);
        loadData();
        setupAddJobBtn(addJob);
    }

    private void setupAddJobBtn(Button addJob) {
        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://demoapp.hopto.org:8443/demo/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                if (!(refreshRateDaysView.equals("0") || refreshRateDaysView == null) && !(refreshRateMonthsView.equals("0") || refreshRateMonthsView == null)){
                    refreshRateDays = Integer.parseInt(refreshRateDaysView.getText().toString());
                    refreshRateMonths = Integer.parseInt(refreshRateMonthsView.getText().toString()) *30;
                }else{
                    refreshRateDays = 0;
                    refreshRateMonths = 0;
                }

                refreshRate = refreshRateDays + refreshRateMonths;


                AddJobRequest addJobModel = new AddJobRequest();

                addJobModel.setJobName(jobName.getText().toString());
                addJobModel.setJobCost(Integer.parseInt(jobPrice.getText().toString()));
                addJobModel.setRefreshRate(refreshRate);
                addJobModel.setJobStatus(jobStatusSpinner.getSelectedItem().toString());
                addJobModel.setGroupId(groupId);


                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<AddJobResponse> call = jsonPlaceHolderApi.addNewJob(addJobModel);
                call.enqueue(new Callback<AddJobResponse>() {
                    @Override
                    public void onResponse(Call<AddJobResponse> call, Response<AddJobResponse> response) {
                        if (!response.isSuccessful()){

                        }else{
                            //Post posts = response.body();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddJobResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        groupId = sharedPreferences.getString("groupId", "");
    }
}