package com.banque.server.jms;

public class JMSConsumer {
    public void start() {
        System.out.println("========================================");
        System.out.println("   👂 SERVICE JMS (SIMULATION)");
        System.out.println("========================================");
        System.out.println("✅ Service JMS démarré (mode simulation)");
        System.out.println("📋 Journalisation dans journal_operations.txt");
        System.out.println("\n📡 Simulation JMS en cours...");

        try {
            // Simuler un service JMS
            while (true) {
                Thread.sleep(10000); // Vérifier toutes les 10 secondes
                System.out.println("📡 JMS Simulation: Service actif...");
            }
        } catch (InterruptedException e) {
            System.out.println("JMS arrêté");
        }
    }
}