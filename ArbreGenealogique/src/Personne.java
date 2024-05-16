
import java.util.List;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String sexe;
    private Date dateDeNaissance;
    private Date dateDeDeces;
    private Personne pere;
    private Personne mere;
    private List<Personne> enfants;

    // Constructeur
    public Personne(int id, String nom, String prenom, String sexe, Date dateDeNaissance, Date dateDeDeces,
                    Personne pere, Personne mere, List<Personne> enfants) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.dateDeDeces = dateDeDeces;
        this.pere = pere;
        this.mere = mere;
        this.enfants = enfants;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Date getDateDeDeces() {
        return dateDeDeces;
    }

    public void setDateDeDeces(Date dateDeDeces) {
        this.dateDeDeces = dateDeDeces;
    }

    public Personne getPere() {
        return pere;
    }

    public void setPere(Personne pere) {
        this.pere = pere;
    }

    public Personne getMere() {
        return mere;
    }

    public void setMere(Personne mere) {
        this.mere = mere;
    }

    public List<Personne> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Personne> enfants) {
        this.enfants = enfants;
    }
}
