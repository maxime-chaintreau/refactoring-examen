package fr.efrei.residence;

import java.util.Optional;

public enum TypeLogement {
    STUDIO("Studios", ""),
    T1("T1", ""),
    T2("T2", ""),
    COLOCATION("Colocation", " places");

    private final String libelleRapport;
    private final String suffixeRapport;

    TypeLogement(String libelleRapport, String suffixeRapport) {
        this.libelleRapport = libelleRapport;
        this.suffixeRapport = suffixeRapport;
    }

    public String getLibelleRapport() {
        return libelleRapport;
    }

    public String getSuffixeRapport() {
        return suffixeRapport;
    }

    public static Optional<TypeLogement> depuis(String code) {
        for (TypeLogement t : values()) {
            if (t.name().equals(code)) return Optional.of(t);
        }
        return Optional.empty();
    }
}