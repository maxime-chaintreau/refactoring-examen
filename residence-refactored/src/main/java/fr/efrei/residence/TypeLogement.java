package fr.efrei.residence;

import java.util.Optional;

public enum TypeLogement {
    STUDIO, T1, T2, COLOCATION;

    public static Optional<TypeLogement> depuis(String code) {
        for (TypeLogement t : values()) {
            if (t.name().equals(code)) return Optional.of(t);
        }
        return Optional.empty();
    }
}