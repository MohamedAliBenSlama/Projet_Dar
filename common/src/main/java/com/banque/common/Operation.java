package com.banque.common;

import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {
    private int id;
    private int clientId;
    private String type; // "DEPOT" ou "RETRAIT"
    private double montant;
    private Date date;

    public Operation() {
        this.date = new Date();
    }

    public Operation(int clientId, String type, double montant) {
        this.clientId = clientId;
        this.type = type;
        this.montant = montant;
        this.date = new Date();
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString() {
        return String.format("Operation[Client: %d, Type: %s, Montant: %.2f, Date: %s]",
                clientId, type, montant, date);
    }
}