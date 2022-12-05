package hotelREST.exceptions;

public class OfferNotFoundException extends Exception {

    public OfferNotFoundException() {
        super("Offer not found");
    }
}
