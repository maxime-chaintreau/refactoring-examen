package fr.efrei.residence;

public interface TarifLogement {

    double CHARGES = 35;
    double COEF_MENAGE = 1.08;

    double loyerDeBase(Logement l);

    default double coefMenage() {
        return COEF_MENAGE;
    }

    default boolean fibreIncluse() {
        return false;
    }

    default double charges() {
        return CHARGES;
    }

    static TarifLogement pour(TypeLogement type) {
        return switch (type) {
            case STUDIO -> new TarifStudio();
            case T1 -> new TarifT1();
            case T2 -> new TarifT2();
            case COLOCATION -> new TarifColocation();
            case DUPLEX -> new TarifDuplex();
        };
    }
}