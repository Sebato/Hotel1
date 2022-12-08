package hotelREST.models;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    private long reference;


    private String client_nom;

    private String client_prenom;

    @OneToOne (cascade = CascadeType.ALL)
    private Offre offre;


    public Reservation() {
    }

//    //reservation normale
//    public Reservation(Date dateDebut, Date dateFin, int chambre, int nbPersonnes, float prixTotal, Client client) {
//        this.dateDebut = dateDebut;
//        this.dateFin = dateFin;
//        this.numChambre= chambre;
//        this.nbPersonnes = nbPersonnes;
//        this.client = client;
//        this.prixTotal = prixTotal;
//    }

    //reservation normale V2
    public Reservation(long ref, Offre offre, String client_nom, String client_prenom) {
        this.reference = ref;
        this.client_nom = client_nom;
        this.client_prenom = client_prenom;
        this.offre = offre;
    }

//    //reservation sans client pour tests
//    public Reservation(Date dateDebut, Date dateFin, int chambre, int nbPersonnes, float prixTotal) {
//        this.dateDebut = dateDebut;
//        this.dateFin = dateFin;
//        this.numChambre= chambre;
//        this.nbPersonnes = nbPersonnes;
//        this.client = new Client();
//        this.prixTotal = prixTotal;
//    }

    public long getId() {
        return reference;
    }

    public Date getDateDebut() throws ParseException {
        return Hotel.dateConvert(this.offre.getD1());
    }
    public Date getDateFin() throws ParseException {
        return Hotel.dateConvert(this.offre.getD2());
    }
    public int getNumChambre() {
        return this.offre.getChambre().getNumero();
    }
    public int getNbPersonnes() {
        return this.offre.getNbPersonnes();
    }
    public float getPrixTotal() {
        return this.offre.getPrix();
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public boolean ChambreCheck(Chambre c, Date d1, Date d2) throws ParseException {
        return (this.getNumChambre() == c.getNumero() &&
                this.getDateDebut().equals(d1) &&
                this.getDateFin().equals(d2));
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reference=" + reference +
                ", client_nom='" + client_nom + '\'' +
                ", client_prenom='" + client_prenom + '\'' +
                ", offre=" + offre +
                '}';
    }
}
