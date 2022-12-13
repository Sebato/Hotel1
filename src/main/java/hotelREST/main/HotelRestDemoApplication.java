package hotelREST.main;

import hotelREST.models.Hotel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "hotelREST.models"
})
@EnableJpaRepositories(basePackages = {
        "hotelREST.repositories"
})
@SpringBootApplication(scanBasePackages = {
        "hotelREST.data",
        "hotelREST.exceptions",
        "hotelREST.controllers"
})
public class HotelRestDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(hotelREST.main.HotelRestDemoApplication.class, args);
        System.out.println("Hotel Service online; Uri : http://localhost:8080/hotelservice/api");
    }

}

