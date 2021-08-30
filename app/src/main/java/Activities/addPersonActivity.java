package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.R;

import Models.addPersonRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addPersonActivity extends AppCompatActivity {

    TextView firstName, lastName;
    Button addPersonBtn;
    private String userId, groupId;

    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        lastName = findViewById(R.id.addPersonLastName);
        firstName = findViewById(R.id.addPersonFirstName);
        //newPersonNeed = findViewById(R.id.addPersonNeed);
        //addPersonBtn = findViewById(R.id.addPersonBtn);
        loadData();
        setAddPersonBtn(addPersonBtn);



    }


    private void setAddPersonBtn(Button addPersonBtn) {
        addPersonBtn.setOnClickListener(v -> {
            addPersonRequest addPerson = new addPersonRequest();
            addPerson.setFirstName(firstName.getText().toString());
            addPerson.setLastName(lastName.getText().toString());
            addPerson.setGroupId(groupId);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.180:8080/demo/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<addPersonRequest> call = jsonPlaceHolderApi.addPerson(addPerson);
            call.enqueue(new Callback<addPersonRequest>() {
                @Override
                public void onResponse(Call<addPersonRequest> call, Response<addPersonRequest> response) {
                    if (!response.isSuccessful()) {
                    } else {
                       Intent i = new Intent(addPersonActivity.this, JobListPage.class);
                       // i.putExtra("people", (Serializable) response.body());
                       startActivity(i);
                    }
                }
                @Override
                public void onFailure(Call<addPersonRequest> call, Throwable t) {
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