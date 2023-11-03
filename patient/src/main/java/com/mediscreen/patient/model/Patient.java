package com.mediscreen.patient.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String family;
    @Column(nullable = false)
    private String given;
    @Column(nullable = false)
    private LocalDate dob;
    @Column
    private int age;
    @Column(nullable = false)
    private String sex;
    @Column
    private String address = "";
    @Column
    private String phone = "";

    public Patient() { }

    public Patient(Long id, String family, String given, LocalDate dob, String sex) {
        this.id = id;
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.age = getAge();
        this.sex = sex;
    }

    public Patient(String family, String given, LocalDate dob, String sex) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.age = getAge();
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return LocalDate.now().compareTo(dob);
    }
}
