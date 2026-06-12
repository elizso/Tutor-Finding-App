"""Script for generating Tutor objects in json format

COMP2100 Assignment Group 23
The Australian National University
Authors: Lachlan McDonald (u6725813)
Date:    2023 S1

"""

import random
import json

# Define lists of common names, days of the week, and disciplines
first_names = ["Amelia","Charlotte","Isla","Olivia","Ava","Mia","Willow","Matilda","Lily","Ella","Ivy","Sophie","Harper","Ruby","Grace","Evelyn","Isabella","Sophia","Violet","Hazel","Luna","Aurora","Sienna","Evie","Mila","Aria","Freya","Layla","Chloe","Lucy","Penelope","Georgia","Ellie","Florence","Elsie","Daisy","Audrey","Zoe","Frankie","Poppy","Eleanor","Millie","Scarlett","Abigail","Remi","Sadie","Maeve","Isabelle","Sofia","Alice","Mackenzie","Eloise","Zara","Emily","Hallie","Ayla","Lola","Piper","Hannah","Bonnie","Stella","Olive","Harriet","Charlie","Delilah","Billie","Maya","Addison","Summer","Emma","Emilia","Harlow","Savannah","Lilly","Adeline","Peyton","Phoebe","Gracie","Athena","Thea","Rosie","Quinn","Eva","Elizabeth","Eden","Claire","Bella","Rose","Heidi","Pippa","Imogen","Margot","Madeline","Jasmine","Arabella","Sage","Skylar","Maggie","Brooklyn","Hayley","Oliver","Noah","William","Hudson","Henry","Theodore","Leo","Charlie","Jack","Luca","Thomas","Elijah","Archie","Levi","Lucas","Harrison","Hunter","Cooper","James","Archer","Harry","George","Xavier","Arlo","Alexander","Theo","Harvey","Arthur","Oscar","Lachlan","Kai","Hugo","Mason","Liam","Benjamin","Lincoln","Samuel","Max","Sonny","Sebastian","Carter","Finn","Riley","Ethan","Ezra","Austin","Edward","Louis","Patrick","Eli","River","Grayson","Parker","Flynn","Beau","Jackson","Jasper","Isaac","Fletcher","Lennox","Angus","Asher","Darcy","Charles","Roman","Ryder","Koa","Braxton","Spencer","Wyatt","Malakai","Felix","Billy","Maverick","Luka","Hugh","Alfie","Ari","Hamish","Joseph","Vincent","Bodhi","Remy","Reuben","Tyler","Jordan","Ashton","Michael","Leon","Jaxon","Finley","Jacob","Ryan","Phoenix","Oakley","Connor","Elias","Xander","Owen","Louie"]
surnames = ["Smith","Jones","Brown","Williams","Taylor","Wilson","Anderson","Johnson","White","Thompson","Lee","Martin","Walker","Kelly","Thomas","Young","King","Ryan","Harris","Roberts","Hall","Baker","Wright","Evans","Davis","Campbell","Edwards","Clark","Robinson","Clarke","Hill","Scott","Stewart","Mitchell","Moore","Turner","Watson","Green","Miller","Bell","Cooper","Wood","O'brien","Murphy","James","Jackson","Lewis","Bennett","Allen","Robertson","Davies","Collins","Cook","Murray","Ward","Hughes","Johnston","Morris","Phillips","Ross","Gray","Graham","Parker","Adams","Russell","Reid","Kennedy","Morgan","Cox","Marshall","Richardson","Harrison","Simpson","Richards","Carter","Nguyen","Walsh","Rogers","Bailey","Matthews","Thomson","Cameron","Webb","Mcdonald","Chapman","Singh","Ellis","Grant","Hunt","Stevens","Shaw","Butler","Mills","Pearce","Barnes","Harvey","Armstrong","Price","Henderson","Knight","Hamilton","O'connor","Fraser","Fisher","Mason","Hunter","Hayes","Ford","Ferguson","Dunn","Wallace","Gibson","Gordon","Foster","Elliott","Howard","Burns","Woods","Jenkins","Palmer","Reynolds","Griffiths","Holmes","Black","Lloyd","Andrews","Duncan","West","Day","Morrison","Rose","Sullivan","Macdonald","Fletcher","Dawson","Brooks","Powell","Dixon","Watts","Saunders","Crawford","Payne","Byrne","Williamson","Francis","Porter","Davidson","Lawrence","Nelson","Rowe","Cole","Webster","Barker","Perry","Wilkinson","Hart","Kerr","Doyle","Lynch","Fitzgerald","Lane","O'neill","Pearson","Fox","Chen","Stone","Wells","Freeman","Wang","Peters","Carroll","Alexander","Wong","George","Lowe","Li","Spencer","Douglas","Coleman","Stephens","Sutton","May","Boyd","Barrett","Hogan","Atkinson","Brennan","Burke","Dean","Burgess","Patterson","Tran","Bourke","Cross","Berry","Newman","Dwyer","Page","Warren","Fleming","Tan","Gill","Chan","Bradley","Burton","Nicholls","Parsons","Harding","Zhang","Power","Higgins","Holland","Gardner","Oliver","Johnstone","Quinn","Hansen","Lucas","Hudson","Carr","Long","Munro","Curtis","Mckenzie","Bishop","Lawson","Maher","Blake","Newton","Allan","Riley","Stevenson","Sinclair","Shepherd","Cunningham","Nicholson","Owen","Bird","Hawkins","Arnold","Davey","Paterson","Waters","Johns","Walters","Lim","Hammond","Hancock","Little","Mann","Barry","Sharp","Baxter","Gilbert","Sutherland","Willis","Watt","Patel","Hooper","Osborne","Miles","Lyons","Griffin","French","Bryant","Austin","Booth","Fuller","Mackenzie","Kemp","Liu","Marsh","Buckley","Hicks","Kim","Gregory","Chambers","Nolan","Dickson","Cooke","Mackay","O'sullivan","Fowler","Hutchinson","Harper","Bartlett","Tucker","Henry","Read","Jennings","Hardy","Skinner","Craig","Ball","Bond","Goodwin","O'donnell","Gardiner","Kent","Gleeson","Morton","Reed","Jordan","Nash","Fitzpatrick","Mclean","Casey","Farrell","Stephenson","Potter","Mccarthy","Barton","Boyle","Barber","Lindsay","Frost","Muir","Jensen","Bates","Dennis","Gallagher","Browne","Townsend","Brady","Wade","Schmidt","Stuart","Egan","Turnbull","Wheeler","Rees","Briggs","Norris","Muller","Field","Daly","Sanders","Milne","Bruce","Lambert","Perkins","Herbert","Hopkins","Vincent","Atkins","Cullen","Giles","Steele","Hay","Maxwell"]
#days_of_week = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
disciplines = ["Math", "Chemistry", "Physics", "Biology", "History", "English", "Literature"]

#Define weightings for number of days and subjects
numbers = [1,2,3,4,5,6,7]
weightings = [0.6,0.2,0.1,0.05,0.03,0.01,0.01]

# Define a function to generate a tutor object with random attributes
def generate_tutor():
    first_name = random.choice(first_names)
    last_name = random.choice(surnames)
    phone = "0" + "".join([str(random.randint(0, 9)) for _ in range(9)])
    days = random.sample(numbers, random.choices(numbers,weightings)[0])
    subjects = generate_subjects()
    
    return {"first_name": first_name, "last_name": last_name, "phone": phone, "days": days, "subjects": subjects}

def generate_subjects():
    subject_list = []
    for discipline in random.sample(disciplines, random.choices(numbers,weightings)[0]):
        a = random.randint(1,13)
        b = random.randint(1,13)
        for level in range(min(a,b),max(a,b)):
            subject_list.append({"discipline":discipline, "level":str(level)})
    return subject_list

# Generate a list of 500 tutor objects
tutors = [generate_tutor() for _ in range(500)]

# Convert the list of tutor objects to a JSON string
tutors_json = json.dumps(tutors)

# Print the JSON string
print(tutors_json)
