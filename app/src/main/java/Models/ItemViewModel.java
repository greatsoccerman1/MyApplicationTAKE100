package Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<String> name;
    private MutableLiveData<ArrayList<String>> needs;
    private MutableLiveData<String> mongoId;



    public MutableLiveData<String> getMongoId() {
        if (mongoId == null){
            mongoId = new MutableLiveData<>();
        }

        return mongoId;
    }

    public void setMongoId(MutableLiveData<String> mongoId) {

        this.mongoId = mongoId;
    }

    public MutableLiveData<String> getName() {
        if (name == null){
            name = new MutableLiveData<>();
        }
        return name;
    }

    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }

    public MutableLiveData<ArrayList<String>> getNeeds() {
        if (needs == null){
            needs = new MutableLiveData<>();
        }
        return needs;
    }

    public void setNeeds(MutableLiveData<ArrayList<String>> needs) {
        this.needs = needs;
    }
}
