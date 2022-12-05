package hotelREST.exceptions;

public class AlreadyBookedException extends Exception {

    public AlreadyBookedException() {
        super("Offer Already booked");
    }
}
