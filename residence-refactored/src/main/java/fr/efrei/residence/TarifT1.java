package fr.efrei.residence;

public class TarifT1 implements TarifLogement {

    static final double PRIX_M2 = 13;
    static final double FORFAIT = 50;

    @Override
    public double loyerDeBase(Logement l) {
        return l.getSurface() * PRIX_M2 + FORFAIT;
    }
}