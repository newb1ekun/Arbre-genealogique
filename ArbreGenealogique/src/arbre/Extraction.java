package arbre;


import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.CSVReader;
import java.time.format.*;
import java.time.*;
import java.text.*;

public class Extraction {
    // Map pour stocker les personnes par leur identifiant
    public static Map<Integer, Personne> personnesMap= new HashMap<>();
    
    //Map pour stocker à quelle famille chaque individu appartient
    public static List<List<Integer>> familles = new ArrayList<>();

    
    public static void main(String[] args) {
    	Extraction.ExcelExtraction();
    	/*Personne personne1 = new Personne(100, "Doe", "John", LocalDate.of(1940, 1, 1), null, "Américaine", "M", null, null, null);
    	Extraction.AjoutPersonne(personne1);
    	Personne personne2 = new Personne(101, "Doe", "John", LocalDate.of(1900, 1, 1), null, "Américaine", "M", personne1, null, null);
    	Extraction.AjoutPersonne(personne2);
    	Extraction.SupprimerPersonne(personne2);
    	Extraction.SupprimerPersonne(personne1);*/
    }

    public static void ExcelExtraction() {
    	// Le chemin du fichier Excel
    	String excelFile = "H:/CY TECH/ING1/S2/Java/Java code/ArbreGenealogique/familles.csv";

    	try (FileReader fis = new FileReader(excelFile); CSVReader csvreader = new CSVReader(fis, ';')) {
        //Saute la première ligne
        csvreader.readNext();
        
        List<String[] > data = csvreader.readAll();	



        for (String[] oneData : data) {
            String idStr = oneData[0];
            Integer id = Integer.parseInt(idStr);
            
            String nom = oneData[1];
            
            String prenom = oneData[2];
            
            
            String dateDeNaissanceStr = oneData[3];
            LocalDate dateDeNaissance = LocalDate.parse(dateDeNaissanceStr, DateTimeFormatter.ISO_LOCAL_DATE);
            
            LocalDate dateDeDeces = null;
            if (!oneData[4].isEmpty()) {
                dateDeDeces = LocalDate.parse(oneData[4], DateTimeFormatter.ISO_LOCAL_DATE);
            }
            
            String nationalite = oneData[5];
            
            String sexe = oneData[6];
            
            Personne pere = null;
            int pereid = -1;
            if (!oneData[7].isEmpty()) {
            	String pereStr = oneData[7];
            	pereid = Integer.parseInt(pereStr);
            	pere = personnesMap.get(pereid);
            }
            
            Personne mere = null;
            int mereid = -1;
            if (!oneData[8].isEmpty()) {
            	String mereStr = oneData[8];
            	mereid = Integer.parseInt(mereStr);
            	mere = personnesMap.get(mereid);
            }
            
            List<Personne> enfants = new ArrayList<>();
            
            Personne personneajoute = new Personne(id, nom, prenom, dateDeNaissance, dateDeDeces, nationalite,sexe , pere, mere, enfants);
            
            Extraction.personnesMap.put(id, personneajoute);
               
            
            if (personnesMap.get(pereid) != null) {
            	personnesMap.get(pereid).ajoutEnfants(personneajoute);
            }
            if (personnesMap.get(mereid) != null) {
            	personnesMap.get(mereid).ajoutEnfants(personneajoute);
            }

            if (pereid==-1 && mereid==-1) {
            	List<Integer> famille = new ArrayList<>();
            	famille.add(id);
                familles.add(famille);
            }
            else if (pereid==-1) {
                for (List<Integer> list : Extraction.familles) {
                    if (list.contains(mereid)) {
                    	list.add(id);
                    }
                }
            }
            else if (mereid==-1) {
            	for (List<Integer> list : Extraction.familles) {
                    if (list.contains(pereid)) {
                        list.add(id);
                    }
                }
            }
            else {
            	List<Integer> famillemere = new ArrayList<>();
            	for (int i = 0; i < Extraction.familles.size(); i++) {
                	if (Extraction.familles.get(i).contains(mereid) && 
                			Extraction.familles.get(i).contains(pereid) && 
                			Extraction.personnesMap.get(Extraction.familles.get(i).get(Extraction.familles.get(i).size()-1)).getPere().getId() == pereid &&
                			Extraction.personnesMap.get(Extraction.familles.get(i).get(Extraction.familles.get(i).size()-1)).getMere().getId() == mereid
                			) {
                    }
                	else if (Extraction.familles.get(i).contains(mereid)) {
                        famillemere = Extraction.familles.get(i);
                        Extraction.familles.remove(i);
                    }
                }
            	for (int i = 0; i < Extraction.familles.size(); i++) {
                    if (Extraction.familles.get(i).contains(pereid)) {
                    	Extraction.familles.get(i).addAll(famillemere);
                    	Extraction.familles.get(i).add(id);
                    }
                }
            }   
            System.out.println(personneajoute.getId());  
            }
        System.out.println("Reading File Completed.");  

        } catch (IOException e) {
            System.out.println("ExcelExtraction catch block");
            e.printStackTrace();
        }
    }
    
