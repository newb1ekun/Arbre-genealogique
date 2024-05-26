import java.time.LocalDate;



public class Diabete {
    private boolean type1;
    private boolean type2;
    private LocalDate dateDiagnostique;
    private Personne personne;

    // Default constructor
    public Diabete() {
    }

    // Parameterized constructor
    public Diabete(boolean type1, boolean type2, LocalDate dateDiagnostique) {
        this.type1 = type1;
        this.type2 = type2;
        this.dateDiagnostique = dateDiagnostique;
        setPersonne(personne);
    }

    // Getters and Setters

    public Personne getPersonne(){
      return personne;
    }
    public void setPersonne(Personne personne){
      if (personne != null && !personne.isAvoirDiabete()) {
        throw new IllegalArgumentException("la personne n'a pas le diabete");}
      this.personne = personne;}
      

      
    public boolean isType1() {
        return type1;
    }

    public void setType1(boolean type1) {
        this.type1 = type1;
    }

    public boolean isType2() {
        return type2;
    }

    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    public LocalDate getDateDiagnostique() {
        return dateDiagnostique;
    }

    public void setDateDiagnostique(LocalDate dateDiagnostique) {
        this.dateDiagnostique = dateDiagnostique;
    }

    @Override
    public String toString() {
        return "Diabete{" +
                "type1=" + type1 +
                ", type2=" + type2 +
                ", dateDiagnostique=" + dateDiagnostique +
                '}';
    }
}
