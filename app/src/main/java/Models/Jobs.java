package Models;

import java.io.Serializable;
import java.util.List;

public class Jobs implements Serializable {

    private List<JobsModel> jobInfo;

    public List<JobsModel> getJobDetails() {
        return jobInfo;
    }

    public void setPeopleNeeds(List<JobsModel> peopleNeeds) {
        this.jobInfo = peopleNeeds;
    }
}
