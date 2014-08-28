package jobs;

import java.io.File;
import java.util.ArrayList;

import models.CountryCodes;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.IO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@OnApplicationStart
public class StartupJob extends Job {

    @Override
    public void doJob() throws Exception {
        if(CountryCodes.count() == 0){
            String json = IO.readContentAsString(new File("conf/countryCodes.json"));
            Gson gson = new Gson();
            
            java.lang.reflect.Type listType = new TypeToken<ArrayList<CountryCodes>>() {
            }.getType();
            ArrayList<CountryCodes> list = gson.fromJson(json,listType);
            for (CountryCodes countryCodes : list) {
                countryCodes.save();
            }
        }
    }
}