    public static void generateExcel(Map<Integer, Personne> personnesMap) {
        try (FileWriter writer = new FileWriter("H:/CY TECH/ING1/S2/Java/Java code/ArbreGenealogique/familles.csv", false)) {
            // Écrire l'en-tête du fichier CSV
            writer.write("ID;Nom;Prénom;Date de Naissance;Date de décès;Nationalité;Sexe;ID père;ID mère\n");
            
            // Parcourir la Map et écrire chaque entrée dans le fichier CSV
            for (Map.Entry<Integer, Personne> entry : personnesMap.entrySet()) {
                Personne personne = entry.getValue();
                // Formateur pour le format "yyyy-MM-dd"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateNaissance = personne.getDateDeNaissance().format(formatter);
                String dateDeces = "";
                if (personne.getDateDeDeces() != null) {
                	dateDeces = personne.getDateDeDeces().format(formatter);
                }
                String pere = "";
                if (personne.getPere() != null) {
                	pere = String.valueOf(personne.getPere().getId());
                }
                String mere = "";
                if (personne.getMere() != null) {
                	pere = String.valueOf(personne.getMere().getId());
                }
                String line = personne.getId() + ";" +
                              personne.getNom() + ";" +
                              personne.getPrenom() + ";" +
                              dateNaissance + ";" +
                              dateDeces + ";" +
                              personne.getNationalite() + ";" +
                              personne.getSexe() + ";" +
                              pere + ";" +
                              mere + "\n";
                writer.write(line);
                System.out.println(line); 
            }
            writer.close();
            System.out.println("fichier cree avec succes");
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }

    
    public static void AjoutPersonne(Personne personne) {
    	Map<Integer, Personne> personnesMap = Extraction.personnesMap;
		
		//boolean qui voit si les parents sont de la même famille
		boolean memeFamille = false;
		if (personne.getMere() != null && personne.getPere() != null) {
			for (List<Integer> list : Extraction.familles) {
				if (list.contains(personne.getMere().getId()) && list.contains(personne.getPere().getId())) {
					memeFamille = true;
				}
			}
		}
		//boolean qui voit si la naissance de l'enfant n'est pas trop pres de ses freres
		boolean naissable = true;
		if (personne.getMere() != null) {
		//Liste des freres de la meme mere
		List<Personne> freres = new ArrayList<>();
		for (Map.Entry<Integer, Personne> entry : Extraction.personnesMap.entrySet()) {
				if (entry.getValue().getMere().equals(personne.getMere())) {
					freres.add(entry.getValue());
				}
	    	}
			for (Personne frere : freres) {
				if (!frere.getDateDeNaissance().isEqual(personne.getDateDeNaissance()) || !frere.getDateDeNaissance().isAfter(personne.getDateDeNaissance().plusYears(1)) || !frere.getDateDeNaissance().isBefore(personne.getDateDeNaissance().minusYears(1))) {
					memeFamille = false;
				}
			}
		}
		//boolean qui voit si la naissance de l'enfant n'est pas apres le deces de ses parents
		boolean naissablep = true;
		boolean naissablem = true;
		//boolean qui voit il a un pere et une mere
		boolean perepresent = true;
		boolean merepresent = true;
		//boolean qui voit si enfant nees apres que les parents ont 16 ans
		boolean perenes = true;
		boolean merenes = true;
		//boolean qui vérifie menopause mere
		boolean menopause = true;
		
		if (personne.getPere() != null) {
			if (Extraction.personnesMap.get(personne.getPere().getId()).getDateDeDeces() != null
					&& Extraction.personnesMap.get(personne.getPere().getId()).getDateDeDeces().isBefore(personne.getDateDeNaissance())
				) {
				naissablep =false;
			}
			perepresent = Extraction.personnesMap.containsKey(personne.getPere().getId());
			perenes = Extraction.personnesMap.get(personne.getPere().getId()).getDateDeNaissance().plusYears(16).isBefore(personne.getDateDeNaissance());
		}
		if (personne.getMere() != null) {
			if (!Extraction.personnesMap.get(personne.getMere().getId()).getDateDeDeces().equals(null)
					&& Extraction.personnesMap.get(personne.getMere().getId()).getDateDeDeces().isBefore(personne.getDateDeNaissance())
					) {
				naissablem =false;
			}
			merepresent = Extraction.personnesMap.containsKey(personne.getMere().getId());
			merenes = Extraction.personnesMap.get(personne.getMere().getId()).getDateDeNaissance().plusYears(16).isBefore(personne.getDateDeNaissance());
			menopause = Extraction.personnesMap.get(personne.getMere().getId()).getDateDeNaissance().plusYears(55).isBefore(personne.getDateDeNaissance());
		}
		
		//boolean qui verifie ne depasse pas 100 ans
		boolean age = true;
		if (personne.getDateDeDeces() != null) {
			age = personne.getDateDeNaissance().plusYears(100).isAfter(personne.getDateDeDeces());
		}
		if (//conditions pour que le personnes soit ajoutable
			//identifiants uniques
			!Extraction.personnesMap.containsKey(personne.getId()) &&
			//identifiants peres et meres connus
			perepresent && merepresent &&
			//enfants nees apres que les parents ont 16 ans
			perenes && merenes &&
			//verifie parents pas meme famille et distance naissance entre freres soeurs
			!memeFamille && naissable &&
			//verifie menopause de la mere
			menopause &&
			//verifie si les parents sont en vie a la naissance
			naissablep && naissablem &&
			//age maximal de 100 ans
			age
				) 
				{
			personnesMap.put(personne.getId(), personne);
			System.out.println("ajout réussi");
			if (personne.getPere() == null && personne.getMere() == null) {
            	Extraction.familles.add(Arrays.asList(personne.getId()));
            }
            else if (personne.getPere() == null) {
                for (List<Integer> list : Extraction.familles) {
                    if (list.contains(personne.getMere().getId())) {
                    	list.add(personne.getId());
                    }
                }
            }
            else if (personne.getMere() == null) {
                for (List<Integer> list : Extraction.familles) {
                    if (list.contains(personne.getPere().getId())) {
                    	list.add(personne.getId());
                    }
                }
            }
            else {
            	List<Integer> famillemere = new ArrayList<>();
            	for (List<Integer> list : Extraction.familles) {
            		if (list.contains(personne.getMere().getId())) {
                    	famillemere = list;
                    	list.clear();
                    }
            	}
            	for (List<Integer> list : Extraction.familles) {
                    if (list.contains(personne.getMere().getId())) {
                    	list.addAll(famillemere);
                    	list.add(personne.getId());
                    }
                }
            }
			System.out.println("Ajout réussi");

		}
		else {System.out.println("Ajout rate");
		}
		Extraction.personnesMap = personnesMap;
		//Création nouveau fichier excel
		Extraction.generateExcel(personnesMap);
    }

    public static void SupprimerPersonne(Personne personne) {
    	if (!Extraction.personnesMap.containsKey(personne.getId())) {
    		System.out.println("Personne non presente dans la base de donnée");
    		return;
    	}
    	else {
    		Extraction.personnesMap.remove(personne.getId());
    		for (Map.Entry<Integer, Personne> entry : personnesMap.entrySet()) {
                Personne pers = entry.getValue();
                if (pers.getEnfants().contains(personne)) {
                	pers.getEnfants().remove(personne);
                }
                if (pers.getPere() != null && pers.getPere().equals(personne)) {
                	pers.setPere(null);
                }
                if (pers.getMere() != null && pers.getMere().equals(personne)) {
                	pers.setMere(null);
                }
    		}
    		System.out.println("Suppression reussi");
    		
    		Extraction.generateExcel(Extraction.personnesMap);

    	}
    }
    
    public static List<Personne> SansAscendant() {
    	List<Personne> sansAscendant = new ArrayList<>();
    	for (Map.Entry<Integer, Personne> entry : personnesMap.entrySet()) {
    		if (entry.getValue().getPere()==null && entry.getValue().getMere() == null) {
    			sansAscendant.add(entry.getValue());
    		}
    	}
    	return sansAscendant;
    }
    
    
    public static List<Personne> Descendance(Personne personne){
    	List<Personne> descendance = personne.getEnfants();
    	int aparcouru = 0;
    	while (aparcouru!=descendance.size()) {
    		if (descendance.get(aparcouru).getEnfants() != null) {
    			descendance.addAll(descendance.get(aparcouru).getEnfants());
    		}
   			aparcouru++;
    	}
    	return descendance;
    }
    
    public static List<Personne> Ascendance(Personne personne){
    	List<Personne> ascendance = personne.getEnfants();
    	int aparcouru = 0;
    	while (aparcouru!=ascendance.size()) {
    		if (ascendance.get(aparcouru).getPere() != null) {
    			ascendance.add(ascendance.get(aparcouru).getPere());
    		}
    		if (ascendance.get(aparcouru).getMere() != null) {
    			ascendance.add(ascendance.get(aparcouru).getMere());
    		}
    		aparcouru++;
    	}
    	return ascendance;
    }
    
    public static List<Personne> FreresSoeurs(Personne personne){
    	List<Personne> freresSoeurs = new ArrayList<>();
    	List<Integer> familleappartient = new ArrayList<>();
    	for (List<Integer> famille : Extraction.familles) {
    		if (famille.contains(personne.getId())) {
    			familleappartient = famille;
    			break;
    		}
    	}
    	for (Integer id : familleappartient) {
    		if (id != personne.getId()) {
    			if (Extraction.personnesMap.get(id).getPere() != null
    					&& Extraction.personnesMap.get(id).getPere() == personne.getPere()){
    				freresSoeurs.add(Extraction.personnesMap.get(id));
    			}
    			else if (Extraction.personnesMap.get(id).getMere() != null
    					&& Extraction.personnesMap.get(id).getMere() == personne.getMere()){
    				freresSoeurs.add(Extraction.personnesMap.get(id));
    			}
    		}

    	}
    	return freresSoeurs;
    }
}
