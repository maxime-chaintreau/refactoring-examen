package fr.efrei.residence;

public class TarifColocation implements TarifLogement {

    static final double PRIX_M2 = 11;
    static final double COEF_MENAGE_COLOCATION = 1.04;
    static final double CHARGES_COLOCATION = 25;

    @Override
    public double loyerDeBase(Logement l) {
        return l.getSurface() * PRIX_M2 / l.getNombrePlaces();
    }

    @Override
    public double coefMenage() {
        return COEF_MENAGE_COLOCATION;
    }

    @Override
    public boolean fibreIncluse() {
        return true;
    }

    @Override
    public double charges() {
        return CHARGES_COLOCATION;
    }
}