package hotelREST.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {

    private Long id;
    private String nom;
    private String prenom;
    private String creditCard;

    public Client() {
        this.nom = "TOTO";
        this.prenom = "TOTO";
        this.creditCard = "TOTO";
    }

    public Client(String nom, String prenom, String creditCard) {
        this.nom = nom;
        this.prenom = prenom;
        this.creditCard = creditCard;
    }

    @Id
    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getCreditCard() {
        return creditCard;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setCreditCard(String cc) {
        this.creditCard = cc;
    }
}
