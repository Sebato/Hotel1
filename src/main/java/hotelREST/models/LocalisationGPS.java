package hotelREST.models;

public class LocalisationGPS {
    public String latitude;
    public String longitude;

    public LocalisationGPS(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "localisation_GPS{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}

