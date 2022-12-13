package hotelREST.models;

import java.util.ArrayList;
import java.util.List;

public class ResponseOffers {

    private ArrayList<Offre> offers;

    public ResponseOffers(List<Offre> offres) {
        this.offers = (ArrayList<Offre>) offres;
    }

    public ResponseOffers(ArrayList<Offre> offers) {
        this.offers = offers;
    }

    public ArrayList<Offre> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offre> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "ResponseOffers{" +
                "offers=" + offers +
                '}';
    }


}
