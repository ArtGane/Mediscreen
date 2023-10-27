package com.mediscreen.notesconf.service;

import com.mediscreen.notesconf.model.Note;
import com.mediscreen.notesconf.repository.NoteRepository;
import java.lang.String;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Crée ou met à jour une note dans la base de données.
     *
     * @param note La note à créer ou mettre à jour.
     * @return La note créée ou mise à jour.
     * @throws IllegalArgumentException Si la note est nulle.
     */
    public Note createOrUpdateNote(Note note) {
        if (note == null) {
            logger.error("La note est null. Impossible de créer ou mettre à jour la note.");
            throw new IllegalArgumentException("La note ne peut pas être null.");
        }
        noteRepository.save(note);
        logger.info("La note du {} a été créée ou mise à jour", note.getDate());
        return note;
    }

    /**
     * Crée ou met à jour une note pour un patient spécifié dans la base de données.
     *
     * @param note La note à créer ou mettre à jour.
     * @param patId L'ID du patient associé à la note.
     * @return La note créée ou mise à jour.
     * @throws IllegalArgumentException Si la note est nulle.
     */
    public Note createOrUpdateNoteByPatId(Note note, Long patId) {
        if (note == null) {
            logger.error("La note est null. Impossible de créer ou mettre à jour la note.");
            throw new IllegalArgumentException("La note ne peut pas être null.");
        }
        note.setPatId(patId.toString());

        if (note.getDate() == null) {
            note.setDate();
        }
        noteRepository.save(note);
        logger.info("La note du {} a été créée ou mise à jour", note.getDate());
        return note;
    }

    /**
     * Supprime une note de la base de données en utilisant son ID.
     *
     * @param id L'ID de la note à supprimer.
     * @throws IllegalArgumentException Si la note n'est pas trouvée avec l'ID spécifié.
     */
    public void deleteNote(String id) {
        Note note = getNoteById(id);
        if (note != null) {
            noteRepository.delete(note);
            logger.info("La note {} a été supprimée", note.getDate());
        } else {
            logger.error("Aucune note trouvée avec l'ID : {}", id);
            throw new IllegalArgumentException("Aucune note trouvée avec l'ID : " + id);
        }
    }

    /**
     * Récupère une note de la base de données en utilisant son ID.
     *
     * @param id L'ID de la note à récupérer.
     * @return La note correspondant à l'ID spécifié.
     * @throws IllegalArgumentException Si aucune note n'est trouvée avec l'ID spécifié.
     */
    public Note getNoteById(String id) {
        Note note = noteRepository.findNoteById(id);
        if (note == null) {
            logger.error("Aucune note trouvée avec l'ID : {}", id);
            throw new IllegalArgumentException("Aucune note trouvée avec l'ID : " + id);
        }
        return note;
    }

    /**
     * Récupère toutes les notes associées à un patient spécifié.
     *
     * @param patId L'ID du patient pour lequel récupérer les notes.
     * @return La liste des notes associées au patient.
     */
    public List<Note> getAllNotes(String patId) {
        List<Note> patientNotes = noteRepository.findAllByPatId(patId);
        if (patientNotes.isEmpty()) {
            logger.warn("Aucune note trouvée pour le patient avec l'ID : {}", patId);
        }
        return patientNotes;
    }

    /**
     * Récupère une liste de notes contenant un mot clé spécifié.
     *
     * @param keyword Le mot clé à rechercher dans les notes.
     * @return La liste des notes contenant le mot clé.
     */
    public List<Note> getNotesFromKey(String keyword) {
        List<Note> notes = noteRepository.findAll();
        List<Note> newNotes = new ArrayList<>();

        for (Note note : notes) {
            String[] notesByWords = note.getE().split(" ");
            for (String word : notesByWords) {
                if (word.equalsIgnoreCase(keyword)) newNotes.add(note);
            }
        }

        return newNotes;
    }
}
