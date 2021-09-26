package Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplicationtake100.JobDescptionFragment;
import com.example.myapplicationtake100.R;
import com.example.myapplicationtake100.TaskListFragment;

import java.util.ArrayList;

import Models.ItemViewModel;

//import com.example.myapplicationtake100.databinding.ActivityPersonNeedsBinding;

public class JobTaskActivity extends AppCompatActivity {

    //private ActivityPersonNeedsBinding binding;

    private Button jobListButton, addJobButton, markJobCompleteButton;
    private String need;
    private ItemViewModel itemViewModel = null;
   // private Se

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        itemViewModel = new ItemViewModel();
        jobListButton = findViewById(R.id.jobListButton);
        addJobButton = findViewById(R.id.addJobButton);
        markJobCompleteButton = findViewById(R.id.completeJobButton);

        ArrayList<String> personNeeds = new ArrayList<>();
        jobListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToOtherTab(new TaskListFragment());
            }
        });

        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JobTaskActivity.this, AddJobActivity.class);
                startActivity(i);

            }
        });

        //switchToOtherTab(new TaskListFragment());
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


}