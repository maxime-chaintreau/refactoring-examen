package fr.efrei.residence;

import java.util.Optional;

public class RegleInternational extends RegleStandard {

    static final String PREFIXE_TELEPHONE_INTERNATIONAL = "+";
    static final int MOIS_CAUTION_INTERNATIONAL = 2;
    static final double FRAIS_DOSSIER_INTERNATIONAL = 150;

    @Override
    public Optional<String> verifierInscription(int echelonBourse, String telephone) {
        if (telephone == null || !telephone.startsWith(PREFIXE_TELEPHONE_INTERNATIONAL)) {
            return Optional.of("numéro international (+XX) obligatoire");
        }
        return Optional.empty();
    }

    @Override
    public double caution(double loyerHorsCharges) {
        return loyerHorsCharges * MOIS_CAUTION_INTERNATIONAL;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER_INTERNATIONAL;
    }

    @Override
    public boolean recoitCopieEmailEnAnglais() {
        return true;
    }
}