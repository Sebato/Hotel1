package hotelREST.exceptions;

public class OverlapException extends Exception {

    public OverlapException() {
        super("Overlapping dates");
    }
}
