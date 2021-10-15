package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import Models.Jobs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerJobActivity extends AppCompatActivity {

    private String userId, groupId;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_job);
        loadData();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demoapp.hopto.org:8443/demo/")
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
                      //  Intent i = new Intent(EmployeeActivity.this, JobListPage.class);
                       // i.putExtra("people", (Serializable) response.body());
                        //startActivity(i);
                    }else{
                      //  errorText.setText("No results found");
                    }
                }
            }
            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {
               // errorText.setText("No results found");
            }
        });

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        groupId = sharedPreferences.getString("groupId", "");
    }
}