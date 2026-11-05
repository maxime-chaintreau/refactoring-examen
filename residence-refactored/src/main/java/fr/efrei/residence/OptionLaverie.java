package fr.efrei.residence;

public class OptionLaverie extends DecorateurLoyer {

    static final double PRIX_LAVERIE = 12;

    public OptionLaverie(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() + PRIX_LAVERIE;
    }
}