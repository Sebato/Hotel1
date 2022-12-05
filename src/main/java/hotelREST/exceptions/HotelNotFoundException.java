package hotelREST.exceptions;

public class HotelNotFoundException extends Exception{
    public HotelNotFoundException() {
    }
    public HotelNotFoundException(String message) {
        super(message);

    }
}
