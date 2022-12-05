package hotelREST.models;

import hotelREST.exceptions.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class Hotel {

    @Id
    private long id;

    private String nom;

    private int nb_Etoiles;

    @OneToOne(targetEntity=Adresse.class, cascade=CascadeType.ALL)
    private Adresse adresse;

    @OneToMany(targetEntity=Chambre.class, cascade=CascadeType.ALL)
    private List<Chambre> chambres;

    @OneToMany(targetEntity=Reservation.class, cascade=CascadeType.ALL)
    private List<Reservation> reservations;

    @ManyToMany(targetEntity=Partenaire.class, cascade=CascadeType.ALL)
    private List<Partenaire> partenaires;

    @OneToMany(targetEntity=Offre.class, cascade=CascadeType.ALL)
    private List<Offre> offres;


    public Hotel() {
    }


    //CONSTRUCTEUR SANS ID
    public Hotel(String nom, Adresse adresse, int nb_Etoiles) {
        this.nom = nom;
        this.nb_Etoiles = nb_Etoiles;
        this.adresse = adresse;
        this.chambres = new ArrayList<Chambre>();
        this.reservations = new ArrayList<Reservation>();

        this.partenaires = new ArrayList<Partenaire>();
        this.offres = new ArrayList<Offre>();
    }

    //CONSTRUCTEUR AVEC ID
    public Hotel(long id, String nom, Adresse adresse, int nb_Etoiles) {
        this.id = id;
        this.nom = nom;
        this.nb_Etoiles = nb_Etoiles;
        this.adresse = adresse;
        this.chambres = new ArrayList<Chambre>();
        this.reservations = new ArrayList<Reservation>();
        this.partenaires = new ArrayList<Partenaire>();
        this.offres = new ArrayList<Offre>();

    }

    public String getNom() {
        return nom;
    }
    public int getNb_Etoiles() {
        return nb_Etoiles;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public List<Chambre> getChambres() {
        return this.chambres;
    }
    public List<Chambre> getChambresLibres(String Date_arrivée, String Date_départ, Integer nbClients)
            throws ParseException {

        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(Date_arrivée);
        Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(Date_départ);
        List<Chambre> chambresLibres = new ArrayList<Chambre>();

        for ( Chambre chambre : this.chambres ) {
            if(nbClients == null){
                if ( Est_Libre(chambre, date1, date2) ) {
                    chambresLibres.add(chambre);
                }
            }
            else if ( chambre.getNb_Places() >= nbClients ) {
                if ( Est_Libre(chambre, date1, date2) ) {
                    chambresLibres.add(chambre);
                }
            }
        }
        return chambresLibres;
    }

    public boolean Est_Libre(Chambre chambre, Date date1, Date date2 )
    {
        for (Reservation reservation : this.reservations)
        {
            if (reservation.ChambreCheck(chambre,date1,date2)) {
                return false;
            }
        }
        return true;
    }

    public boolean Est_Libre(Chambre chambre, String date1, String date2 ) throws ParseException {
        Date d1 = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
        Date d2 = new SimpleDateFormat("dd-MM-yyyy").parse(date2);
        for (Reservation reservation : this.reservations)
        {
            if (reservation.ChambreCheck(chambre,d1,d2)) {
                return false;
            }
        }
        return true;
    }




    public List<Offre> getOffres(){
        return this.offres;
    }
    public Offre getOffre(int i){
        return this.offres.get(i);
    }

    public List<Partenaire> getPartenaires() {
        return this.partenaires;
    }
    public void setChambres(List<Chambre> chambres) {
        this.chambres = chambres;
    }
    public void addChambre(Chambre chambre) {
        chambre.setNumero(this.chambres.size() + 1);
        chambre.setId_Hotel(this.id);
        this.chambres.add(chambre);
    }

    public void addPartenaire(Partenaire partenaire) {
        this.partenaires.add(partenaire);
    }


    public Partenaire findPartenaire(String identifiant) throws PartenaireNotFoundException {
        System.err.println("findPartenaire : " + identifiant);
        for (Partenaire partenaire : this.partenaires) {
            if (partenaire.getIdentifiant().equals(identifiant)) {
                return partenaire;
            }
        }
        throw new PartenaireNotFoundException();
    }

    public List<Offre> getOffres(String identifiant, String motDePasse, String date_arrivee, String date_depart, int nb_personnes)
            throws WrongCredentialsException, PartenaireNotFoundException, ParseException {
        Partenaire partenaire = findPartenaire(identifiant);
        if(partenaire.checkMotDePasse(motDePasse)) {
            return getOffresPartenaireValide(date_arrivee, date_depart , nb_personnes, partenaire);
        } else {
            throw new WrongCredentialsException();
        }
    }

    public List<Offre> getOffresPartenaireValide(String date_arrivee, String date_depart, int nbPersonnes, Partenaire partenaire) throws ParseException {
        List<Offre> offres = new ArrayList<Offre>();

        for( Chambre chambre : this.chambres) {
            if (Est_Libre(chambre, date_arrivee, date_depart) &&
                    (chambre.getNb_Places() >= nbPersonnes)) {

                long offreId = nextOfferIdAvailable();
//                DateInterval dateOffre = new DateInterval(offreId, date_arrivee, date_depart);
                Offre e = new Offre(offreId,
                        partenaire.getIdentifiant(),
                        chambre,
                        date_depart,
                        date_arrivee,
                        chambre.getPrixSejour(calculDuree(date_depart,date_arrivee)) * partenaire.getPourcentage(),
                        nbPersonnes);
                this.addOffre(e);
                offres.add(e);
            }
        }
        System.err.println("getOffresPartenaireValide : " + offres.size()
                + " offres trouvées pour le partenaire " + partenaire.getIdentifiant()
                + "\npour la période du " + date_arrivee + " au " + date_depart
                + " pour " + nbPersonnes + " personnes: \n");

        for (Offre offre : offres) {
            System.err.println("\n"+offre);
        }

        return offres;
    }

    public long nextOfferIdAvailable()
    {
        return this.offres.size()+1;
    }

    public String toString(){
        String hotelStr = "\n";
        String chambresStr = "";

        hotelStr += "Hotel "+this.id+" : " + this.nom + "\n";
        hotelStr += "nb_Etoiles : " + this.nb_Etoiles + "\n";
        hotelStr += "adresse : " + this.adresse.toString() + "\n";
        hotelStr += "nb_chambres : " + this.chambres.size() + "\n";

        if (this.chambres.size() > 0) {
            for ( Chambre chambre : this.chambres ) {
                chambresStr += "- " + chambre.toString() + "\n";
            }
            hotelStr += "chambres : " + chambresStr + "\n";
        }

        return hotelStr;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEtoiles(int nb_etoiles) {
        this.nb_Etoiles = nb_etoiles;
    }

    public void setAddress(Adresse adresse) {
        this.adresse = adresse;
    }

    public void addOffre(Offre offre) {
        this.offres.add(offre);
    }

    public List<Reservation> getReservation(Reservation reservation) {
        return this.reservations;
    }

    public Reservation getReservation(long ref) {
        for (Reservation reservation : this.reservations) {
            if (reservation.getId() == ref) {
                return reservation;
            }
        }
        return null;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public Offre findOffre(String identifiantAgence, int offreID)
            throws PartenaireNotFoundException, OfferNotFoundException, WrongAgencyException  {

        //on cherche le partenaire
        try {
            Partenaire partenaire = findPartenaire(identifiantAgence);
        } catch (PartenaireNotFoundException e) {
            throw new PartenaireNotFoundException();
        }

        //on cherche l'offre
        Offre offre = null;
        for (Offre o : this.offres) {

            if (o.getOffreID() == offreID) {
                offre = o;
            }
        }
        if (offre == null) {
            throw new OfferNotFoundException();
        }

        //on verifie que l'offre appartient bien a l'agence
        if (offre.estMemePartenaire(identifiantAgence))
        {
            return offre;
        }
        else
        {
            throw new WrongAgencyException();
        }
    }

    public Reservation reserver(String identifiantAgence, int offreID, String nom, String prenom, String cred)
            throws PartenaireNotFoundException, WrongAgencyException, OfferNotFoundException, AlreadyBookedException {
        Offre offre = findOffre(identifiantAgence, offreID);
        if (offre != null ) {

            if (!offre.getDisponible())
            {
                throw new AlreadyBookedException();
            }

            //client
            Client client = new Client(nom, prenom, cred);

            //reservation
//            Date arrivee = offre.getDateInterval().getStartDate();
//            Date depart = offre.getDateInterval().getEndDate();
//            Chambre chambre = offre.getChambre();
            float prix = offre.getPrix();
            long refRes = this.nextReservationId();

            Reservation reservation = new Reservation( refRes, offre, client);

            this.reservations.add(reservation);
            offre.setDisponible(false);

            return reservation;
        }
        return null;
    }

    private int nextReservationId() {
        return this.reservations.size();
    }

    public void dispOffres() {
        System.out.println("Offres :");
        for (Offre offre : this.offres) {
            System.out.println(offre);
        }
    }

    public void dispPartenaires() {
        System.out.println("Offres :");
        for (Partenaire partenaire : this.partenaires) {
            System.out.println(partenaire);
        }
    }

    public int calculDuree(Date date_debut, Date date_fin ) {
        return (int) (date_debut.getTime() - date_fin.getTime()) / (1000 * 60 * 60 * 24);
    }

    public int calculDuree(String date_debut, String date_fin ) throws ParseException {
        return (int) (dateConvert(date_debut).getTime() - dateConvert(date_fin).getTime()) / (1000 * 60 * 60 * 24);
    }

    public static Date dateConvert(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }
}