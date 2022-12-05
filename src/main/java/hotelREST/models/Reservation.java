package hotelREST.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservation {

    @Id
    private long reference;
    //private Date dateDebut;
    //private Date dateFin;
    //private int numChambre;
    //private int nbPersonnes;
    @OneToOne
    private Client client;

    @OneToOne
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
    public Reservation(long ref, Offre offre, Client client) {
        this.reference = ref;
        this.client = client;
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
    public Client getClient() {
        return client;
    }
    public Date getDateDebut() {
        return this.offre.getDateInterval().getStartDate();
    }
    public Date getDateFin() {
        return this.offre.getDateInterval().getEndDate();
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

    public boolean ChambreCheck(Chambre c, Date d1, Date d2)
    {
        return (this.getNumChambre() == c.getNumero() &&
                this.getDateDebut().equals(d1) &&
                this.getDateFin().equals(d2));
    }

}