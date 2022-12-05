package hotelREST.exceptions;

public class WrongAgencyException extends Throwable {
    
        public WrongAgencyException() {
            super("Wrong agency identifier");
        }
}
