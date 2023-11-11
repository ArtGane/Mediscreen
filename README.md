# Mediscreen
Application pour détecter les signaux avant-coureur du diabète

Pour faire fonctionner l'application, il vous suffit de cloner le projet sur votre poste de travail.

Il faut ensuite vérifier la configuration de chaque API.
Il faut donc se rendre sur chaque module, grâce au terminal de l'IDE (ainsi que la commande cd), puis envoyer une commande "mvn clean install".

Vous pouvez maintenant démarrer les containers docker depuis la racine du projet Mediscreen.
Entrer les commandes l'une après l'autre dans le terminal de votre IDE : - docker compose build 
                                                                         - docker compose up

Maintenant que votre application est démarrée, il vous faut maintenant rentrer les données, grâce à ces curl vous pouvez dès à présent enregistrer les dix premiers patients :

curl -d "family=Ferguson&given=Lucas&dob=1968-06-22&sex=M&address=2 Warren Street&phone=387-866-1399" -X POST http://localhost:8081/patient/add

curl -d "family=Rees&given=Pippa&dob=1952-09-27&sex=F&address=745 West Valley Farms Drive&phone=628-423-0993" -X POST http://localhost:8081/patient/add

curl -d "family=Arnold&given=Edward&dob=1952-11-11&sex=M&address=599 East Garden Ave&phone=123-727-2779" -X POST http://localhost:8081/patient/add

curl -d "family=Sharp&given=Anthony&dob=1946-11-26&sex=M&address=894 Hall Street&phone=451-761-8383" -X POST http://localhost:8081/patient/add

curl -d "family=Ince&given=Wendy&dob=1958-06-29&sex=F&address=4 Southampton Road&phone=802-911-9975" -X POST http://localhost:8081/patient/add

curl -d "family=Ross&given=Tracey&dob=1949-12-07&sex=F&address=40 Sulphur Springs Dr&phone=131-396-5049" -X POST http://localhost:8081/patient/add

curl -d "family=Wilson&given=Claire&dob=1966-12-31&sex=F&address=12 Cobblestone St&phone=300-452-1091" -X POST http://localhost:8081/patient/add

curl -d "family=Buckland&given=Max&dob=1945-06-24&sex=M&address=193 Vale St&phone=833-534-0864" -X POST http://localhost:8081/patient/add

curl -d "family=Clark&given=Natalie&dob=1964-06-18&sex=F&address=12 Beechwood Road&phone=241-467-9197" -X POST http://localhost:8081/patient/add

curl -d "family=Bailey&given=Piers&dob=1959-06-28&sex=M&address=1202 Bumble Dr&phone=747-815-0557" -X POST http://localhost:8081/patient/add


Suite aux patients nous pouvons maintenant rajouter les notes de chaque patient : 

curl -d "patId=1&e=Patient: Ferguson Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=2&e=Patient: Rees Practitioner's notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late" -X POST http://localhost:8082/patHistory/add

curl -d "patId=2&e=Patient: Rees Practitioner's notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic" -X POST http://localhost:8082/patHistory/add

curl -d "patId=3&e=Patient: Arnold Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=4&e=Patient: Sharp Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=5&e=Patient: Ince Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=6&e=Patient: Ross Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=7&e=Patient: Wilson Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=8&e=Patient: Buckland Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=9&e=Patient: Clark Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add

curl -d "patId=10&e=Patient: Bailey Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level" -X POST http://localhost:8082/patHistory/add


Voilà, votre application est fonctionnelle et bien remplie ! :)
