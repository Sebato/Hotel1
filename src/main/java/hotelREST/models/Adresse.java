package hotelREST.models;

//import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Adresse {

    //@Column(name = "lieu_dit")
    private String lieu_dit;

    //@Column(name = "numero")
    private int numero;

    //@Column(name = "rue")
    private String rue;

    //@Column(name = "ville")
    private String ville;

    //@Column(name = "pays")
    private String pays;

    @Id
    @GeneratedValue
    private Long id;

    public Adresse() {}

    public Adresse(long id, String pays, String ville, String rue, int numero, String lieu_dit) {
        this.pays = pays;
        this.ville = ville;
        this.rue = rue;
        this.numero = numero;
        this.lieu_dit = lieu_dit;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                " lieu_dit='" + lieu_dit + '\'' +
                ", numero=" + numero +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                '}';
    }

    public String getPays() { return pays; }

    public void setPays(String pays) { this.pays = pays; }

    public String getVille() { return ville; }

    public void setVille(String ville) { this.ville = ville; }

    public String getRue() { return rue; }

    public void setRue(String rue) { this.rue = rue; }

    public int getNumero() { return numero; }

    public void setNumero(int numero) { this.numero = numero; }

    public String getLieu_dit() { return lieu_dit; }

    public void setLieu_dit(String lieu_dit) { this.lieu_dit = lieu_dit; }

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }
}