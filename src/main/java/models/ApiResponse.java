package models;

public class ApiResponse {
    private String base;
    private RateObject rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public RateObject getRates() {
        return rates;
    }

    public void setRates(RateObject rates) {
        this.rates = rates;
    }
}
