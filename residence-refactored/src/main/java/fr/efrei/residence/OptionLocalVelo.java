package fr.efrei.residence;

public class OptionLocalVelo extends DecorateurLoyer {

    static final double PRIX_LOCAL_VELO = 6;

    public OptionLocalVelo(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() + PRIX_LOCAL_VELO;
    }
}