package com.mediscreen.assessment.service;

import com.mediscreen.assessment.dto.NoteDto;
import com.mediscreen.assessment.dto.PatientDto;
import com.mediscreen.assessment.feign.NoteFeign;
import com.mediscreen.assessment.feign.PatientFeign;
import com.mediscreen.assessment.model.Assessment;
import com.mediscreen.assessment.utils.Level;
import com.mediscreen.assessment.utils.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    private PatientFeign proxyPatient;
    @Autowired
    private NoteFeign proxyNotes;

    public Assessment getAssessmentByPatientId(Long patId) {
        PatientDto patient = proxyPatient.getPatientById(patId);
        List<NoteDto> listNote = proxyNotes.getAllPatientNotes(String.valueOf(patId));
        int factors = getTriggersNumber(listNote);

        Level level = getRiskByLevel(factors, patient.getAge(), patient.getSex());

        return new Assessment(patId, patient.getAge(), level.getLevelAssessmentMessage());
    }

    private int getTriggersNumber(List<NoteDto> notes) {
        int triggerCount = 0;

        for (NoteDto note : notes) {
            String noteContent = note.getE();

            for (Trigger trigger : Trigger.values()) {
                if (noteContent.contains(trigger.getTrigger())) {
                    triggerCount++;
                }
            }
        }

        return triggerCount;
    }

    private Level getRiskByLevel(int factors, int age, String gender) {
        if (age < 30) {
            if (gender.equals("F")) {
                if (factors >= 7) {
                    return Level.EARLY_ONSET;
                } else if (factors >= 4) {
                    return Level.IN_DANGER;
                }
            } else if (gender.equals("M")) {
                if (factors >= 5) {
                    return Level.EARLY_ONSET;
                } else if (factors >= 3) {
                    return Level.IN_DANGER;
                }
            }
        } else if (age >= 30) {
            if (factors >= 8) {
                return Level.EARLY_ONSET;
            } else if (factors >= 6) {
                return Level.IN_DANGER;
            } else if (factors >= 2) {
                return Level.BORDERLINE;
            }
        }
        return Level.EMPTY;
    }
}
