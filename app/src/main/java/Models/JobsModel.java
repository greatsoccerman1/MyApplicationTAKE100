package Models;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class JobsModel extends ViewModel implements Serializable {
    private String jobName;
    private List<JobTasks> jobTask;
    private String jobMongoId;

    private String jobId;

    private String jobStatus;
   private BigDecimal jobPrice;

    public JobsModel(String name, List<JobTasks> Task, String mongoId, String jobId, String jobStatus) {
        this.jobName = name;
        this.jobTask = Task;
        this.jobMongoId = mongoId;
        this.jobId = jobId;
        this.jobStatus = jobStatus;
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String name) {
        this.jobName = name;
    }

    public String getJobMongoId() {
        return jobMongoId;
    }

    public void setMongoId(String jobMongoId) {
        this.jobMongoId = jobMongoId;
    }

    public void setJobTasks(List<JobTasks> task) {
        this.jobTask = task;
    }

    public List<JobTasks> getJobTasks() {
        return jobTask;
    }
}
