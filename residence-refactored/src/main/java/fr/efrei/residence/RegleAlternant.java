package fr.efrei.residence;

public class RegleAlternant extends RegleStandard {

    static final double REMISE = 0.10;
    static final int COEF_SOLVABILITE_ALTERNANT = 2;
    static final int PREAVIS_JOURS_REDUIT = 15;

    @Override
    public double tauxRemise(int echelonBourse) {
        return REMISE;
    }

    @Override
    public boolean estSolvable(double ressourcesMensuelles, double loyerChargesComprises) {
        return ressourcesMensuelles >= loyerChargesComprises * COEF_SOLVABILITE_ALTERNANT;
    }

    @Override
    public int joursPreavis() {
        return PREAVIS_JOURS_REDUIT;
    }
}