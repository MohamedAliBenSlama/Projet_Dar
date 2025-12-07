package com.banque.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBanqueService extends Remote {
    // Ajouter un client
    Client ajouterClient(String nom, String prenom, String email)
            throws RemoteException, ServiceException;

    // Supprimer un client
    boolean supprimerClient(int id)
            throws RemoteException, ServiceException;

    // Modifier un client
    Client modifierClient(Client client)
            throws RemoteException, ServiceException;

    // Ajouter de l'argent
    double ajouterArgent(int clientId, double montant)
            throws RemoteException, ServiceException;

    // Retirer de l'argent
    double retirerArgent(int clientId, double montant)
            throws RemoteException, ServiceException;

    // Afficher client par ID
    Client afficherClientParId(int id)
            throws RemoteException, ServiceException;

    // Liste de tous les clients
    Client[] getAllClients()
            throws RemoteException, ServiceException;
}