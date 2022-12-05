package hotelREST.models;

public class Search {
    private String date1;
    private String date2;
    private String ville;
    private Integer nbEtoiles;

    private Integer nbClients;

    private Float prixMin;
    private Float prixMax;
    public Search() {
        this.date1 = null;
        this.date2 = null;
        this.ville = null;
        this.nbEtoiles = null;
        this.nbClients = null;
        this.prixMin = null;
        this.prixMax = null;
    }

    public Search(String date1, String date2, String ville, Integer nbEtoiles, Integer nbClients, Float prixMin, Float prixMax) {
        this.date1 = date1;
        this.date2 = date2;
        this.ville = ville;
        this.nbEtoiles = nbEtoiles;
        this.nbClients = nbClients;
        this.prixMin = prixMin;
        this.prixMax = prixMax;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(Integer nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public Integer getNbClients() {
        return nbClients;
    }

    public void setNbClients(Integer nbClients) {
        this.nbClients = nbClients;
    }

    public Float getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(Float prixMin) {
        this.prixMin = prixMin;
    }

    public Float getPrixMax() {
        return prixMax;
    }

    public void setPrixMax(Float prixMax) {
        this.prixMax = prixMax;
    }

    @Override
    public String toString() {
        return "Search{" +
                "date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                ", ville='" + ville + '\'' +
                ", nbEtoiles=" + nbEtoiles +
                ", nbClients=" + nbClients +
                ", prixMin=" + prixMin +
                ", prixMax=" + prixMax +
                '}';
    }
}
