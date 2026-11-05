package fr.efrei.residence;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ServiceRapport {

    static final String FORMAT_LIGNE_OCCUPATION = "%-13s: %d/%d%s";

    private final LogementRepository logements;
    private final BailRepository baux;
    private final SmsService sms;
    private final CourrierService courrier;

    public ServiceRapport(LogementRepository logements, BailRepository baux, SmsService sms, CourrierService courrier) {
        this.logements = logements;
        this.baux = baux;
        this.sms = sms;
        this.courrier = courrier;
    }

    public void afficher() {
        System.out.println();
        System.out.println(generer());
    }

    public String generer() {
        String r = "";
        r += "=============== RAPPORT " + Residence.NOM + " ===============" + "\n";
        Map<TypeLogement, Integer> places = new EnumMap<>(TypeLogement.class);
        Map<TypeLogement, Integer> placesOccupees = new EnumMap<>(TypeLogement.class);
        double surfaceBalcons = 0;
        for (Logement l : logements.tous()) {
            places.merge(l.getType(), l.getNombrePlaces(), Integer::sum);
            placesOccupees.merge(l.getType(), l.getPlacesOccupees(), Integer::sum);
            surfaceBalcons += l.getSurfaceBalcon();
        }
        int total = 0;
        int occupes = 0;
        for (TypeLogement type : TypeLogement.values()) {
            int nb = places.getOrDefault(type, 0);
            int nbOccupes = placesOccupees.getOrDefault(type, 0);
            if (nb == 0) continue;
            r += String.format(FORMAT_LIGNE_OCCUPATION, type.getLibelleRapport(), nbOccupes, nb, type.getSuffixeRapport()) + "\n";
            total += nb;
            occupes += nbOccupes;
        }
        r += "Occupation   : " + Math.round(occupes * 100.0 / total) + " %" + "\n";
        r += "Balcons      : " + surfaceBalcons + " m²" + "\n";

        double loyersMensuels = 0;
        double impayes = 0;
        double cautions = 0;
        List<Bail> tousLesBaux = baux.tous();
        for (Bail b : tousLesBaux) {
            if (b.getStatut().estEnCours()) {
                loyersMensuels += b.getLoyerChargesComprises();
                cautions += b.getCaution();
            }
            if (b.soldeDu > 0) impayes += b.soldeDu;
        }
        r += "Loyers CC    : " + Utils.euros(Utils.arrondir(loyersMensuels)) + " / mois" + "\n";
        r += "Cautions     : " + Utils.euros(Utils.arrondir(cautions)) + "\n";
        r += "Impayés      : " + Utils.euros(Utils.arrondir(impayes)) + "\n";
        r += "SMS envoyés  : " + sms.getCompteurSms() + "\n";
        r += "Timbres      : " + Utils.euros(Utils.arrondir(courrier.getTotalAffranchissement())) + "\n";
        r += "============================================================";
        return r;
    }
}