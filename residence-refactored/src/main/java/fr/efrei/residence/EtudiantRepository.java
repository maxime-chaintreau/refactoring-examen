package fr.efrei.residence;

public interface EtudiantRepository {

    void sauvegarder(Etudiant e);

    Etudiant trouverParEmail(String email);

    Etudiant trouverParId(String id);
}