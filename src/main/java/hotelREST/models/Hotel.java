package hotelREST.models;

import hotelREST.exceptions.*;

import javax.persistence.*;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity(name = "Hotel")
@Table(name = "Hotel")
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

    public boolean Est_Libre(Chambre chambre, Date date1, Date date2 ) throws ParseException {
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
        //System.err.println("findPartenaire : " + identifiant);
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
        List<Offre>offresGen = new ArrayList<Offre>();

        for( Chambre chambre : this.chambres) {
            if ((chambre.getNb_Places() >= nbPersonnes) &&
                    Est_Libre(chambre, date_arrivee, date_depart) ) {

                long offreId = nextOfferIdAvailable();
                Offre e = new Offre(offreId,
                        partenaire.getIdentifiant(),
                        chambre,
                        date_arrivee,
                        date_depart,
                        chambre.getPrixSejour(calculDuree(date_depart,date_arrivee)) * partenaire.getPourcentage(),
                        nbPersonnes);
                offresGen.add(e);
                this.addOffre(e);
            }
        }
//        System.err.println("getOffresPartenaireValide : " + offresGen.size()
//                + " offresGen trouvées pour le partenaire " + partenaire.getIdentifiant()
//                + "\npour la période du " + date_arrivee + " au " + date_depart
//                + " pour " + nbPersonnes + " personnes: \n");


        this.dispOffres();

        return offresGen;
    }

    public long nextOfferIdAvailable(){
        if (this.offres.size() == 0) { return 1; }
        else { return this.offres.get(this.offres.size() - 1).getOffreID() + 1; }
    }

    public String toString(){
        String hotelStr = "\n";
        String tmpStr = "";

        hotelStr += "Hotel "+this.id+" : " + this.nom + "\n";
        hotelStr += "nb_Etoiles : " + this.nb_Etoiles + "\n";
        hotelStr += "adresse : " + this.adresse.toString() + "\n";
        hotelStr += "nb_chambres : " + this.chambres.size() + "\n";

        if (this.chambres.size() > 0) {
            for ( Chambre chambre : this.chambres ) {
                tmpStr = "";
                tmpStr += "- " + chambre.toString() + "\n";
            }
            hotelStr += "chambres : " + tmpStr + "\n";
        }

        if (this.offres.size() > 0) {
            for ( Offre offre : this.offres ) {
                tmpStr = "";
                tmpStr += "- " + offre.toString() + "\n";
            }
            hotelStr += "Offres : " + tmpStr + "\n";
        }

        if (this.reservations.size() > 0) {
            for ( Reservation reservation : this.reservations ) {
                tmpStr = "";
                tmpStr += "- " + reservation.toString() + "\n";
            }
            hotelStr += "Reservations : " + tmpStr + "\n";
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

    public List<Reservation> getReservations() {
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
            Partenaire p = findPartenaire(identifiantAgence);
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

    public Reservation reserver(String identifiantAgence, String MdpAgence, int offreID, String c_nom, String c_prenom)
            throws PartenaireNotFoundException, WrongAgencyException, OfferNotFoundException, AlreadyBookedException,
            WrongCredentialsException, OverlapException, ParseException, YOUDIDSHITBROException {

        //1. vérification de l'identité de l'agence qui demande la réservation

        Partenaire partenaire = findPartenaire(identifiantAgence);
        if(!partenaire.checkMotDePasse(MdpAgence)) {
            throw new WrongCredentialsException();
        }
        else {

            //2. vérification de la présence de l'offre

            Offre offre = findOffre(identifiantAgence, offreID);
            if (offre != null ) {

                //3. vérification de la disponibilité de l'offre

                if (!offre.getDisponible()){ throw new AlreadyBookedException(); }

                //(3 bis vérification anti conflits)
                this.disableConflictingOffers(offre);

                //4. création de la réservation

                long refRes = this.nextReservationId();
                Reservation reservation = new Reservation( refRes, offre, c_nom, c_prenom);

                //5. ajout de la réservation à la liste des réservations de l'hôtel

                this.reservations.add(reservation);

                //6. mise à jour de la disponibilité de l'offre

                offre.setDisponible(false);

                return reservation;
            }

        }
        return null;
    }

    private void disableConflictingOffers(Offre offre) throws OverlapException, ParseException, YOUDIDSHITBROException {
        System.out.println("disableConflictingOffers with offer " + offre.toString());

        //liste temporaire pour éviter de modifier la liste des offres de l'hotel pendant la boucle (VERY BAD)
        ArrayList<Offre> tmpOffres = new ArrayList<Offre>(this.offres);

        for (Offre o : tmpOffres) {
            System.out.print("\n  - offre : " + o.getOffreID());
            if ( (!offre.equals(o)) && o.getChambre().equals(offre.getChambre())) {
                if (o.dateConflictWith(offre)) {
                    System.err.println("\ndisableConflictingOffers : " + o.toString()
                            + " est en conflit avec " + offre.toString());
                    if (o.getDisponible()) {
//                        o.setDisponible(false); // A VOIR SI ON SUPPRIME L'OFFRE CONFLICTUELLE
                        // (OUI) :
                        this.deleteOffer(o);
                        // SINON ON DOIT CHECKER TOUTES LES RESERVATIONS POUR SAVOIR SI ELLE A ETE UTILISEE PLUTOT)
                        System.err.println("disableConflictingOffers : "
                                + o.toStringXS() + " est désormais indisponible");
                    }else
                    {
                        System.err.println("disableConflictingOffers : "
                                + o.toStringXS() + " est déjà réservée");
                        throw new OverlapException();
                    }
                }

            }
            System.out.println(" : OK");
        }
    }

    private void deleteOffer(Offre o) throws YOUDIDSHITBROException {
        for (Reservation r : this.reservations) {
            if (r.getOffre().equals(o)) {
                throw new YOUDIDSHITBROException();
            }
        }
        if (this.offres.contains(o)) {
            this.offres.remove(o);
            System.out.println("deleteOffer : " + o.toStringXS() + " supprimée");
        }
    }

    private int nextReservationId() {
        if (this.reservations.size() == 0) { return 1; }
    else { return (int) (this.reservations.get(this.reservations.size() - 1).getId() + 1); }
    }

    public void dispOffres() {
        System.out.println("toutes les Offres :");
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

    public List<Offre> getOffresChmbr(long id) {
        List<Offre> offresChmbr = new ArrayList<Offre>();
        for (Offre o : this.offres) {
            if (o.getChambre().getNumero() == id) {
                offresChmbr.add(o);
            }
        }
        return offresChmbr;
    }

    public List<Reservation> getReservations_Chmbr(long id) {
        List<Reservation> reservationsChmbr = new ArrayList<Reservation>();
        for (Reservation r : this.reservations) {
            if (r.getOffre().getChambre().getNumero() == id) {
                reservationsChmbr.add(r);
            }
        }
        return reservationsChmbr;
    }
}