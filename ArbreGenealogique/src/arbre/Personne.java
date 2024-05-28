package arbre;

import java.util.List;
import java.time.*;
import java.time.Period;
import java.util.ArrayList;

public class Personne {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private LocalDate dateDeDeces;
    private String nationalite;
    private String sexe;
    private Personne pere;
    private Personne mere;
    private List<Personne> enfants;
    private boolean avoirDiabete;
    
    public Personne() {
    	this.dateDeDeces = null;
    	this.pere = null;
    	this.mere = null;
    	this.enfants = null;
    }

    // Constructeur
    public Personne(int id, String nom, String prenom, LocalDate dateDeNaissance, LocalDate dateDeDeces, String nationalite, String sexe,
                    Personne pere, Personne mere, List<Personne> enfants) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance; // Use setter to apply validation
        this.dateDeDeces = dateDeDeces; 
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


    public LocalDate getDateDeNaissance() {
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

    public LocalDate getDateDeDeces() {
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
    public boolean isAvoirDiabete() {
        return avoirDiabete;
    }

    public void setAvoirDiabete(boolean avoirDiabete) {
        this.avoirDiabete = avoirDiabete;
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
    @Override
    public String toString() {
        return "Personne{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateDeNaissance='" + dateDeNaissance + '\'' +
                ", dateDeDeces='" + dateDeDeces + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", sexe='" + sexe + '\'' +
                ", idPere='" + pere.getId() + '\'' +
                ", idMere='" + mere.getId() + '\'' +
                ", Enfants='" + enfants + '\'' +
                '}';
    }

}
