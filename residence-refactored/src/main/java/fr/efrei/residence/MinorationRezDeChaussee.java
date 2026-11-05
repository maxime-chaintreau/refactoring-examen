package fr.efrei.residence;

public class MinorationRezDeChaussee extends DecorateurLoyer {

    static final double COEF_REZ_DE_CHAUSSEE = 0.95;

    public MinorationRezDeChaussee(Loyer loyer) {
        super(loyer);
    }

    @Override
    public double montant() {
        return loyer.montant() * COEF_REZ_DE_CHAUSSEE;
    }
}