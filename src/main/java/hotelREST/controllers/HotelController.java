package hotelREST.controllers;

import hotelREST.exceptions.*;
import hotelREST.models.*;
import hotelREST.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class HotelController {
    @Autowired
    private HotelRepository repository;
    private static final String uri = "hotelservice/api";

    /* METHODS */


    // INFOS HOTEL
    @GetMapping(uri+"/hotel")
    public Hotel getHotel(){
        return repository.findAll().get(0);
    }

    //TOUTES LES CHAMBRES
    @GetMapping(uri+"/hotel/chambres")
    public List<Chambre> getChambres() throws HotelNotFoundException {
        return repository.findAll().get(0).getChambres();
    }

    //TOUS LES PARTENAIRES (RESERVÉ AU DEBUG)
    @GetMapping(uri+"/partenaires")
    public List<Partenaire> getPartenaires() throws HotelNotFoundException {
        return repository.findAll().get(0).getPartenaires();
    }

    //TOUTES LES OFFRES (RESERVÉ AU DEBUG)
    @GetMapping(uri+"/offres")
    public List<Offre> getOffres() throws HotelNotFoundException {
        return repository.findAll().get(0).getOffres();
    }

    //TOUTES LES OFFRES POUR UNE CHAMBRE DONNEE (RESERVÉ AU DEBUG)
    @GetMapping(uri+"/offres_chambre/{id}")
    public List<Offre> getOffres_chambre( @PathVariable long id) {
        return repository.findAll().get(0).getOffresChmbr(id);
    }

    //TOUTES LES RESERVATIONS (RESERVÉ AU DEBUG)
    @GetMapping(uri+"/reservations")
    public List<Reservation> getReservations() throws HotelNotFoundException {
        return repository.findAll().get(0).getReservations();
    }

    //TOUTES LES RESERVATIONS POUR UNE CHAMBRE DONNEE (RESERVÉ AU DEBUG)
    @GetMapping(uri+"/reservations_chambre/{id}")
    public List<Reservation> getReservations_chambre( @PathVariable long id){
        return repository.findAll().get(0).getReservations_Chmbr(id);
    }

    //CHAMBRES DISPONIBLES POUR UN INTERVALLE DE DATE ET UN NB DE CLIENTS
//    @GetMapping(uri+"/hotel/chambres_dispo")
//    public List<Chambre> getChambres_Disponibles(@RequestParam String date1, @RequestParam String date2, @RequestParam int nbclients) throws HotelNotFoundException, ParseException {
//        return repository.findAll().get(0).getChambresLibres(date1, date2, nbclients);
//    }

//    //CHAMBRES DISPONIBLES RECHERCHE AVANCÉE
//    @GetMapping(uri+"/hotel/recherche")
//    public List<Chambre> recherche_Chambre(@RequestParam String date1,
//                                           @RequestParam String date2,
//                                           @RequestParam Integer nbClients,
//                                           @RequestParam(required = false) String ville,
//                                           @RequestParam(required = false) Integer nbEtoiles,
//                                           @RequestParam(required = false) Float prixMin,
//                                           @RequestParam(required = false) Float prixMax)
//            throws HotelNotFoundException, ParseException {
//
//        Search search = new Search(date1, date2, ville, nbEtoiles, nbClients, prixMin, prixMax);
//
//        return repository.search_room(search).orElseThrow(() -> new HotelNotFoundException(
//                "Error: To debug\n param : " +
//                        "\n date1 : " + date1 +
//                        "\n date2 : " + date2 +
//                        "\n adresse : " + ville +
//                        "\n nbEtoiles : " + nbEtoiles +
//                        "\n nbClients : " + nbClients +
//                        "\n prixMin : " + prixMin +
//                        "\n prixMax : " + prixMax));
//    }

//    @ResponseStatus(HttpStatus.CREATED)

    //GENERATION D'OFFRES EN ACCORD AVEC LA RECHERCHE
//    @PostMapping(uri+"/recherche_offre")
//    public List<Offre> recherche_Offre(@RequestParam String Idagence,
//                                       @RequestParam String Mdpagence,
//                                       @RequestParam String date1,
//                                       @RequestParam String date2,
//                                       @RequestParam String nbClients)
//            throws PartenaireNotFoundException, ParseException, WrongCredentialsException {
//
//        Hotel hotel = repository.findAll().get(0);
//        List<Offre> offresGen;
//        offresGen = hotel.getOffres(Idagence, Mdpagence, date1, date2, Integer.parseInt(nbClients));
//        repository.save(hotel);
//
//        System.err.println("\nrepository saved\n");
//
//        return offresGen;
//    }

    @PostMapping(uri+"/recherche_offre")
    public ResponseOffers recherche_Offre(@RequestBody PostSearchBody search)
            throws PartenaireNotFoundException, ParseException, WrongCredentialsException {

        Hotel hotel = repository.findAll().get(0);
        ResponseOffers offresGen = new ResponseOffers(
                hotel.getOffres(search.getIdagence(),
                search.getMdpagence(),
                search.getDate1(),
                search.getDate2(),
                Integer.parseInt(search.getNbClient())));
        repository.save(hotel);

        System.err.println("\nrepository saved\n");

        return offresGen;
    }


    //RESERVATION D'UNE OFFRE GENEREE AU PREALABLE
    @PostMapping(uri+"/reservation")
    public Reservation reservation(@RequestParam String Idagence,
                            @RequestParam String Mdpagence,
                            @RequestParam int OffreId,
                            @RequestParam String Client_nom,
                            @RequestParam String Client_prenom)
            throws PartenaireNotFoundException, WrongAgencyException,
            WrongCredentialsException, OfferNotFoundException, AlreadyBookedException, OverlapException, ParseException, YOUDIDSHITBROException {

        Hotel hotel = repository.findAll().get(0);
        Reservation res = hotel.reserver(Idagence, Mdpagence, OffreId, Client_nom, Client_prenom);
        repository.save(hotel);
        System.err.println("\nrepository saved\n");
        return res;
    }

//    @PutMapping(uri+"/hotel/{id}")
//    public Hotel updateHotel(@RequestBody Hotel newHotel,
//                                   @PathVariable long id) {
//        return repository.findById(id)
//                .map(hotel -> {
//                    hotel.setNom(newHotel.getNom());
//                    hotel.setEtoiles(newHotel.getNb_Etoiles());
//                    hotel.setAddress(newHotel.getAdresse());
//                    return repository.save(hotel);
//                })
//                .orElseGet(() -> repository.save(newHotel));
//
//    }

    @ResponseStatus(HttpStatus.NO_CONTENT)

    @DeleteMapping(uri+"/hotel/{id}")
    public void deleteHotel(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(
                        "Error: could not find hotel with ID " + id));
        repository.delete(hotel);
    }
}
