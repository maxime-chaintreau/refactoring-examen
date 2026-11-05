package fr.efrei.residence;

public class TarifT2 implements TarifLogement {

    static final double PRIX_M2 = 12;
    static final double FORFAIT = 100;

    @Override
    public double loyerDeBase(Logement l) {
        return l.getSurface() * PRIX_M2 + FORFAIT;
    }
}