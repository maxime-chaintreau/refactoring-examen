package fr.efrei.residence;

public class TarifDuplex implements TarifLogement {

    static final double PRIX_M2 = 12.50;
    static final double FORFAIT = 80;

    @Override
    public double loyerDeBase(Logement l) {
        return l.getSurface() * PRIX_M2 + FORFAIT;
    }
}