package domain;


import data.DataStorageManager;
import data.LoadDataManager;
import models.ApiResponse;

public class RatesInteractor {
    private LoadDataManager loadDataManager;
    private DataStorageManager dataStorageManager;

    public RatesInteractor() {
        this.loadDataManager = new LoadDataManager();
        this.dataStorageManager = new DataStorageManager();
    }

    public String getRate(String base, String target) {
        if (dataStorageManager.checkActualData()) {
            String request = base + " => " + target;
            String dataFromFile = dataStorageManager.getDataFromFile(request);
            if (!dataFromFile.equals("")) {
                return dataFromFile;
            }
        }
        final ApiResponse apiResponse = loadDataManager.getRate(base, target);
        if (apiResponse != null) {
            String rateResponce = apiResponse.getBase()
                    + " => " + apiResponse.getRates().getName()
                    + " = " + apiResponse.getRates().getRate();
            saveDataToCache(rateResponce);
            return rateResponce;
        } else {
            return "An error occurred while retrieving data, please try again later";
        }
    }

    private void saveDataToCache(String data) {
        dataStorageManager.saveData(data, true);
    }
}
