package fr.efrei.residence;

import java.util.Optional;

public class ServiceInscription {

    static final String PREFIXE_ID_ETUDIANT = "ETU-";

    private final EtudiantRepository etudiants;
    private int compteurEtudiants = 0;

    public ServiceInscription(EtudiantRepository etudiants) {
        this.etudiants = etudiants;
    }

    public Etudiant inscrire(String nom, String prenom, String email, String telephone, String adresse,
                             Profil profil, int echelonBourse, double ressources, boolean pmr,
                             Canal canalPrefere) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("Inscription refusée : nom obligatoire");
            return null;
        }
        if (!Utils.emailValide(email)) {
            System.out.println("Inscription refusée : email invalide (" + email + ")");
            return null;
        }
        if (etudiants.trouverParEmail(email) != null) {
            System.out.println("Inscription refusée : " + email + " déjà inscrit");
            return null;
        }
        if (profil == null) {
            System.out.println("Inscription refusée : profil inconnu " + profil);
            return null;
        }
        Optional<String> refusProfil = RegleProfil.pour(profil).verifierInscription(echelonBourse, telephone);
        if (refusProfil.isPresent()) {
            System.out.println("Inscription refusée : " + refusProfil.get());
            return null;
        }
        if (canalPrefere == Canal.SMS && telephone == null) {
            System.out.println("Inscription refusée : téléphone obligatoire pour les SMS");
            return null;
        }
        if (canalPrefere == Canal.COURRIER && (adresse == null || adresse.isEmpty())) {
            System.out.println("Inscription refusée : adresse obligatoire pour le courrier");
            return null;
        }
        compteurEtudiants++;
        Etudiant e = new Etudiant(PREFIXE_ID_ETUDIANT + compteurEtudiants, nom, prenom, email, telephone, adresse,
                profil, echelonBourse, ressources, pmr, canalPrefere);
        etudiants.sauvegarder(e);
        System.out.println("Étudiant inscrit : " + e.getId() + " " + e.getNomComplet() + " (" + profil + ")");
        return e;
    }
}