package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

public class ServiceAttribution {

    static final String FORMAT_ID_BAIL = "BAIL-%03d";

    private final LogementRepository logements;
    private final BailRepository baux;
    private final CalculateurLoyer calculateur;
    private final ServiceNotification notifications;
    private int compteurBail = 0;

    public ServiceAttribution(LogementRepository logements, BailRepository baux, CalculateurLoyer calculateur,
                              ServiceNotification notifications) {
        this.logements = logements;
        this.baux = baux;
        this.calculateur = calculateur;
        this.notifications = notifications;
    }

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, Set<Option> options, LocalDate dateSignature) {
        System.out.println("--- Attribution d'un " + typeSouhaite + " à " + e.getNomComplet());
        RegleProfil regle = RegleProfil.pour(e.getProfil());
        Logement choisi = null;
        double loyer = 0;
        double charges = 0;
        for (Logement l : logements.tous()) {
            if (l.getType() != typeSouhaite) continue;
            if (!l.estLibre()) continue;
            if (e.isPmr() && !l.estAccessiblePMR()) continue;

            double loyerCandidat = calculateur.calculerLoyer(l, e.getProfil(), e.getEchelonBourse(), options);
            double chargesCandidat = calculateur.calculerCharges(l);
            double total = loyerCandidat + chargesCandidat;

            // solvabilité
            if (!regle.estSolvable(e.getRessourcesMensuelles(), total)) {
                System.out.println("  " + l.getNumero() + " : ressources insuffisantes (" + Utils.euros(total) + "/mois)");
                continue;
            }
            choisi = l;
            loyer = loyerCandidat;
            charges = chargesCandidat;
            break;
        }
        if (choisi == null) {
            System.out.println("  Aucun logement disponible pour " + e.getNomComplet());
            return null;
        }

        if (choisi instanceof Colocation) {
            choisi.ajouterColocataire(e);
        } else {
            choisi.loger(e);
        }

        compteurBail++;
        String id = String.format(FORMAT_ID_BAIL, compteurBail);
        Bail b = new Bail(id, e, choisi, dateSignature);
        b.setOptions(options);
        b.setLoyerHorsCharges(loyer);
        b.setCharges(charges);

        // dépôt de garantie
        b.setCaution(Utils.arrondir(regle.caution(loyer)));

        // frais de dossier
        b.setFraisDossier(regle.fraisDossier());

        baux.sauvegarder(b);
        System.out.println("  Bail " + id + " signé : " + choisi.getNumero() + ", " + Utils.euros(b.getLoyerChargesComprises())
                + " CC/mois, caution " + Utils.euros(b.getCaution()) + ", frais de dossier " + Utils.euros(b.getFraisDossier()));
        notifications.notifier(e, "Votre bail " + id,
                "Bienvenue à la " + Residence.NOM + " ! Logement " + choisi.getNumero() + ", loyer "
                        + Utils.euros(b.getLoyerChargesComprises()) + " charges comprises.");
        return b;
    }
}