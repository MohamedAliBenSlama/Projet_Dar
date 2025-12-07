package com.banque.common;

import java.io.Serializable;

public class Client implements Serializable {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private double solde;

    public Client() {}

    public Client(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.solde = 0.0;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    @Override
    public String toString() {
        return String.format("ID: %d | Nom: %s %s | Email: %s | Solde: %.2f €",
                id, prenom, nom, email, solde);
    }
}