package fr.efrei.residence;

import java.util.Optional;

public class RegleStandard implements RegleProfil {

    static final int COEF_SOLVABILITE = 3;
    static final int MOIS_CAUTION = 1;
    static final double FRAIS_DOSSIER = 100;
    static final double TAUX_PENALITE_RETARD = 0.05;
    static final double PENALITE_MINIMUM = 10;
    static final int PREAVIS_JOURS = 30;

    @Override
    public Optional<String> verifierInscription(int echelonBourse, String telephone) {
        return Optional.empty();
    }

    @Override
    public double tauxRemise(int echelonBourse) {
        return 0;
    }

    @Override
    public boolean estSolvable(double ressourcesMensuelles, double loyerChargesComprises) {
        return ressourcesMensuelles >= loyerChargesComprises * COEF_SOLVABILITE;
    }

    @Override
    public double caution(double loyerHorsCharges) {
        return loyerHorsCharges * MOIS_CAUTION;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER;
    }

    @Override
    public double penaliteRetard(double loyerChargesComprises) {
        return Utils.arrondir(Math.max(loyerChargesComprises * TAUX_PENALITE_RETARD, PENALITE_MINIMUM));
    }

    @Override
    public int joursPreavis() {
        return PREAVIS_JOURS;
    }

    @Override
    public boolean recoitCopieEmailEnAnglais() {
        return false;
    }
}