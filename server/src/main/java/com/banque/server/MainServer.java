package com.banque.server;

import com.banque.server.rmi.RMIServer;
import com.banque.server.jms.JMSConsumer;

public class MainServer {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║      🏦 SERVEUR BANQUE DISTRIBUÉE                   ║");
        System.out.println("║      Technologies: RMI + JMS                        ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        System.out.println("\n📡 DÉMARRAGE DES SERVICES...\n");

        try {
            // 1. Démarrer RMI (service principal)
            System.out.println("1️⃣  DÉMARRAGE SERVICE RMI...");
            Thread rmiThread = new Thread(() -> {
                try {
                    RMIServer.main(new String[]{});
                } catch (Exception e) {
                    System.err.println("❌ Erreur RMI: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            rmiThread.setDaemon(true);
            rmiThread.start();

            // Attendre que RMI démarre
            Thread.sleep(3000);

            // 2. Démarrer JMS (optionnel)
            System.out.println("2️⃣  DÉMARRAGE SERVICE JMS...");
            Thread jmsThread = new Thread(() -> {
                try {
                    JMSConsumer consumer = new JMSConsumer();
                    consumer.start();
                } catch (Exception e) {
                    System.err.println("❌ JMS: " + e.getMessage());
                    System.out.println("⚠️  Mode simulation JMS activé");
                    // Mode simulation JMS
                    try {
                        while (true) {
                            Thread.sleep(5000);
                            System.out.println("📡 JMS Simulation: Service actif...");
                        }
                    } catch (InterruptedException ie) {
                        System.out.println("JMS simulé arrêté");
                    }
                }
            });
            jmsThread.setDaemon(true);
            jmsThread.start();

            Thread.sleep(2000);

            // Afficher informations
            afficherInformations();

            // Garder le serveur actif
            System.out.println("\n🎯 SERVEUR PRINCIPAL ACTIF");
            System.out.println("🛑 Appuyez sur Ctrl+C pour arrêter\n");

            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.err.println("❌ ERREUR SERVEUR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void afficherInformations() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                ✅ SERVICES DÉMARRÉS                  ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║                                                     ║");
        System.out.println("║  🟢 RMI: rmi://localhost:1099/BanqueService         ║");
        System.out.println("║  🟣 JMS: tcp://localhost:61616 (simulé si besoin)   ║");
        System.out.println("║                                                     ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        System.out.println("\n👤 CLIENTS DE TEST DISPONIBLES (via RMI):");
        System.out.println("   • ID 1: Jean Dupont (solde: 1100.00 €)");
        System.out.println("   • ID 2: Marie Martin (solde: 1200.00 €)");
        System.out.println("   • ID 3: Pierre Dubois (solde: 1300.00 €)");

        System.out.println("\n🎯 FONCTIONNALITÉS DISPONIBLES:");
        System.out.println("   1. ➕ Ajouter un client");
        System.out.println("   2. 🗑️  Supprimer un client");
        System.out.println("   3. ✏️  Modifier un client");
        System.out.println("   4. 💰 Ajouter de l'argent");
        System.out.println("   5. 💸 Retirer de l'argent");
        System.out.println("   6. 🔍 Afficher client par ID");
        System.out.println("   7. 📋 Lister tous les clients");
    }
}