#Importation des bibliothèques nécessaires
from faker import Faker
import random
from datetime import datetime, timedelta, date

#On adapte la bibliothèque faker au français
fake = Faker('fr_FR')

#Cette fonction crée un dictionnaire avec les données d'une personne
def generate_person(sex=None, min_age=1, max_age=90):
    if sex is None:
        sex = random.choice(['M', 'F'])
    
    if sex == 'M':
        first_name = fake.first_name_male()
    else:
        first_name = fake.first_name_female()
    
    last_name = fake.last_name()
    birth_date = fake.date_of_birth(minimum_age=min_age, maximum_age=max_age).strftime('%Y-%m-%d')
    nationality = "France"
    return {
        'first_name': first_name,
        'last_name': last_name,
        'birth_date': birth_date,
        'sex': sex,
        'nationality': nationality,
        'death_date': None
    }

#Cette fonction crée une éventuelle date de decès en prenant en compte la date de naissance
def generate_death_date(birth_date):
    birth_datetime = datetime.strptime(birth_date, '%Y-%m-%d')
    
    if random.random() < 0.8:  
        death_age = random.randint(70, 90)
    else: 
        death_age = random.randint(40, 70)
    
    death_date = birth_datetime + timedelta(days=death_age * 365)
    
    if death_date > datetime.now():
        return None
    return death_date.strftime('%Y-%m-%d')


#Cette fonction gère le status de vie de la personne 
def generate_life_status(birth_date, child_birth_date):
    current_year = datetime.now().year
    birth_year = datetime.strptime(birth_date, '%Y-%m-%d').year
    age = current_year - birth_year
    
    if age > 99:
        death_date = generate_death_date(birth_date)
        return death_date

    if random.random() < 0.7:  
        return None
    
    else:
        death_date = generate_death_date(birth_date)
        if death_date:
            while datetime.strptime(death_date, '%Y-%m-%d') < child_birth_date:
                death_date = generate_death_date(birth_date)
                if not death_date:
                    return None
        return death_date


#Cette fonction crée les grands-parents en prenant en compte l'âge des parents
def generate_grandparents(parent_birth_date):
    grandparent_age_at_parent_birth = random.randint(18, 50)
    parent_birth_datetime = datetime.strptime(parent_birth_date, '%Y-%m-%d')
    base_grandparent_birth_date = parent_birth_datetime - timedelta(days=grandparent_age_at_parent_birth * 365)

    grandfather_birth_date = (base_grandparent_birth_date - timedelta(days=random.randint(0, 365))).strftime('%Y-%m-%d')
    grandmother_birth_date = (base_grandparent_birth_date + timedelta(days=random.randint(366, 730))).strftime('%Y-%m-%d')
    
    grandfather = generate_person('M', min_age=grandparent_age_at_parent_birth, max_age=grandparent_age_at_parent_birth + 50)
    grandmother = generate_person('F', min_age=grandparent_age_at_parent_birth, max_age=grandparent_age_at_parent_birth + 50)
    grandfather['birth_date'] = grandfather_birth_date
    grandmother['birth_date'] = grandmother_birth_date
    
    grandfather_death_date = generate_life_status(grandfather['birth_date'], parent_birth_datetime)
    grandmother_death_date = generate_life_status(grandmother['birth_date'], parent_birth_datetime)

    if grandfather_death_date:
        grandfather['death_date'] = grandfather_death_date
    if grandmother_death_date:
        grandmother['death_date'] = grandmother_death_date
    
    return grandfather, grandmother

#Cette fonction utilise les fonctions précedentes pour créer un dictionnaire qui continent la famille, en prenant en compte les contraintes
def generate_family():
    #On peut avoir entre 0 et 5 enfants
    nbEnfants = random.randint(0, 5)

    childs = []
    distance = 0

    #Les enfants doivent respecter les 9 mois de grossesse
    for _ in range(nbEnfants):
        child = generate_person(min_age=distance, max_age=35)
        childs.append(child)
        distance += 1

    ages = [datetime.strptime(child['birth_date'], '%Y-%m-%d').year for child in childs]
    
    #On vérifie les contraintes d'âge pour les parents
    if ages:
        maxChildAge = min(ages)
        minChildAge = max(ages)
        today = date.today()
        youngest_child_birth_year = minChildAge
        
        max_mother_age_at_youngest_birth = 55
        max_mother_birth_year = youngest_child_birth_year - max_mother_age_at_youngest_birth
        max_mother_age = today.year - max_mother_birth_year
        
        father = generate_person('M', min_age=16 + (today.year - maxChildAge), max_age=90)
        mother = generate_person('F', min_age=16 + (today.year - maxChildAge), max_age=max_mother_age)
    else:
        father = generate_person('M', min_age=18, max_age=90)
        mother = generate_person('F', min_age=18, max_age=90)
    
    for parent in [father, mother]:
        if nbEnfants > 0:
            oldest_child_birth_date = datetime.strptime(childs[0]['birth_date'], '%Y-%m-%d')
        else:
            oldest_child_birth_date = datetime.now()
        parent_death_date = generate_life_status(parent['birth_date'], oldest_child_birth_date)
        if parent_death_date:
            parent['death_date'] = parent_death_date
    
    for child in childs:
        child_death_date = generate_life_status(child['birth_date'], datetime.now())
        if child_death_date:
            child['death_date'] = child_death_date
    
    paternal_grandfather, paternal_grandmother = generate_grandparents(father['birth_date'])
    maternal_grandfather, maternal_grandmother = generate_grandparents(mother['birth_date'])

    #On crée le dictionnaire "family" qui contient les grands-parents et les parents
    family = {
        'paternal_grandfather': paternal_grandfather,
        'paternal_grandmother': paternal_grandmother,
        'maternal_grandfather': maternal_grandfather,
        'maternal_grandmother': maternal_grandmother,
        'father': father,
        'mother': mother,
    }

    #On y rajoute les enfants
    for i, child in enumerate(childs):
        family[f'child{i}'] = child
    
    #On vérifie les noms de familles
    nomFamillePere = paternal_grandfather['last_name']
    nomFamilleMere = maternal_grandfather['last_name']

    for member in family:
        personne = family[member]
        if member == 'father':
            personne['last_name'] = nomFamillePere
        elif member == 'mother':
            personne['last_name'] = nomFamilleMere
        elif member[0:5:1] == 'child':
            personne['last_name'] = nomFamillePere

    return family