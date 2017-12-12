package com.mwroblewski.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ATTACHMENTS")
@JsonIgnoreProperties(value = {"profile"})
public class Attachments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] photo;
    @Lob
    private byte[] cv;
    @Lob
    private byte[] coverLetter;

    public Attachments() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public byte[] getCv() {
        return cv;
    }
    public void setCv(byte[] cv) {
        this.cv = cv;
    }
    public byte[] getCoverLetter() {
        return coverLetter;
    }
    public void setCoverLetter(byte[] coverLetter) {
        this.coverLetter = coverLetter;
    }
}