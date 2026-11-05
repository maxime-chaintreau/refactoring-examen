package fr.efrei.residence;

public class CalculateurLoyer {

    static final double COEF_MEUBLE = 1.15;
    static final double COEF_REZ_DE_CHAUSSEE = 0.95;

    static final double PRIX_PARKING = 45;
    static final double PRIX_LAVERIE = 12;
    static final double PRIX_FIBRE = 20;
    static final double PRIX_ASSURANCE = 9;

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, boolean parking, boolean menage,
                                boolean laverie, boolean fibre, boolean assurance) {
        TarifLogement tarif = TarifLogement.pour(l.getType());
        RegleProfil regle = RegleProfil.pour(profil);

        double loyer = tarif.loyerDeBase(l);

        if (l.isMeuble()) loyer = loyer * COEF_MEUBLE;
        if (l.getEtage() == Logement.ETAGE_REZ_DE_CHAUSSEE) loyer = loyer * COEF_REZ_DE_CHAUSSEE;

        // options
        if (menage) loyer = loyer * tarif.coefMenage();
        if (parking) loyer = loyer + PRIX_PARKING;
        if (laverie) loyer = loyer + PRIX_LAVERIE;
        if (fibre && !tarif.fibreIncluse()) loyer = loyer + PRIX_FIBRE;
        if (assurance) loyer = loyer + PRIX_ASSURANCE;

        // remise selon le profil
        loyer = loyer * (1 - regle.tauxRemise(echelon));

        return Utils.arrondir(loyer);
    }

    public double calculerCharges(Logement l) {
        return TarifLogement.pour(l.getType()).charges();
    }
}
