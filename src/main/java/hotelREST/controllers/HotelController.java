package hotelREST.controllers;

import hotelREST.exceptions.HotelNotFoundException;
import hotelREST.exceptions.PartenaireNotFoundException;
import hotelREST.exceptions.WrongCredentialsException;
import hotelREST.models.Chambre;
import hotelREST.models.Hotel;
import hotelREST.models.Offre;
import hotelREST.models.Search;
import hotelREST.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HotelController {
    @Autowired
    private HotelRepository repository;
    private static final String uri = "hotelservice/api";

    /* METHODS */


    // INFOS HOTEL
    @GetMapping(uri+"/hotel")
    public List<Hotel> getAllHotels(){
        return repository.findAll();
    }


    //TOUTES LES CHAMBRES HOTEL
    @GetMapping(uri+"/hotel/chambres")
    public List<Chambre> getChambres() throws HotelNotFoundException {
        return repository.findAll().get(0).getChambres();
    }

    //CHAMBRES DISPONIBLES POUR UN INTERVALLE DE DATE ET UN NB DE CLIENTS
    @GetMapping(uri+"/hotel/chambres_dispo")
    public List<Chambre> getChambres_Disponibles(@RequestParam String date1, @RequestParam String date2, @RequestParam int nbclients) throws HotelNotFoundException, ParseException {
        return repository.findAll().get(0).getChambresLibres(date1, date2, nbclients);
    }

    //CHAMBRES DISPONIBLES POUR UNE RECHERCHE AVANCÉE
    @GetMapping(uri+"/hotel/chambres_dispo")
    public List<Chambre> recherche_Chambre(@RequestParam String date1,
                                           @RequestParam String date2,
                                           @RequestParam(required = false) String ville,
                                           @RequestParam(required = false) Integer nbEtoiles,
                                           @RequestParam(required = false) Integer nbClients,
                                           @RequestParam(required = false) Float prixMin,
                                           @RequestParam(required = false) Float prixMax)
            throws HotelNotFoundException, ParseException {

        Search search = new Search(date1, date2, ville, nbEtoiles, nbClients, prixMin, prixMax);

        return repository.search_room(search).orElseThrow(() -> new HotelNotFoundException(
                "Error: To debug\n param : " +
                        "\n date1 : " + date1 +
                        "\n date2 : " + date2 +
                        "\n adresse : " + ville +
                        "\n nbEtoiles : " + nbEtoiles +
                        "\n nbClients : " + nbClients +
                        "\n prixMin : " + prixMin +
                        "\n prixMax : " + prixMax));
    }

    @ResponseStatus(HttpStatus.CREATED)


    @PostMapping(uri+"/hotel/recherche_offre")
    public List<Offre> recherche_Offre(@RequestParam String Idagence,
                                       @RequestParam String Mdpagence,
                                       @RequestParam String date1,
                                       @RequestParam String date2,
                                       @RequestParam int nbClients)
            throws PartenaireNotFoundException, ParseException, WrongCredentialsException {

        Hotel hotel = repository.findAll().get(0);
        List<Offre> offres = new ArrayList<Offre>();

            offres.addAll(hotel.getOffres(Idagence, Mdpagence, date1, date2, nbClients));

        return offres;
    }

    @PostMapping(uri+"/hotel")
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return repository.save(hotel);
    }

    @PutMapping(uri+"/hotel/{id}")
    public Hotel updateHotel(@RequestBody Hotel newHotel,
                                   @PathVariable long id) {
        return repository.findById(id)
                .map(hotel -> {
                    hotel.setNom(newHotel.getNom());
                    hotel.setEtoiles(newHotel.getNb_Etoiles());
                    hotel.setAddress(newHotel.getAdresse());
                    return repository.save(hotel);
                })
                .orElseGet(() -> repository.save(newHotel));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)

    @DeleteMapping(uri+"/hotel/{id}")
    public void deleteHotel(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(
                        "Error: could not find hotel with ID " + id));
        repository.delete(hotel);
    }
}
