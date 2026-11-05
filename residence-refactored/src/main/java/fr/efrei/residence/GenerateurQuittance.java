package fr.efrei.residence;

import java.util.stream.Collectors;

public class GenerateurQuittance {

    String listerOptions(Bail b) {
        if (b.getOptions().isEmpty()) return "aucune";
        return b.getOptions().stream().map(Option::getLibelle).collect(Collectors.joining(", "));
    }

    public String generer(Bail b, String mois) {
        Paiement p = null;
        for (Paiement x : b.paiements) {
            if (x.mois.equals(mois)) p = x;
        }
        if (p == null) {
            return "Aucun paiement pour " + mois;
        }
        String q = "";
        q += "==========================================\n";
        q += "   RÉSIDENCE CAMPUS VERT - QUITTANCE\n";
        q += "==========================================\n";
        q += "Bail      : " + b.getId() + "\n";
        q += "Locataire : " + b.getEtudiant().getNomComplet() + " (" + b.getEtudiant().getProfil() + ")\n";
        q += "Logement  : " + b.getLogement().getNumero() + " - " + b.getLogement().getType() + " " + b.getLogement().getSurface() + " m²";
        if (b.getLogement().isMeuble()) q += " meublé";
        q += "\n";
        q += "Période   : " + mois + "\n";
        q += "Options   : " + listerOptions(b) + "\n";
        q += "------------------------------------------\n";
        q += "Loyer HC  : " + Utils.euros(b.getLoyerHorsCharges()) + "\n";
        q += "Charges   : " + Utils.euros(b.getCharges()) + "\n";
        if (p.penalite > 0) {
            q += "Pénalité  : " + Utils.euros(p.penalite) + "\n";
        }
        q += "Total dû  : " + Utils.euros(p.du) + "\n";
        q += "Payé le   : " + Utils.date(p.date) + " : " + Utils.euros(p.montant) + "\n";
        if (p.montant < p.du) {
            q += "RESTE DÛ  : " + Utils.euros(Utils.arrondir(p.du - p.montant)) + "\n";
        }
        q += "==========================================";
        return q;
    }
}