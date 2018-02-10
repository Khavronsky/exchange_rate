package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ApiResponse;
import models.RateObject;

public class LoadDataManager {

    public ApiResponse getRate(String base, String target) {
        ConnectionHelper helper = new ConnectionHelper();
        String request = RequestBuilder.createRequest(base, target);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RateObject.class, new RatesDeserializer())
                .create();
        try {
            return gson.fromJson(helper.getData(request), ApiResponse.class);
        } catch (Exception e) {
            System.out.println("Unable to load data");
        }
        return null;
    }
}

