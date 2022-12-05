package hotelREST.repositories;

import hotelREST.models.Chambre;
import hotelREST.models.Hotel;
import hotelREST.models.Search;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {


    default Optional<List<Chambre>> search_room(Search s) throws ParseException {

        String date1 = s.getDate1();
        String date2 = s.getDate2();
        String ville = s.getVille();
        Integer nbEtoiles = s.getNbEtoiles();
        Integer nbClients = s.getNbClients();
        Float prixMin = s.getPrixMin();
        Float prixMax = s.getPrixMax();


        ArrayList<Chambre> chambresDispo = new ArrayList<>();
        List<Hotel> hotels = this.findAll();
        List<Hotel> hotels_filtered = new ArrayList<>(hotels);

        for (Hotel h : hotels) {
            if (ville != null ) {
                if (!h.getAdresse().getVille().equalsIgnoreCase(ville) ) {
                    hotels_filtered.remove(h);
                }
            };
            if (nbEtoiles != null) {
                if (h.getNb_Etoiles() != nbEtoiles) {
                    hotels_filtered.remove(h);
                }
            }
        }
        //System.err.println("hotels_filtered " + hotels_filtered);

        for (Hotel h : hotels_filtered) {
            chambresDispo.addAll(h.getChambresLibres(date1, date2, nbClients));
        }
        ArrayList<Chambre> chambresFilter = new ArrayList<>(chambresDispo);

        for (Chambre c : chambresDispo) {
            if (prixMin != null) {
                if (c.getPrixSejour(date1,date2) < prixMin) {
                    chambresFilter.remove(c);
                }
            }
            if (prixMax != null) {
                if (c.getPrixSejour(date1,date2) > prixMax) {
                    chambresFilter.remove(c);
                }
            }
        }
        return Optional.of(chambresFilter);
    }
}