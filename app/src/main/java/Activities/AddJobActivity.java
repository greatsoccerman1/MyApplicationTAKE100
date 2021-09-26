package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.myapplicationtake100.*;

import Models.AddJobRequest;
import Models.AddJobResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddJobActivity extends AppCompatActivity {

    private Button addJob;
    private TextView jobName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        jobName = findViewById(R.id.jobName);
        addJob = findViewById(R.id.addJobBtn);

        setupAddJobBtn(addJob);
    }

    private void setupAddJobBtn(Button addJob) {
        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.131.148:8080/demo/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AddJobRequest addJobModel = new AddJobRequest();

                addJobModel.setJobName(jobName.getText().toString());

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<AddJobResponse> call = jsonPlaceHolderApi.addNewJob(addJobModel);
                call.enqueue(new Callback<AddJobResponse>() {
                    @Override
                    public void onResponse(Call<AddJobResponse> call, Response<AddJobResponse> response) {
                        if (!response.isSuccessful()){

                        }else{
                            //Post posts = response.body();

                        }
                    }

                    @Override
                    public void onFailure(Call<AddJobResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}