package fr.efrei.residence;

public class NotificationPush implements CanalNotification {


    private final PushService push;

    public NotificationPush(PushService push) {
        this.push = push;
    }

    @Override
    public void envoyer(Etudiant e, String sujet, String message) {
        push.envoyer(e.getEmail(), sujet);
    }

    @Override
    public void relancer(Etudiant e, Bail b) {
        push.envoyer(e.getEmail(), "Relance loyer impayé");
    }
}