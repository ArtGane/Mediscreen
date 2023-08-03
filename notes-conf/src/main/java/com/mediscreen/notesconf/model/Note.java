package com.mediscreen.notesconf.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long patId;

    @Column
    private String e;

    @Column
    private LocalDateTime date;

    public Note(Long patId, String e) {
        this.patId = patId;
        this.e = e;
        this.date = getDate();
    }

    public Note(Long id, Long patId, String e) {
        this.id = id;
        this.patId = patId;
        this.e = e;
        this.date = getDate();
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
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
