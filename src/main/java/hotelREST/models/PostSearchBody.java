package hotelREST.models;

import java.util.Objects;

public class PostSearchBody {
    private String Idagence;
    private String Mdpagence;
    private String date1;
    private String date2;
    private String nbClient;

    public PostSearchBody(String Idagence, String Mdpagence, String date1, String date2, String nbClient) {
        this.Idagence = Idagence;
        this.Mdpagence = Mdpagence;
        this.date1 = date1;
        this.date2 = date2;
        this.nbClient = nbClient;
    }

    public String getIdagence() {
        return Idagence;
    }

    public void setIdagence(String idagence) {
        Idagence = idagence;
    }

    public String getMdpagence() {
        return Mdpagence;
    }

    public void setMdpagence(String mdpagence) {
        Mdpagence = mdpagence;
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

    public String getNbClient() {
        return nbClient;
    }

    public void setNbClient(String nbClient) {
        this.nbClient = nbClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostSearchBody postSearchBody = (PostSearchBody) o;
        return Objects.equals(Idagence, postSearchBody.Idagence) && Objects.equals(Mdpagence, postSearchBody.Mdpagence) && Objects.equals(date1, postSearchBody.date1) && Objects.equals(date2, postSearchBody.date2) && Objects.equals(nbClient, postSearchBody.nbClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Idagence, Mdpagence, date1, date2, nbClient);
    }

    @Override
    public String toString() {
        return "Search2{" +
                "Idagence='" + Idagence + '\'' +
                ", Mdpagence='" + Mdpagence + '\'' +
                ", date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                ", nbClient='" + nbClient + '\'' +
                '}';
    }
}
