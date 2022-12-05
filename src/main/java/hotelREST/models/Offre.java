package hotelREST.models;

import jdk.jfr.Enabled;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Offre {


    @Id
    private long OffreID;

    private String idPartenaire;

    @ManyToOne(targetEntity=Chambre.class)
    private Chambre chambre;

    @ManyToOne(targetEntity=DateInterval.class)
    private DateInterval dateInterval;

    private float prix;

    private boolean disponible;

    private int nbPersonnes;


    //Constructeurs
    public Offre(){

    }

    public Offre(long OffreID, String idPartenaire, Chambre chambre, DateInterval dateInterval, float prix, int nbPersonnes){
        this.OffreID = OffreID;
        this.idPartenaire = idPartenaire;
        this.chambre = chambre;
        this.dateInterval = dateInterval;
        this.prix = prix;
        this.disponible = true;
        this.nbPersonnes = nbPersonnes;
    }

    //GETTERS
    public long getOffreID() {
        return OffreID;
    }

    public DateInterval getDateInterval() {
        return dateInterval;
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

    public void setOffreID(int offreID) {
        this.OffreID = offreID;
    }

    public void setIdPartenaire(String idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public void setDateInterval(DateInterval dateInterval) {
        this.dateInterval = dateInterval;
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
        return "Offre n°" + this.OffreID + " : " + this.chambre.toString() + " du " + this.dateInterval.getStartDate() + " au " + this.dateInterval.getEndDate() + " pour " + this.prix + "€";
    }

    public int getNbPersonnes() {
        return this.nbPersonnes;
    }
}
