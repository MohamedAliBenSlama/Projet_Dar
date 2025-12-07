package com.banque.client;

import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.banque.common.IBanqueService;
import com.banque.common.Client;
import com.banque.common.ServiceException;

public class BanqueClient extends JFrame {
    private IBanqueService service;

    // Composants UI
    private JTextArea outputArea;
    private JButton btnAjouter, btnSupprimer, btnModifier;
    private JButton btnAjouterArgent, btnRetirerArgent, btnAfficher;
    private JButton btnListerTous, btnQuitter;
    private JComboBox<String> modeCombo;

    public BanqueClient() {
        initUI();
        connecterAuService();
    }

    private void connecterAuService() {
        try {
            service = (IBanqueService) Naming.lookup("rmi://localhost:1099/BanqueService");
            afficherMessage("✅ Connecté au serveur RMI avec succès!");
            afficherMessage("📡 Adresse: rmi://localhost:1099/BanqueService");
            afficherMessage("\n👤 3 clients de test sont disponibles:");
            afficherMessage("   • ID 1: Jean Dupont");
            afficherMessage("   • ID 2: Marie Martin");
            afficherMessage("   • ID 3: Pierre Dubois\n");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Impossible de se connecter au serveur!\n" +
                            "Assurez-vous que le serveur est démarré.\n\n" +
                            "Erreur: " + e.getMessage(),
                    "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initUI() {
        setTitle("🏦 Banque Distribuée - Client");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel titre
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0, 102, 204));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("GESTION BANCAIRE DISTRIBUÉE");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        titlePanel.add(title, BorderLayout.WEST);

        // ComboBox pour le mode
        modeCombo = new JComboBox<>(new String[]{"Mode: RMI", "Mode: CORBA"});
        modeCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        titlePanel.add(modeCombo, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);

