package hotelREST.exceptions;

public class YOUDIDSHITBROException extends Exception {

    public YOUDIDSHITBROException() {
        super("cannot delete offer already booked");
    }
}
