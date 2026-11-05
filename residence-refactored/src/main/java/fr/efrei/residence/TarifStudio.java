package fr.efrei.residence;

public class TarifStudio implements TarifLogement {

    static final double PRIX_M2 = 14;
    static final double LOYER_MINIMUM = 350;

    @Override
    public double loyerDeBase(Logement l) {
        return Math.max(l.getSurface() * PRIX_M2, LOYER_MINIMUM);
    }
}