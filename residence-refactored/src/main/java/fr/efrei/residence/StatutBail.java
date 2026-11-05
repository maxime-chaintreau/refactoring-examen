package fr.efrei.residence;

public enum StatutBail {
    SIGNE, ACTIF, PREAVIS, TERMINE, ANNULE;

    public boolean estEnCours() {
        return this == ACTIF || this == PREAVIS;
    }
}