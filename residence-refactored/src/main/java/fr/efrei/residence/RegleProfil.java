package fr.efrei.residence;

import java.util.Optional;

public interface RegleProfil {

    Optional<String> verifierInscription(int echelonBourse, String telephone);

    double tauxRemise(int echelonBourse);

    boolean estSolvable(double ressourcesMensuelles, double loyerChargesComprises);

    double caution(double loyerHorsCharges);

    double fraisDossier();

    double penaliteRetard(double loyerChargesComprises);

    int joursPreavis();

    boolean recoitCopieEmailEnAnglais();

    static RegleProfil pour(Profil profil) {
        return switch (profil) {
            case STANDARD -> new RegleStandard();
            case BOURSIER -> new RegleBoursier();
            case ALTERNANT -> new RegleAlternant();
            case INTERNATIONAL -> new RegleInternational();
        };
    }
}