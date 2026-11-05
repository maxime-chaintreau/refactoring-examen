package fr.efrei.residence;

public enum Option {
    MENAGE("ménage"),
    PARKING("parking"),
    LAVERIE("laverie"),
    FIBRE("fibre"),
    ASSURANCE("assurance"),
    LOCAL_VELO("local vélo");

    private final String libelle;

    Option(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}