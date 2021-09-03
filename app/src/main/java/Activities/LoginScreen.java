package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtake100.JsonPlaceHolderApi;
import com.example.myapplicationtake100.Post;
import com.example.myapplicationtake100.R;
import com.example.myapplicationtake100.register_new_account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity {
    private Boolean passwordBeenClicked = false, userNameBeenClicked = false;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        TextView userNameView = findViewById(R.id.userNameText);
        TextView passwordView = findViewById(R.id.passwordText);
        TextView failedLogin = findViewById(R.id.failedLoginMsg);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerButton = findViewById(R.id.registerTextButton);


        setupTextFieldClear(userNameView);
        setupTextFieldClear(passwordView);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.180:8080/demo/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                Call<Post> call = jsonPlaceHolderApi.getPosts( userNameView.getText().toString(), passwordView.getText().toString());
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (!response.isSuccessful()){
                            failedLogin.setText("Response Code: " + response.code());
                        }else{
                            //Post posts = response.body();
                            saveData(response.body().getUserId());
                            Intent i = new Intent(LoginScreen.this, groupActivity.class);
                            i.putExtra("userId", response.body().getUserId());
                            i.putExtra("userName", response.body().getUserName());
                            i.putExtra("groupInfo", response.body().getGroupInfo());
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        failedLogin.setText(t.getMessage());
                    }
                });
            }
        });

        setupRegisterbutton(registerButton);
        userNameView.setText("abc");
        passwordView.setText("123");
    }

    @Override
    public void finish() {
        super.onDestroy();
    }


    public void setupTextFieldClear(TextView textFieldToClear){
        textFieldToClear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (getResources().getResourceEntryName(textFieldToClear.getId()).equals("userNameText") && userNameBeenClicked == false){
                    textFieldToClear.setText("");
                    userNameBeenClicked = true;
                }
                if (getResources().getResourceEntryName(textFieldToClear.getId()).equals("passwordText") && passwordBeenClicked == false){
                    textFieldToClear.setText("");
                    passwordBeenClicked = true;
                }
            }
        });
    }

    public void goToRegisterNew(View v){
        Intent i = new Intent(LoginScreen.this, groupActivity.class);
        startActivity(i);
    }

    public void setupRegisterbutton(TextView registerButton) {
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LoginScreen.this, register_new_account.class);
                startActivity(i);
            }
        });
    }

    public void saveData(String groupId){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("groupId", groupId);
        //editor.putString("userId", userId);
        editor.apply();
    }
}