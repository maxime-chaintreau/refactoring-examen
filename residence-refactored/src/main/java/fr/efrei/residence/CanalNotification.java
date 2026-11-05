package fr.efrei.residence;

public interface CanalNotification {

    void envoyer(Etudiant e, String sujet, String message);

    void relancer(Etudiant e, Bail b);
}