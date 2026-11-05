package fr.efrei.residence;

public class RemiseProfil extends DecorateurLoyer {

    private final double taux;

    public RemiseProfil(Loyer loyer, double taux) {
        super(loyer);
        this.taux = taux;
    }

    @Override
    public double montant() {
        return loyer.montant() * (1 - taux);
    }
}