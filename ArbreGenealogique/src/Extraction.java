import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class Extraction {

    public static void main(String[] args) {
        // Le chemin du fichier Excel
        String fileName = "familles.xlsx";
        
        try (FileInputStream fis = new FileInputStream(fileName);
                Workbook workbook = new XSSFWorkbook(fis)) {

        	// Obtenir la première feuille de calcul
            Sheet sheet = workbook.getSheetAt(0);

            // Identifier les colonnes des informations personnelles
            int idc = 0; // Colonne du nom
            int nomc = 1; // Colonne du prénom
            int prenomc = 2; // Colonne de l'adresse e-mail
            int dateDeNaissancec = 3;
            int nationalitec = 4;
            int sexec = 5;
            int merec = 6;
            int perec = 7;
            List<Personne> personnes = new ArrayList<>();
            
            // Parcourir les lignes de la feuille de calcul
            for (Row row : sheet) {
                // Ignorer l'en-tête
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Extraire les données de la personne
                int id = (int) row.getCell(idc).getNumericCellValue();
                String nom = row.getCell(nomc).getStringCellValue();
                String prenom = row.getCell(prenomc).getStringCellValue();
                Date dateDeNaissance = row.getCell(dateDeNaissancec).getDateCellValue();
                String nationalite = row.getCell(nationalitec).getStringCellValue();
                String sexe = row.getCell(sexec).getStringCellValue();
                int pereid = (int) row.getCell(perec).getNumericCellValue();
                int mereid = (int) row.getCell(merec).getNumericCellValue();
                Personne pere = new Personne();
                Personne mere = new Personne();
                for (Personne personne : personnes) {
                	if (personne.getId() == pereid) {
                		pere = personne;
                	}
                	if (personne.getId() == mereid) {
                		mere = personne;
                	}
                }
                List<Personne> enfants = new ArrayList<>();
                
                
                // Créer un objet Personne
                Personne personneajoute = new Personne(id, nom, prenom, dateDeNaissance,dateDeNaissance, nationalite, sexe, pere, mere, enfants);
                personnes.add(new Personne(id, nom, prenom, dateDeNaissance,dateDeNaissance, nationalite, sexe, pere, mere, enfants));

                // Traiter ou enregistrer l'objet Person
                System.out.println(personneajoute); // Afficher la personne à l'écran
                
                for (Personne personne : personnes) {
                	if (personne.getId() == pereid) {
                		personne.ajoutEnfants(personneajoute);
                	}
                	if (personne.getId() == mereid) {
                		personne.ajoutEnfants(personneajoute);
                	}
                }
                
            }
            System.out.println("Reading File Completed.");

           } catch (IOException e) {
               e.printStackTrace();
           }
    }
}