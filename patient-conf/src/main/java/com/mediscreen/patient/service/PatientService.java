package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.UnknowPatient;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    PatientRepository patientRepository;

    /**
     * Méthode permettant de créer un nouveau patient ou de le mettre à jour en le sauvegardant dans le référentiel (repository) des patients.
     *
     * @param patient Objet de type Patient représentant les informations du patient à créer.
     */
    public void createOrUpdatePatient(Patient patient) {
        if (patient != null) {
            patientRepository.save(patient);
            logger.info("Patient enregistré en base de données");
        } else {
            logger.error("Le patient n'a pas pu être enregistré en bdd parce qu'il est null");
        }
    }

    /**
     * Méthode permettant de supprimer un patient existant du référentiel des patients en utilisant son identifiant unique.
     *
     * @param id Identifiant unique (Long) du patient à supprimer.
     */
    public void deletePatient(Long id) {
        if (id != null) {
            Patient patient = patientRepository.getReferenceById(id);
            patientRepository.delete(patient);
            logger.info("Patient supprimé");
        } else {
            logger.error("ID du patient null");
        }
    }


    /**
     * Méthode permettant d'obtenir la liste de tous les patients enregistrés dans le référentiel.
     *
     * @return Une liste (List) d'objets Patient contenant tous les patients enregistrés.
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            logger.error("Il n'y a aucun patient en base de données");
        }

        return patients;
    }

    /**
     * Méthode permettant de récupérer un patient spécifique du référentiel en utilisant son identifiant unique.
     *
     * @param id Identifiant unique (Long) du patient recherché.
     * @return Un objet Patient représentant les informations du patient correspondant à l'identifiant unique donné.
     */
    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if (!patient.isPresent()) {
            logger.error("Ce patient ou son ID n'existe pas en base de données");
        }
        return patient.get();
    }

    /**
     * Récupère un patient en fonction de son nom et de son prénom.
     *
     * @param lastname  Le nom de famille du patient.
     * @param firstname Le prénom du patient.
     * @return Un objet Patient représentant les informations du patient correspondant au nom de famille et au prénom donnés.
     */
    public Patient getPatientByCompleteName(String lastname, String firstname) throws UnknowPatient {
        if (!getPatientsByLastname(lastname).isEmpty() && !firstname.isEmpty()) {
            Patient patient = getPatientsByLastname(lastname).stream()
                    .filter(p -> p.getGiven().equalsIgnoreCase(firstname))
                    .findAny().orElseThrow();

            return patient;
        } else {
            throw new UnknowPatient("Il n'y a aucun patient à ce nom et/ou à ce prénom");
        }
    }

    /**
     * Récupère une liste de patients en fonction de leur nom de famille.
     *
     * @param lastname Le nom de famille des patients recherchés.
     * @return Une liste (List) d'objets Patient contenant les patients correspondant au nom de famille donné.
     */
    public List<Patient> getPatientsByLastname(String lastname) throws UnknowPatient {
        if (lastname.isEmpty()) {
            logger.error("Aucun patient avec ce nom de famille en base de données");
        }
        return patientRepository.findPatientByFamily(lastname);
    }

    /**
     * Récupère une liste de patients dont l'âge est supérieur à l'âge requis.
     *
     * @param ageRequired L'âge requis pour filtrer les patients.
     * @return Une liste (List) d'objets Patient contenant les patients dont l'âge est supérieur à l'âge requis.
     */
    public List<Patient> getPatientsByAgeOverAgeRequired(int ageRequired) {
        List<Patient> patients = new ArrayList<>();

        for (Patient patient : getAllPatients()) {
            if (patient.getAge() > ageRequired) {
                patients.add(patient);
            }
        }

        return patients;
    }

    /**
     * Récupère une liste de patients en fonction du genre (sexe) spécifié.
     *
     * @param gender Le genre (sexe) des patients recherchés. Doit être "F" pour féminin ou "M" pour masculin.
     * @return Une liste (List) d'objets Patient contenant les patients du genre (sexe) spécifié.
     * Si le genre (sexe) n'est pas "F" ou "M", la liste sera vide.
     */
    public List<Patient> getOneGenderListOfPatients(String gender) {
        List<Patient> genderList;
        switch (gender) {
            case "F":
                genderList = getAllPatients().stream().filter(p -> p.getSex().equalsIgnoreCase("F")).toList();
                break;
            case "M":
                genderList = getAllPatients().stream().filter(p -> p.getSex().equalsIgnoreCase("M")).toList();
                break;
            default:
                genderList = new ArrayList<>();
                logger.error("Nous sommes désolés mais vous devez choisir entre masculin et féminin");
                break;
        }

        return genderList;
    }

}
