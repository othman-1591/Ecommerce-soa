package com.ecommerce.client.models;

public class Client {
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String numTel;
    private String ville;
    private String email;
    private String password;

    public Client() {}

    public Client(String nom, String prenom, String adresse, String numTel, String ville, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numTel = numTel;
        this.ville = ville;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
