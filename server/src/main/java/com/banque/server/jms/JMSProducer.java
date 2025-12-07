package com.banque.server.jms;

public class JMSProducer {
    // Version simplifiée - peut fonctionner sans ActiveMQ
    public static void envoyerNotification(String message) {
        System.out.println("📨 [NOTIFICATION] " + message);
        // Log dans un fichier
        try {
            java.nio.file.Files.write(
                    java.nio.file.Paths.get("journal_operations.txt"),
                    (java.time.LocalDateTime.now() + " - " + message + "\n").getBytes(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            // Ignorer si fichier non accessible
        }
    }

    public static void envoyerOperation(String type, long clientId, double montant, double solde) {
        String message = String.format("%s: Client %d - Montant %.2f € - Nouveau solde: %.2f €",
                type, clientId, montant, solde);
        envoyerNotification(message);
    }
}