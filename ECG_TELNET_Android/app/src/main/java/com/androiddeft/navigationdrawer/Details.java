package com.androiddeft.navigationdrawer;


//details d'une personne
public class Details {

    int id;
    String nom;
    String prenom;
    String civilite;
    String datee;
    String email;
    Integer number;
    String nationality;
    String remarque;
    Integer reference;
    private byte[] image;

    public Details() {

    }

    public Details(int id, String nom, String datee, Integer reference, byte[] image) {
        this.id = id;
        this.nom = nom;
        this.datee = datee;
        this.reference = reference;
        this.image = image;
    }

    public Details(int id, String nom, byte[] image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
    }




    public Details(String nom, String prenom, String civilite, String datee, String email, Integer number, String nationality, String remarque, Integer reference, byte[] image) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.datee = datee;
        this.email = email;
        this.number = number;
        this.nationality = nationality;
        this.remarque = remarque;
        this.reference = reference;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }
}