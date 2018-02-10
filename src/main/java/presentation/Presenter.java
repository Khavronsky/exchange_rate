package presentation;

import domain.RatesInteractor;

public class Presenter {

    private RatesInteractor ratesInteractor;

    public Presenter() {
        this.ratesInteractor = new RatesInteractor();
    }

    public String getRate (String base, String target){
        return ratesInteractor.getRate(base,target);
    }
}
