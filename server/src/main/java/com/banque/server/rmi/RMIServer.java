package com.banque.server.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import com.banque.common.IBanqueService;

public class RMIServer {
    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("   🚀 DÉMARRAGE SERVEUR RMI BANQUE");
            System.out.println("========================================");

            // Créer le registre RMI
            LocateRegistry.createRegistry(1099);
            System.out.println("✅ Registre RMI créé sur le port 1099");

            // Créer le service
            IBanqueService service = new BanqueRMIService();

            // Enregistrer le service
            Naming.rebind("rmi://localhost:1099/BanqueService", service);

            System.out.println("✅ Service RMI enregistré: rmi://localhost:1099/BanqueService");
            System.out.println("✅ Serveur RMI prêt à recevoir des requêtes");
            System.out.println("\n📡 En attente de connexions clients...");
            System.out.println("🛑 Appuyez sur Ctrl+C pour arrêter\n");

            // Garder le serveur actif
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.err.println("❌ Erreur serveur RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}