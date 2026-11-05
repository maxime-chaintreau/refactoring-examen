package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer {

    public Loyer construire(Logement l, Profil profil, int echelon, Set<Option> options) {
        TarifLogement tarif = TarifLogement.pour(l.getType());

        Loyer loyer = new LoyerDeBase(l);
        if (l.isMeuble()) loyer = new MajorationMeuble(loyer);
        if (l.getEtage() == Logement.ETAGE_REZ_DE_CHAUSSEE) loyer = new MinorationRezDeChaussee(loyer);

        if (options.contains(Option.MENAGE)) loyer = new OptionMenage(loyer, tarif.coefMenage());
        if (options.contains(Option.PARKING)) loyer = new OptionParking(loyer);
        if (options.contains(Option.LAVERIE)) loyer = new OptionLaverie(loyer);
        if (options.contains(Option.FIBRE) && !tarif.fibreIncluse()) loyer = new OptionFibre(loyer);
        if (options.contains(Option.ASSURANCE)) loyer = new OptionAssurance(loyer);
        if (options.contains(Option.LOCAL_VELO)) loyer = new OptionLocalVelo(loyer); 

        return new RemiseProfil(loyer, RegleProfil.pour(profil).tauxRemise(echelon));
    }

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, Set<Option> options) {
        return Utils.arrondir(construire(l, profil, echelon, options).montant());
    }

    public double calculerCharges(Logement l) {
        return TarifLogement.pour(l.getType()).charges();
    }
}
