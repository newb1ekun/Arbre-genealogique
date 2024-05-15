import csv
import random
from faker import Faker
import create_famille

def ecrire_csv(nom_fichier, lignes):
    with open(nom_fichier, 'w', newline='', encoding='utf-8-sig') as fichier:
        writer = csv.writer(fichier, delimiter=';')
        for ligne in lignes:
            writer.writerow(ligne)

fake = Faker('fr_FR')
arbres_genealogiques = [["ID", "Nom", "Prénom", "Date de Naissance", "Nationalité", "Sexe", "ID mère", "ID père"]]

family = create_famille.generate_family()
i = 0

for f in range(1,11):
    family = create_famille.generate_family()
    for member in family:
        personne=family[member]
        nom_famille = personne['last_name']
        prenom = personne['first_name']
        date_naissance = personne['birth_date']
        nationalite = personne['nationality']
        sexe = personne['sex']
        id_mere = 0
        id_pere = 0
        i+=1
        if str(member) == "child":
            id_mere = i+2
            id_pere = i+1
        else:
            id_mere = None
            id_pere = None
        arbres_genealogiques.append([i, nom_famille, prenom, date_naissance, nationalite, sexe, id_mere, id_pere])


ecrire_csv("familles.csv", arbres_genealogiques) 