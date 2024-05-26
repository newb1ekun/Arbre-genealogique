import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;


public class Extraction {
	public static void main(String[] args) {
		Extraction obj = new Extraction();
		obj.ExcelExtraction();
	}

    public String ExcelExtraction() {
        // Le chemin du fichier Excel
        String excelFile = "familles.xlsx";
        try (FileInputStream fis = new FileInputStream(excelFile); Workbook workbook = WorkbookFactory.create(fis)) {
        	
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
            for (int rowIndex = 0; rowIndex < 10; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                // Ignorer l'en-tête
                if (row.getRowNum() == 0) {
                    continue;
                }
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                

                // Extraire les données de la personne
                Personne personneajoute = new Personne();
                
                int id = (int) row.getCell(idc).getNumericCellValue();
                personneajoute.setId(id);
                
                String nom = row.getCell(nomc).getStringCellValue();
                personneajoute.setNom(nom);
                
                String prenom = row.getCell(prenomc).getStringCellValue();
                personneajoute.setPrenom(prenom);
                
                String dateDN = row.getCell(dateDeNaissancec).getStringCellValue();
                LocalDate dateDeNaissance = null;
                try {
                    // Convertir la chaîne de caractères en LocalDate
                    dateDeNaissance = LocalDate.parse(dateDN, formatter);
                    
                    // Afficher la date convertie
                    System.out.println("Date convertie (java.time.LocalDate) : " + dateDeNaissance);
                } catch (DateTimeParseException e) {
                    System.err.println("Erreur de format de date : " + e.getMessage());
                }
                personneajoute.setDateDeNaissance(dateDeNaissance);
                
                personneajoute.setDateDeDeces(dateDeNaissance);
                
                String nationalite = row.getCell(nationalitec).getStringCellValue();
                personneajoute.setNationalite(nationalite);
                
                String sexe = row.getCell(sexec).getStringCellValue();
                personneajoute.setSexe(sexe);

                int pereid = (int) row.getCell(perec).getNumericCellValue();
                int mereid = (int) row.getCell(merec).getNumericCellValue();
                
                for (Personne personne : personnes) {
                	if (personne.getId() == pereid) {
                		personneajoute.setPere(personne);
                	}
                	if (personne.getId() == mereid) {
                		personneajoute.setMere(personne);
                	}
                }
                
                List<Personne> enfants = new ArrayList<Personne>();
                personneajoute.setEnfants(enfants);
                
               /*
                // Créer un objet Personne
                Personne personneajoute = new Personne(id, nom, prenom, dateDeNaissance, dateDeNaissance, nationalite, sexe, pere, mere, enfants);
                personnes.add(personneajoute);*/

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
            workbook.close();
            System.out.println("Reading File Completed.");
            

           } catch (IOException e) {
        	   System.out.println("ExcelExtraction catch block");
               e.printStackTrace();
           }
        return "test";
    }
}
