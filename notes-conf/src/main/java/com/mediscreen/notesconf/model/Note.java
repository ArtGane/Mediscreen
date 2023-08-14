package com.mediscreen.notesconf.model;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("notes")
public class Note {

    @Id
    private String id;
    private String patId;
    private String e;
    private LocalDateTime date;

    public Note() {
    }

    public Note(String patId, String e) {
        this.patId = patId;
        this.e = e;
        this.date = getDate();
    }

    public Note(String id, String patId, String e) {
        this.id = id;
        this.patId = patId;
        this.e = e;
        this.date = getDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDateTime.now();
    }
}
