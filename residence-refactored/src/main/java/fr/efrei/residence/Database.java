package fr.efrei.residence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Accès à la base de données (simulée en mémoire pour l'instant).
 */
public class Database implements EtudiantRepository, BailRepository {

    private final Map<String, Etudiant> etudiants = new HashMap<>();
    private final Map<String, Bail> baux = new HashMap<>();

    public Database() {
        System.out.println("[DB] Connexion à jdbc:postgresql://db.campusvert.local:5432/residence");
    }

    public void executer(String sql) {
        System.out.println("[SQL] " + sql);
    }

     @Override
    public void sauvegarder(Etudiant e) {
        executer("INSERT INTO etudiant (id, nom, email, profil) VALUES ('" + e.getId() + "', '" + e.getNom() + "', '" + e.getEmail() + "', '" + e.getProfil() + "')");
        etudiants.put(e.getEmail(), e);
    }

    @Override
    public Etudiant trouverParEmail(String email) {
        executer("SELECT * FROM etudiant WHERE email = '" + email + "'");
        return etudiants.get(email);
    }

    @Override
    public Etudiant trouverParId(String id) {
        executer("SELECT * FROM etudiant WHERE id = '" + id + "'");
        for (Etudiant e : etudiants.values()) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    @Override
    public void sauvegarder(Bail b) {
        executer("INSERT INTO bail (id, etudiant, logement, statut, loyer) VALUES ('" + b.getId() + "', '" + b.getEtudiant().getId() + "', '" + b.getLogement().getNumero() + "', '" + b.getStatut() + "', " + b.getLoyerChargesComprises() + ")");
        baux.put(b.getId(), b);
    }

    @Override
    public void mettreAJour(Bail b) {
        executer("UPDATE bail SET statut = '" + b.getStatut() + "', solde = " + b.soldeDu + " WHERE id = '" + b.getId() + "'");
    }

    @Override
    public Bail trouver(String id) {
        executer("SELECT * FROM bail WHERE id = '" + id + "'");
        return baux.get(id);
    }

    @Override
    public List<Bail> tous() {
        executer("SELECT * FROM bail");
        List<Bail> liste = new ArrayList<>(baux.values());
        liste.sort((a, b) -> a.getId().compareTo(b.getId()));
        return liste;
    }
}
