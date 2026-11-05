package fr.efrei.residence;

public class OptionMenage extends DecorateurLoyer {

    private final double coefficient;

    public OptionMenage(Loyer loyer, double coefficient) {
        super(loyer);
        this.coefficient = coefficient;
    }

    @Override
    public double montant() {
        return loyer.montant() * coefficient;
    }
}