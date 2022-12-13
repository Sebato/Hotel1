package hotelREST.data;

import hotelREST.models.*;
import hotelREST.repositories.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HotelData {

    private Logger logger = LoggerFactory.getLogger(HotelData.class);
    @Bean
    public CommandLineRunner initDataHotel(HotelRepository repository) {
        return args -> {
            String ville1 = "Paris";

            Chambre c1 = new Chambre(1,1, 25);
            Chambre c2 = new Chambre(2,2, 30);
            Chambre c3 = new Chambre(3,3, 35);
            Chambre c4 = new Chambre(4,4, 40);
//            Chambre c5 = new Chambre(5,5, 45);
//            Chambre c6 = new Chambre(6,1, 20);
//            Chambre c7 = new Chambre(7,2, 25);
//            Chambre c8 = new Chambre(8,3, 80);
//            Chambre c9 = new Chambre(9,4, 100);

            Hotel h1 = new Hotel(1, "HotelTest1", ville1, 5);

            h1.addChambre(c1);
            h1.addChambre(c2);
            h1.addChambre(c3);
            h1.addChambre(c4);

            Partenaire p1 = new Partenaire("A0", "Partenaire1", 0.80f);
            Partenaire p2 = new Partenaire("A1", "Partenaire2", 0.92f);
            Partenaire p3 = new Partenaire("A2", "Partenaire3", 0.75f);

            h1.addPartenaire(p1);
            h1.addPartenaire(p2);

            Offre o1 = new Offre(1, "A0", c3, "20-01-2020", "22-01-2020", 70f, 2);

            h1.addOffre(o1);

            logger.info("Preloading database with "
                    +repository.save(h1)
            );
        };
    }
}
