package fr.efrei.residence;

public class MajorationMeuble extends DecorateurLoyer {

    static final double COEF_MEUBLE = 1.15;

    public MajorationMeuble(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() * COEF_MEUBLE;
    }
}