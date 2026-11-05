package fr.efrei.residence;

import java.util.Optional;

public class RegleBoursier extends RegleStandard {

    static final int ECHELON_MIN = 0;
    static final int ECHELON_MAX = 7;
    static final double REMISE_PAR_ECHELON = 0.05;
    static final double REMISE_MAX = 0.30;
    static final int PREAVIS_JOURS_REDUIT = 15;

    @Override
    public Optional<String> verifierInscription(int echelonBourse, String telephone) {
        if (echelonBourse < ECHELON_MIN || echelonBourse > ECHELON_MAX) {
            return Optional.of("échelon de bourse invalide");
        }
        return Optional.empty();
    }

    @Override
    public double tauxRemise(int echelonBourse) {
        return Math.min(echelonBourse * REMISE_PAR_ECHELON, REMISE_MAX);
    }

    @Override
    public boolean estSolvable(double ressourcesMensuelles, double loyerChargesComprises) {
        return true; // garantie Visale
    }

    @Override
    public double caution(double loyerHorsCharges) {
        return 0;
    }

    @Override
    public double fraisDossier() {
        return 0;
    }

    @Override
    public double penaliteRetard(double loyerChargesComprises) {
        return 0;
    }

    @Override
    public int joursPreavis() {
        return PREAVIS_JOURS_REDUIT;
    }
}