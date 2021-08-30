package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplicationtake100.R;

public class ManagerPage extends AppCompatActivity {

    Button peopleButton, jobButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_page);

        peopleButton = findViewById(R.id.peopleBtn);
        jobButton = findViewById(R.id.jobBtn);

        setupJobButton(jobButton);
        setupPersonButton(peopleButton);
    }

    public void setupJobButton(Button jobButton){
        jobButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(ManagerPage.this, JobListPage.class);
                startActivity(i);
            }
        });
    }

    public void setupPersonButton(Button peopleButton){
        peopleButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(ManagerPage.this, ManagerPeopleActivity.class);
                startActivity(i);
            }
        });
    }

}