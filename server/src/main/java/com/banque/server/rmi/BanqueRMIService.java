package com.banque.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import com.banque.common.IBanqueService;
import com.banque.common.Client;
import com.banque.common.ServiceException;
import com.banque.server.jms.JMSProducer;

public class BanqueRMIService extends UnicastRemoteObject implements IBanqueService {

    private static final long serialVersionUID = 1L;

    // ✅ Stockage interne en Long
    private Map<Long, Client> clients = new HashMap<>();
    private long nextId = 1;

    public BanqueRMIService() throws RemoteException {
        super();

        ajouterClientTest("Dupont", "Jean", "jean.dupont@email.com", 1100.00);
        ajouterClientTest("Martin", "Marie", "marie.martin@email.com", 1200.00);
        ajouterClientTest("Dubois", "Pierre", "pierre.dubois@email.com", 1300.00);

        System.out.println("✅ 3 clients de test créés");
    }

    private void ajouterClientTest(String nom, String prenom, String email, double solde) {
        Client client = new Client(nom, prenom, email);

        // ✅ CAST OBLIGATOIRE ICI
        client.setId((int) nextId);
        client.setSolde(solde);

        clients.put(nextId, client);
        System.out.println("   → Client " + nextId + ": " + prenom + " " + nom);

        nextId++;
    }

    // ✅ AJOUT CLIENT
    @Override
    public Client ajouterClient(String nom, String prenom, String email)
            throws RemoteException, ServiceException {

        Client client = new Client(nom, prenom, email);

        // ✅ CAST OBLIGATOIRE ICI
        client.setId((int) nextId);

        clients.put(nextId, client);

        JMSProducer.envoyerNotification(
                "Nouveau client: " + nom + " " + prenom + " (ID: " + nextId + ")"
        );

        nextId++;
        return client;
    }

    // ✅ SUPPRIMER CLIENT (int conforme à l’interface)
    @Override
    public boolean supprimerClient(int id) throws RemoteException, ServiceException {

        long longId = id;

        if (!clients.containsKey(longId)) {
            throw new ServiceException("Client non trouvé: " + id);
        }

        Client client = clients.remove(longId);

        JMSProducer.envoyerNotification(
                "Client supprimé: " + client.getNom() + " (ID: " + id + ")"
        );

        return true;
    }

    // ✅ MODIFIER CLIENT
    @Override
    public Client modifierClient(Client client) throws RemoteException, ServiceException {

        long id = client.getId();

        if (!clients.containsKey(id)) {
            throw new ServiceException("Client non trouvé: " + id);
        }

        clients.put(id, client);

        JMSProducer.envoyerNotification(
                "Client modifié: " + client.getNom() + " (ID: " + id + ")"
        );

        return client;
    }

    // ✅ AJOUT ARGENT
    @Override
    public double ajouterArgent(int clientId, double montant)
            throws RemoteException, ServiceException {

        long longId = clientId;

        Client client = getClient(longId);

        double nouveauSolde = client.getSolde() + montant;
        client.setSolde(nouveauSolde);

        clients.put(longId, client);

        JMSProducer.envoyerOperation("DEPOT", longId, montant, nouveauSolde);

        return nouveauSolde;
    }

    // ✅ RETRAIT ARGENT
    @Override
    public double retirerArgent(int clientId, double montant)
            throws RemoteException, ServiceException {

        long longId = clientId;

        Client client = getClient(longId);

        if (client.getSolde() < montant) {
            throw new ServiceException("Solde insuffisant. Solde actuel: " + client.getSolde());
        }

        double nouveauSolde = client.getSolde() - montant;
        client.setSolde(nouveauSolde);

        clients.put(longId, client);

        JMSProducer.envoyerOperation("RETRAIT", longId, montant, nouveauSolde);

        return nouveauSolde;
    }

    // ✅ AFFICHER CLIENT PAR ID (int conforme)
    @Override
    public Client afficherClientParId(int id)
            throws RemoteException, ServiceException {

        long longId = id;
        return getClient(longId);
    }

    // ✅ LISTE DES CLIENTS
    @Override
    public Client[] getAllClients()
            throws RemoteException, ServiceException {

        return clients.values().toArray(new Client[0]);
    }

    // ✅ MÉTHODE INTERNE
    private Client getClient(Long id) throws ServiceException {

        Client client = clients.get(id);

        if (client == null) {
            throw new ServiceException("Client non trouvé avec ID: " + id);
        }

        return client;
    }
}
