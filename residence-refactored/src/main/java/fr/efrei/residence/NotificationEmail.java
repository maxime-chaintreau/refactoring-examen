package fr.efrei.residence;

public class NotificationEmail implements CanalNotification {

    private final EmailService email;

    public NotificationEmail(EmailService email) {
        this.email = email;
    }

    @Override
    public void envoyer(Etudiant e, String sujet, String message) {
        email.envoyer(e.getEmail(), sujet, message);
    }

    @Override
    public void relancer(Etudiant e, Bail b) {
        email.envoyer(e.getEmail(), "RELANCE - loyer impayé",
                "Bonjour " + e.getPrenom() + ", il reste " + Utils.euros(b.soldeDu) + " à régler sur le bail " + b.getId() + ".");
    }
}