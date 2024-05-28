#Importation des bibliothèques nécessaires ainsi que le fichier "create_famille.py"
import csv
import random
from faker import Faker
import create_famille
fake = Faker('fr_FR')

#On formate notre fichier .csv
def ecrire_csv(nom_fichier, lignes):
    with open(nom_fichier, 'w', newline='', encoding='utf-8-sig') as fichier:
        writer = csv.writer(fichier, delimiter=';')
        for ligne in lignes:
            writer.writerow(ligne)

#On crée le nom des colonnes
arbres_genealogiques = [["ID", "Nom", "Prénom", "Date de Naissance","Date de decès", "Nationalité", "Sexe", "ID père", "ID mère"]]

#On crée la variable que l'on utilisera pour les id des membres de chaque famille
i = 0

#On crée 5 familles que l'on ajoute dans le fichier .csv
for f in range(5):
    family = create_famille.generate_family()
    family['father']['id_paternal_grandfather'] = i 
    family['father']['id_paternal_grandmother'] = i + 1
    family['mother']['id_maternal_grandfather'] = i + 2
    family['mother']['id_maternal_grandmother'] = i + 3
    
    family['father'].update({'id': i + 4})
    family['mother'].update({'id': i + 5})
    for member in family:
        personne=family[member]
        nom_famille = personne['last_name']
        prenom = personne['first_name']
        date_naissance = personne['birth_date']
        nationalite = personne['nationality']
        sexe = personne['sex']
        death = personne['death_date']
        idMere = None
        idPere = None
        if member[0:5:] == 'child':
            idPere = family['father']['id']
            idMere = family['mother']['id']
        elif member == 'father':
            idPere = family['father']['id_paternal_grandfather']
            idMere = family['father']['id_paternal_grandmother']
        elif member == 'mother':
            idPere = family['mother']['id_maternal_grandfather']
            idMere = family['mother']['id_maternal_grandmother']
        arbres_genealogiques.append([i, nom_famille, prenom, date_naissance, death, nationalite, sexe, idPere, idMere])
        i+=1

#On crée le fichier "familles.csv" avec les familles
ecrire_csv("familles.csv", arbres_genealogiques)


