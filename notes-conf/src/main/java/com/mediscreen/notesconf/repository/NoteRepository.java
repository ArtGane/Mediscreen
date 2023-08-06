package com.mediscreen.notesconf.repository;

import com.mediscreen.notesconf.model.Note;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Long> {

    List<Note> findAllByPatId(String id);

    Note findNoteById(ObjectId id);
    Note findNoteByPatId(String id);
}
