
import java.util.*;
import java.util.List;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private Date dateDeNaissance;
    private Date dateDeDeces;
    private String nationalite;
    private String sexe;
    private Personne pere;
    private Personne mere;
    private List<Personne> enfants;
    
    public Personne() {
    }

    // Constructeur
    public Personne(int id, String nom, String prenom, Date dateDeNaissance, Date dateDeDeces, String nationalite, String sexe,
                    Personne pere, Personne mere, List<Personne> enfants) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        setDateDeNaissance(dateDeNaissance); // Use setter to apply validation
        setDateDeDeces(dateDeDeces); 
        this.nationalite = nationalite;
        this.sexe = sexe;
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


    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        if (dateDeNaissance.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur");
        }
         if (Period.between(dateDeNaissance, LocalDate.now()).getYears() > 120) {
            throw new IllegalArgumentException("L'âge ne peut pas dépasser 120 ans");
        }
        this.dateDeNaissance = dateDeNaissance;
    }

    public Date getDateDeDeces() {
        return dateDeDeces;
    }

    public void setDateDeDeces(LocalDate dateDeDeces) {
        if (dateDeDeces != null && dateDeNaissance != null && dateDeDeces.isBefore(dateDeNaissance)) {
            throw new IllegalArgumentException("La date de décès ne peut pas être avant la date de naissance");
        }
        this.dateDeDeces = dateDeDeces;
    }
    
    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
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
    
   public void ajoutEnfants(Personne enfant) {
        if (enfant == null) {
            throw new IllegalArgumentException("L'enfant ne peut pas être null");
        }

        int ageParent = calculateAge(this.dateDeNaissance, LocalDate.now());
        int ageEnfant = calculateAge(enfant.getDateDeNaissance(), LocalDate.now());

        if (ageEnfant >= (ageParent+15)) {
            throw new IllegalArgumentException("L'âge de l'enfant doit être inférieur à celui du parent");
        }

        if (this.enfants == null) {
            this.enfants = new ArrayList<>();
        }
        this.enfants.add(enfant);
    }

    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate != null && currentDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