        // Panel boutons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnAjouter = creerBouton("➕ AJOUTER CLIENT", new Color(46, 125, 50));
        btnSupprimer = creerBouton("🗑️ SUPPRIMER CLIENT", new Color(183, 28, 28));
        btnModifier = creerBouton("✏️ MODIFIER CLIENT", new Color(245, 124, 0));
        btnAjouterArgent = creerBouton("💰 AJOUTER ARGENT", new Color(21, 101, 192));
        btnRetirerArgent = creerBouton("💸 RETIRER ARGENT", new Color(123, 31, 162));
        btnAfficher = creerBouton("🔍 AFFICHER CLIENT", new Color(0, 131, 143));
        btnListerTous = creerBouton("📋 LISTER TOUS", new Color(69, 90, 100));
        btnQuitter = creerBouton("🚪 QUITTER", new Color(120, 144, 156));

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnAjouterArgent);
        buttonPanel.add(btnRetirerArgent);
        buttonPanel.add(btnAfficher);
        buttonPanel.add(btnListerTous);
        buttonPanel.add(btnQuitter);

        add(buttonPanel, BorderLayout.CENTER);

        // Zone de sortie
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("RÉSULTATS ET JOURNAL"));
        scrollPane.setPreferredSize(new Dimension(800, 250));

        add(scrollPane, BorderLayout.SOUTH);

        // Écouteurs
        setupListeners();

        // Centrer la fenêtre
        setLocationRelativeTo(null);
    }

    private JButton creerBouton(String texte, Color couleur) {
        JButton bouton = new JButton(texte);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setBackground(couleur);
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bouton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bouton.setBackground(couleur.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bouton.setBackground(couleur);
            }
        });

        return bouton;
    }

    private void setupListeners() {
        btnAjouter.addActionListener(e -> ajouterClient());
        btnSupprimer.addActionListener(e -> supprimerClient());
        btnModifier.addActionListener(e -> modifierClient());
        btnAjouterArgent.addActionListener(e -> ajouterArgent());
        btnRetirerArgent.addActionListener(e -> retirerArgent());
        btnAfficher.addActionListener(e -> afficherClient());
        btnListerTous.addActionListener(e -> listerTousClients());
        btnQuitter.addActionListener(e -> quitter());
    }

    private void ajouterClient() {
        try {
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField nomField = new JTextField(20);
            JTextField prenomField = new JTextField(20);
            JTextField emailField = new JTextField(20);

            panel.add(new JLabel("Nom:"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom:"));
            panel.add(prenomField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);

            int result = JOptionPane.showConfirmDialog(this, panel,
                    "AJOUTER UN NOUVEAU CLIENT", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String email = emailField.getText().trim();

                if (!nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty()) {
                    afficherMessage("📝 Tentative d'ajout client: " + prenom + " " + nom);
                    Client client = service.ajouterClient(nom, prenom, email);
                    afficherMessage("✅ Client ajouté avec succès!");
                    afficherMessage("   " + client.toString());
                } else {
                    JOptionPane.showMessageDialog(this,
                            "❌ Tous les champs sont requis!",
                            "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            afficherErreur("Erreur lors de l'ajout du client", e);
        }
    }

    private void supprimerClient() {
        try {
            String idStr = JOptionPane.showInputDialog(this,
                    "Entrez l'ID du client à supprimer:",
                    "SUPPRIMER UN CLIENT", JOptionPane.QUESTION_MESSAGE);

            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr.trim());
                afficherMessage("🗑️  Tentative de suppression client ID: " + id);

                boolean success = service.supprimerClient(id);

                if (success) {
                    afficherMessage("✅ Client " + id + " supprimé avec succès!");
                } else {
                    afficherMessage("❌ Échec de la suppression du client " + id);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ ID invalide! Veuillez entrer un nombre.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            afficherErreur("Erreur lors de la suppression", e);
        }
    }

    private void modifierClient() {
        try {
            String idStr = JOptionPane.showInputDialog(this,
                    "Entrez l'ID du client à modifier:",
                    "MODIFIER UN CLIENT", JOptionPane.QUESTION_MESSAGE);

            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr.trim());
                afficherMessage("✏️  Récupération client ID: " + id);

                Client client = service.afficherClientParId(id);

                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JTextField nomField = new JTextField(client.getNom(), 20);
                JTextField prenomField = new JTextField(client.getPrenom(), 20);
                JTextField emailField = new JTextField(client.getEmail(), 20);
                JTextField soldeField = new JTextField(String.valueOf(client.getSolde()), 20);

                panel.add(new JLabel("Nom:"));
                panel.add(nomField);
                panel.add(new JLabel("Prénom:"));
                panel.add(prenomField);
                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Solde:"));
                panel.add(soldeField);

                int result = JOptionPane.showConfirmDialog(this, panel,
                        "MODIFIER LE CLIENT ID: " + id, JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    client.setNom(nomField.getText().trim());
                    client.setPrenom(prenomField.getText().trim());
                    client.setEmail(emailField.getText().trim());

                    try {
                        double solde = Double.parseDouble(soldeField.getText().trim());
                        client.setSolde(solde);
                    } catch (NumberFormatException e) {
                        afficherMessage("⚠️  Solde inchangé (format invalide)");
                    }

                    afficherMessage("✏️  Modification en cours...");
                    Client updated = service.modifierClient(client);
                    afficherMessage("✅ Client modifié avec succès!");
                    afficherMessage("   " + updated.toString());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ ID invalide!", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            afficherErreur("Erreur lors de la modification", e);
        }
    }

    private void ajouterArgent() {
        operationArgent("AJOUTER");
    }

    private void retirerArgent() {
        operationArgent("RETIRER");
    }

    private void operationArgent(String type) {
        try {
            JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField idField = new JTextField(10);
            JTextField montantField = new JTextField(10);

            panel.add(new JLabel("ID Client:"));
            panel.add(idField);
            panel.add(new JLabel("Montant (€):"));
            panel.add(montantField);

            String titre = type.equals("AJOUTER") ? "AJOUTER DE L'ARGENT" : "RETIRER DE L'ARGENT";

            int result = JOptionPane.showConfirmDialog(this, panel,
                    titre, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int id = Integer.parseInt(idField.getText().trim());
                double montant = Double.parseDouble(montantField.getText().trim());

                afficherMessage((type.equals("AJOUTER") ? "💰 " : "💸 ") +
                        "Opération: Client " + id + ", Montant: " + montant + " €");

                double nouveauSolde;
                if (type.equals("AJOUTER")) {
                    nouveauSolde = service.ajouterArgent(id, montant);
                    afficherMessage("✅ Argent ajouté avec succès!");
                } else {
                    nouveauSolde = service.retirerArgent(id, montant);
                    afficherMessage("✅ Argent retiré avec succès!");
                }

                afficherMessage("   Nouveau solde: " + String.format("%.2f", nouveauSolde) + " €");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Valeurs numériques invalides!", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            afficherErreur("Erreur lors de l'opération", e);
        }
    }

    private void afficherClient() {
        try {
            String idStr = JOptionPane.showInputDialog(this,
                    "Entrez l'ID du client à afficher:",
                    "RECHERCHER UN CLIENT", JOptionPane.QUESTION_MESSAGE);

            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr.trim());
                afficherMessage("🔍 Recherche client ID: " + id);

                Client client = service.afficherClientParId(id);
                afficherMessage("✅ Client trouvé:");
                afficherMessage("   " + client.toString());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ ID invalide!", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            afficherErreur("Erreur lors de la recherche", e);
        }
    }

    private void listerTousClients() {
        try {
            afficherMessage("📋 Récupération de tous les clients...");

            Client[] clients = service.getAllClients();

            if (clients.length == 0) {
                afficherMessage("ℹ️  Aucun client trouvé dans la base de données.");
            } else {
                afficherMessage("✅ " + clients.length + " client(s) trouvé(s):");
                afficherMessage("══════════════════════════════════════════");

                for (Client client : clients) {
                    afficherMessage(client.toString());
                }

                afficherMessage("══════════════════════════════════════════");

                // Calculer le solde total
                double soldeTotal = 0;
                for (Client client : clients) {
                    soldeTotal += client.getSolde();
                }
                afficherMessage("💰 Solde total de tous les clients: " +
                        String.format("%.2f", soldeTotal) + " €");
            }
        } catch (Exception e) {
            afficherErreur("Erreur lors du listing", e);
        }
    }

    private void quitter() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment quitter l'application?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            afficherMessage("\n👋 Fermeture de l'application...");
            System.exit(0);
        }
    }

    private void afficherMessage(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void afficherErreur(String contexte, Exception e) {
        String message = e.getMessage();
        if (e instanceof ServiceException) {
            afficherMessage("❌ " + contexte + ": " + message);
        } else {
            afficherMessage("❌ " + contexte + ": " + e.getClass().getSimpleName() + " - " + message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                BanqueClient client = new BanqueClient();
                client.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Erreur lors du démarrage du client: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}