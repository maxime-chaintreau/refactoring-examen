package fr.efrei.residence;

public class OptionFibre extends DecorateurLoyer {

    static final double PRIX_FIBRE = 20;

    public OptionFibre(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() + PRIX_FIBRE;
    }
}