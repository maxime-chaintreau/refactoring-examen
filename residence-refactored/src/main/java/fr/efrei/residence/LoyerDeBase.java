package fr.efrei.residence;

public class LoyerDeBase implements Loyer {

    private final Logement logement;

    public LoyerDeBase(Logement logement) {
        this.logement = logement;
    }

    @Override
    public double montant() {
        return TarifLogement.pour(logement.getType()).loyerDeBase(logement);
    }
}