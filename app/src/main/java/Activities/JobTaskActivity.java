package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;
import com.example.myapplicationtake100.TaskListFragment;

import java.util.ArrayList;

import Models.ItemViewModel;
import Models.MarkJobCompleteRequest;
import Models.MarkJobCompleteResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.myapplicationtake100.databinding.ActivityPersonNeedsBinding;

public class JobTaskActivity extends AppCompatActivity {

    //private ActivityPersonNeedsBinding binding;

    private Button jobListButton, addTaskButton;
    private String need, jobId, groupId;
    private int refreshRate;
    private ItemViewModel itemViewModel = null;
    public static final String SHARED_PREFS = "sharedPrefs";
   // private Se

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        itemViewModel = new ItemViewModel();
        jobListButton = findViewById(R.id.jobListButton);
        addTaskButton = findViewById(R.id.addTaskBtn);

        ArrayList<String> personNeeds = new ArrayList<>();
        jobListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToOtherTab(new TaskListFragment());
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JobTaskActivity.this, AddTaskActivity.class);
                startActivity(i);

            }
        });
        //switchToOtherTab(new TaskListFragment());
    }

    private void markJobComplete() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demoapp.hopto.org:80/demo-0.0.1-SNAPSHOT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarkJobCompleteRequest markJobCompleteRequest = new MarkJobCompleteRequest();
        markJobCompleteRequest.setJobId(jobId);
        markJobCompleteRequest.setGroupId(groupId);
        markJobCompleteRequest.setRefreshRate(refreshRate);

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<MarkJobCompleteResponse> call = jsonPlaceHolderApi.markJobComplete(markJobCompleteRequest);
        call.enqueue(new Callback<MarkJobCompleteResponse>() {
            @Override
            public void onResponse(Call<MarkJobCompleteResponse> call, Response<MarkJobCompleteResponse> response) {
                if (!response.isSuccessful()){

                }else{
                    //Post posts = response.body();

                }
            }

            @Override
            public void onFailure(Call<MarkJobCompleteResponse> call, Throwable t) {

            }
        });
    }

    public void switchToOtherTab(Fragment frag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.need_fragment_place, frag);
        fragmentTransaction.commit();
    }

    public void setNeedForDescriptionFrag(String need){
        this.need = need;
    }

    public String getNeedForDescriptionFrag(){
        return need;
    }


    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        jobId = sharedPreferences.getString("jobId", "");
        groupId = sharedPreferences.getString("groupId", "");
       // refreshRate = sharedPreferences.getString("mongoId", "");
    }
}