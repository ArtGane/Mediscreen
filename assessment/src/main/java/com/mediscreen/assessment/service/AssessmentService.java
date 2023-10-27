package com.mediscreen.assessment.service;

import com.mediscreen.assessment.dto.NoteDto;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.model.Level;
import com.mediscreen.assessment.model.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AssessmentService {

    private Logger logger = LoggerFactory.getLogger(AssessmentService.class);
    @Autowired
    private PatientFeign proxyPatient;
    @Autowired
    private NoteFeign proxyNotes;

    /**
     * Récupère l'évaluation pour le patient ayant l'identifiant donné.
     *
     * @param patId L'identifiant du patient.
     * @return L'évaluation du patient.
     */
    public Assessment getAssessmentByPatientId(Long patId) {
        Level level;
        logger.info("Récupération de l'évaluation pour le patient avec l'identifiant : {}", patId);
        PatientDto patient = proxyPatient.getPatientById(patId);
        List<NoteDto> listNote = proxyNotes.getAllPatientNotes(String.valueOf(patId));

        if (listNote.isEmpty()) {
            level = Level.EMPTY;
        } else {
            int factors = getTriggersNumber(listNote);
            level = getRiskByLevel(factors, patient.getAge(), patient.getSex());
        }

        logger.info("Évaluation pour le patient avec l'identifiant {} terminée.", patId);
        return new Assessment(patId, patient.getAge(), level.getLevelAssessmentMessage());
    }


    /**
     * Calcule le nombre de déclencheurs dans les notes du patient.
     *
     * @param notes La liste des notes du patient.
     * @return Le nombre de déclencheurs calculé.
     */
    private int getTriggersNumber(List<NoteDto> notes) {
        int triggerCount = 0;

        for (NoteDto note : notes) {
            String noteContent = note.getE();

            for (Trigger trigger : Arrays.asList(Trigger.HEMOGLOBIN, Trigger.MICROALBUMIN, Trigger.HEIGHT, Trigger.WEIGHT,
                    Trigger.SMOKER, Trigger.ABNORMAL, Trigger.CHOLESTEROL, Trigger.DIZZINESS, Trigger.RELAPSE, Trigger.REACTION,
                    Trigger.ANTIBODIES)) {
                if (noteContent.contains(trigger.getTrigger())) {
                    triggerCount++;
                }
            }
        }

        logger.debug("Nombre de déclencheurs calculé : {}", triggerCount);
        return triggerCount;
    }

    /**
     * Calcule le niveau de risque en fonction des facteurs, de l'âge et du genre du patient.
     *
     * @param factors Le nombre de facteurs de risque.
     * @param age L'âge du patient.
     * @param gender Le genre du patient (F pour féminin, M pour masculin).
     * @return Le niveau de risque calculé.
     */
    private Level getRiskByLevel(int factors, int age, String gender) {
        logger.debug("Calcul du niveau de risque pour les facteurs : {}, âge : {}, genre : {}", factors, age, gender);

        if (age < 30) {
            if (gender.equals("F")) {
                if (factors >= 7) {
                    logger.debug("Niveau de risque calculé : PRÉCOCE");
                    return Level.EARLY_ONSET;
                } else if (factors >= 4) {
                    logger.debug("Niveau de risque calculé : EN DANGER");
                    return Level.IN_DANGER;
                }
            } else if (gender.equals("M")) {
                if (factors >= 5) {
                    logger.debug("Niveau de risque calculé : PRÉCOCE");
                    return Level.EARLY_ONSET;
                } else if (factors >= 3) {
                    logger.debug("Niveau de risque calculé : EN DANGER");
                    return Level.IN_DANGER;
                }
            }
        } else if (age >= 30) {
            if (factors >= 8) {
                logger.debug("Niveau de risque calculé : PRÉCOCE");
                return Level.EARLY_ONSET;
            } else if (factors >= 6) {
                logger.debug("Niveau de risque calculé : EN DANGER");
                return Level.IN_DANGER;
            } else if (factors >= 2) {
                logger.debug("Niveau de risque calculé : LIMITE");
                return Level.BORDERLINE;
            }
        }

        logger.debug("Niveau de risque calculé : VIDE");
        return Level.EMPTY;
    }

}
