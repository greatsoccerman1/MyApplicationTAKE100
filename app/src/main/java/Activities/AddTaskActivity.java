package Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import Models.AddTaskRequest;
import Models.addTaskResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddTaskActivity extends AppCompatActivity {
        private TextView taskTextView, taskDescriptionTextView;
    public static final String SHARED_PREFS = "sharedPrefs";
    private String jobId;
    private Button addTaskButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskTextView = findViewById(R.id.TaskTextField);
        taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView);
        addTaskButton = findViewById(R.id.addTaskButton);
        loadData();
        setupAddTaskButton(addTaskButton);


    }

    private void setupAddTaskButton(Button addTaskButton) {
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskRequest addTaskRequest = new AddTaskRequest();
                addTaskRequest.setTaskName(taskTextView.getText().toString());
                addTaskRequest.setJobId(jobId);
                addTaskRequest.setTaskStatus("Not Done");
                addTaskRequest.setTaskDescription(taskDescriptionTextView.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://demoapp.hopto.org:80/demo-0.0.1-SNAPSHOT/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<addTaskResponse> call = jsonPlaceHolderApi.addNewTask(addTaskRequest);
                call.enqueue(new Callback<addTaskResponse>() {
                    @Override
                    public void onResponse(Call<addTaskResponse> call, Response<addTaskResponse> response) {
                        if (!response.isSuccessful()) {

                        } else {
                            //Post posts = response.body();
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<addTaskResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        jobId = sharedPreferences.getString("jobId", "");
    }
}