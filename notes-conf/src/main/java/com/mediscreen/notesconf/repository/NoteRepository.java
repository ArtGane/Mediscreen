package com.mediscreen.notesconf.repository;

import com.mediscreen.notesconf.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Long> {

    List<Note> findAllByPatId(Long id);

    Note findNoteById(Long id);
    Note findNoteByPatId(Long id);
}
