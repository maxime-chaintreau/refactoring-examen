package fr.efrei.residence;

public class ServiceNotification {

    static final String PREFIXE_SUJET_ANGLAIS = "[EN] ";

    private final CanalNotification email;
    private final CanalNotification sms;
    private final CanalNotification courrier;

    public ServiceNotification(CanalNotification email, CanalNotification sms, CanalNotification courrier) {
        this.email = email;
        this.sms = sms;
        this.courrier = courrier;
    }

    public void notifier(Etudiant e, String sujet, String message) {
        Canal canal = e.getCanalPrefere();
        canal(canal).envoyer(e, sujet, message);
        // les internationaux reçoivent toujours un email en plus (demande de l'administration)
        if (RegleProfil.pour(e.getProfil()).recoitCopieEmailEnAnglais() && canal != Canal.EMAIL) {
            email.envoyer(e, PREFIXE_SUJET_ANGLAIS + sujet, message);
        }
    }

    public void relancer(Etudiant e, Bail b) {
        canal(e.getCanalPrefere()).relancer(e, b);
    }

    private CanalNotification canal(Canal canal) {
        return switch (canal) {
            case EMAIL -> email;
            case SMS -> sms;
            case COURRIER -> courrier;
        };
    }
}