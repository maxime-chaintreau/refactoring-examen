package fr.efrei.residence;

public class RegleSportif extends RegleStandard {

    static final double REMISE = 0.15;
    static final int COEF_SOLVABILITE_SPORTIF = 2;
    static final double FRAIS_DOSSIER_SPORTIF = 50;
    static final int PREAVIS_JOURS_REDUIT = 15;

    @Override
    public double tauxRemise(int echelonBourse) {
        return REMISE;
    }

    @Override
    public boolean estSolvable(double ressourcesMensuelles, double loyerChargesComprises) {
        return ressourcesMensuelles >= loyerChargesComprises * COEF_SOLVABILITE_SPORTIF;
    }

    @Override
    public double fraisDossier() {
        return FRAIS_DOSSIER_SPORTIF;
    }

    @Override
    public int joursPreavis() {
        return PREAVIS_JOURS_REDUIT;
    }
}