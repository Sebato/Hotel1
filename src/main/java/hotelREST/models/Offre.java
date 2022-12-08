package hotelREST.models;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;

@Entity
public class Offre {

    @Id
    private long id;
    private String idPartenaire;
    @ManyToOne
    private Chambre chambre;
    private String d1;
    private String d2;
    private float prix;
    private boolean disponible;
    private int nbPersonnes;


    //Constructeurs
    public Offre(){
    }

    public Offre(long OffreID, String idPartenaire, Chambre chambre, DateInterval dateInterval, float prix, int nbPersonnes){
        this.id = OffreID;
        this.idPartenaire = idPartenaire;
        this.chambre = chambre;
        //this.dateInterval = dateInterval;
        this.prix = prix;
        this.disponible = true;
        this.nbPersonnes = nbPersonnes;
    }

    public Offre(long OffreID, String idPartenaire, Chambre chambre, String d1, String d2, float prix, int nbPersonnes){
        this.id = OffreID;
        this.idPartenaire = idPartenaire;
        this.chambre = chambre;
        this.d1 = d1;
        this.d2 = d2;
        this.prix = prix;
        this.disponible = true;
        this.nbPersonnes = nbPersonnes;
    }

    //GETTERS
    public long getOffreID() {
        return id;
    }

    public float getPrix() {
        return prix;
    }

    public String getIdPartenaire() {
        return idPartenaire;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setOffreID(int id) {
        this.id = id;
    }

    public void setIdPartenaire(String idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public boolean estMemePartenaire(String idPartenaire){
        return this.idPartenaire.equals(idPartenaire);
    }

    public String toString(){
        return "Offre n°" + this.id + " : " + this.chambre.toString() +  " du " + this.d1 + " au " + this.d2 + " pour " + this.prix + "€";
    }

    public int getNbPersonnes() {
        return this.nbPersonnes;
    }

    public String getD1() {
        return d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public boolean dateConflictWith(Offre offre) throws ParseException {
        Date date_arrivee_1 = Hotel.dateConvert(this.d1);
        Date date_depart_1 = Hotel.dateConvert(this.d2);
        Date date_arrivee_2 = Hotel.dateConvert(offre.getD1());
        Date date_depart_2 = Hotel.dateConvert(offre.getD2());

        if (date_depart_1.compareTo(date_arrivee_2) < 0 || date_arrivee_1.compareTo(date_depart_2) > 0) {
            return false;
        }
        return true;
    }

    public String toStringXS() {
        return "Offre n°" + this.id;
    }
}
