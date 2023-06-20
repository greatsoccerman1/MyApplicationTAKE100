package Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplicationtake100.JobDescptionFragment;
import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;
import com.example.myapplicationtake100.TaskListFragment;

import java.math.BigDecimal;
import java.util.ArrayList;

import Models.ItemViewModel;
import Models.RemoveNeed;
import Models.changeJobStatusResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.myapplicationtake100.databinding.ActivityPersonNeedsBinding;

public class JobTaskActivity extends AppCompatActivity {

    //private ActivityPersonNeedsBinding binding;

    private Button jobNotDoneButton, jobDescButton, markJobCompleteButton;
    private String need;
    private ItemViewModel itemViewModel = null;

    private TextView jobNameTxt, jobPriceTxt, jobStatusTxt, notDoneSinceTxt, jobDescriptionTxt;
    private String jobName, jobStatus, notDoneSince, jobDescription, jobId, groupId;
    private BigDecimal jobPrice;
    public static final String SHARED_PREFS = "sharedPrefs";
   // private Se

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        itemViewModel = new ItemViewModel();
        jobNotDoneButton = findViewById(R.id.jobNotDoneBtn);
        jobDescButton = findViewById(R.id.jobCompleteButton);
        markJobCompleteButton = findViewById(R.id.jobCompleteButton);
        jobNameTxt = (TextView) findViewById(R.id.jobNameTxt);
        jobPriceTxt = (TextView) findViewById(R.id.jobPriceTxt);
        jobStatusTxt = (TextView) findViewById(R.id.jobStatusTxt);
        notDoneSinceTxt = (TextView) findViewById(R.id.notDoneSinceTxt);
        jobDescriptionTxt = (TextView) findViewById(R.id.jobDescriptTxt);

        setupMarkJobCompleteButton(markJobCompleteButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        jobDescriptionTxt.setText(jobDescription);
        jobStatusTxt.setText(jobStatus);
        jobNameTxt.setText(jobName);
        jobPriceTxt.setText(jobPrice.toString());
        notDoneSinceTxt.setText(notDoneSince);
    }

    public void switchToOtherTab(Fragment frag){
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.need_fragment_place, frag);
        fragmentTransaction.commit();*/
    }
    public void loadData(){
        Bundle extras = getIntent().getExtras();
        jobStatus = extras.getString("jobStatus");
        jobPrice = (BigDecimal) extras.getSerializable("jobPrice");
        jobName =  extras.getString("jobName");
        jobDescription =  extras.getString("jobDescription");
        notDoneSince =  extras.getString("notDoneSince");
        jobId = extras.getString("jobId");
        groupId = extras.getString("groupId");
    }

    public void setupMarkJobCompleteButton(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.180:8080/demo/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                Call<changeJobStatusResponse> call = jsonPlaceHolderApi.markJobComplete(jobId, groupId);
                call.enqueue(new Callback<changeJobStatusResponse>() {
                    @Override
                    public void onResponse(Call<changeJobStatusResponse> call, Response<changeJobStatusResponse> response) {
                        if (response.isSuccessful()){
                            jobStatusTxt.setText("Done did it");
                          // ((JobTaskActivity)getActivity()).switchToOtherTab(new TaskListFragment());
                        }
                    }

                    @Override
                    public void onFailure(Call<changeJobStatusResponse> call, Throwable t) {

                    }
                });
            }
        })
    }
}