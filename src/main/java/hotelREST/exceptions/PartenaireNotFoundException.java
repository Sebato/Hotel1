package hotelREST.exceptions;

public class PartenaireNotFoundException extends Exception {

    public PartenaireNotFoundException() {
        super("Partenaire not found");
    }
}
