package fr.efrei.residence;

import java.util.EnumMap;
import java.util.Map;

public class ServiceNotification {

    static final String PREFIXE_SUJET_ANGLAIS = "[EN] ";

    private final Map<Canal, CanalNotification> canaux;

     public ServiceNotification(Map<Canal, CanalNotification> canaux) {
        this.canaux = new EnumMap<>(canaux);
    }

    public void notifier(Etudiant e, String sujet, String message) {
        Canal canal = e.getCanalPrefere();
        canal(canal).envoyer(e, sujet, message);
        // les internationaux reçoivent toujours un email en plus (demande de l'administration)
        if (RegleProfil.pour(e.getProfil()).recoitCopieEmailEnAnglais() && canal != Canal.EMAIL) {
            canal(Canal.EMAIL).envoyer(e, PREFIXE_SUJET_ANGLAIS + sujet, message);
        }
    }

    public void relancer(Etudiant e, Bail b) {
        canal(e.getCanalPrefere()).relancer(e, b);
    }

    private CanalNotification canal(Canal canal) {
        CanalNotification c = canaux.get(canal);
        if (c == null) {
            throw new IllegalStateException("Aucun canal configuré pour " + canal);
        }
        return c;
    }
}