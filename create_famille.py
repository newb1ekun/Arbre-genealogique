from faker import Faker
import random
from datetime import datetime, timedelta

fake = Faker('fr_FR')

def generate_person(sex=None,min_age=1, max_age=90):
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
        'nationality': nationality
    }

def generate_family():
    child = generate_person(min_age=0, max_age=40)  # Enfant âgé de 0 à 17 ans
    
    child_birth_date = datetime.strptime(child['birth_date'], '%Y-%m-%d')
    
    # Générer les dates de naissance des parents dans un intervalle réaliste
    father = generate_person('M', min_age=(18 + (2024 - child_birth_date.year)), max_age=90)
    mother = generate_person('F', min_age=(18 + (2024 - child_birth_date.year)), max_age=90)
    
    father['last_name'] = child['last_name']
    mother['last_name'] = child['last_name']
    
    return {
        'child': child,
        'father': father,
        'mother': mother
    }

for i in range(100):
    family=generate_family()
    print(f"Date de naissance de l'enfant: {family['child']['birth_date']}")
    print(f"Date de naissance du père: {family['father']['birth_date']}")
    print(f"Date de naissance de la mère: {family['mother']['birth_date']}")
    print(" ")