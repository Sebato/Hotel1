package hotelREST.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Partenaire {
    @Id
    private String identifiant;

    private String motDePasse;

    private float pourcentage;

    public Partenaire(String identifiant, String motDePasse, float pourcentage) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.pourcentage = pourcentage;
    }

    public Partenaire(String identifiant, String motDePasse) {
        this(identifiant, motDePasse, 1);
    }

    public Partenaire() {

    }

    public String getIdentifiant() {
        return identifiant;
    }

    public float getPourcentage() {
        return pourcentage;
    }

    public boolean checkMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }
}
