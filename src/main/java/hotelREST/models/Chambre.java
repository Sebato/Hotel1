package hotelREST.models;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Chambre {

    @Id
    private long id;
    private int numero;
    private int nb_Places;
    private float prix_Nuit;

    private long id_Hotel;

//    @ManyToMany(targetEntity=DateInterval.class, cascade=CascadeType.ALL)
//    private List<DateInterval> dates_Prises;



    public Chambre() {

    }

    public Chambre(int nb_Places, float prix_Nuit) {
        this.nb_Places = nb_Places;
        this.prix_Nuit = prix_Nuit;
//        this.dates_Prises = new ArrayList<DateInterval>();
    }

    public Chambre(long id, int nb_Places, float prix_Nuit) {
        this.id = id;
        this.nb_Places = nb_Places;
        this.prix_Nuit = prix_Nuit;
//        this.dates_Prises = new ArrayList<DateInterval>();
    }

    public int getNumero() {
        return numero;
    }

    public int getNb_Places() {
        return this.nb_Places;
    }

    public float getPrix_Nuit() {
        return this.prix_Nuit;
    }

    public void setNumero(int i) {this.numero = i;}

//    public boolean Est_Libre(Date Date_arrivée, Date Date_départ) {
//        DateInterval dateInterval = new DateInterval(Date_arrivée, Date_départ);
//        for ( DateInterval date : this.dates_Prises ) {
//            if ( dateInterval.isOverlapping(date) ) {
//                return false;
//            }
//        }
//        return true;
//    }

//    public void Ajouter_Date(Date date_arrivée, Date date_départ) {
//        this.dates_Prises.add(new DateInterval(date_arrivée, date_départ));
//    }

    public String toString() {
        return "Chambre["+ this.id +"] (Hotel "+this.id_Hotel+") num : " + this.numero + " (" + this.nb_Places + " places) : " + this.prix_Nuit + "€/nuit";
    }

    public float getPrixSejour(DateInterval dateSejour){
        return this.prix_Nuit*dateSejour.nbNuits();
    }

    public float getPrixSejour(String dateArrivee, String dateDepart) throws ParseException {
        int duréeSejour = new DateInterval(dateArrivee, dateDepart).nbNuits();

        return this.prix_Nuit*duréeSejour;
    }

//    public List<DateInterval> getDates() {
//        return this.dates_Prises;
//    }


    public long getId_Hotel() {
        return id_Hotel;
    }

    public void setId_Hotel(long id_Hotel) {
        this.id_Hotel = id_Hotel;
    }
}