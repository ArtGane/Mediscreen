package com.mediscreen.notesconf.model;

import jakarta.persistence.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Entity(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;
    @Column
    private String patId;
    @Column
    private String e;
    @Column
    private LocalDateTime date;

    public Note() {
    }

    public Note(String patId, String e) {
        this.patId = patId;
        this.e = e;
        this.date = getDate();
    }

    public Note(ObjectId id, String patId, String e) {
        this.id = id;
        this.patId = patId;
        this.e = e;
        this.date = getDate();
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
        return LocalDateTime.now();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
