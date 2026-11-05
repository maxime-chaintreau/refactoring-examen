package fr.efrei.residence;

public abstract class Logement implements ILogement {

    protected final String numero;
    protected final TypeLogement type;
    protected final double surface;
    protected final int etage;
    protected final boolean meuble;

public static final int ETAGE_REZ_DE_CHAUSSEE = 0;
public static final String BATIMENT_AVEC_ASCENSEUR = "A";

    protected Logement(String numero, TypeLogement type, double surface, int etage, boolean meuble) {
        this.numero = numero;
        this.type = type;
        this.surface = surface;
        this.etage = etage;
        this.meuble = meuble;
    }

    @Override public String getNumero() { return numero; }
    @Override public TypeLogement getType() { return type; }
    @Override public double getSurface() { return surface; }
    @Override public int getEtage() { return etage; }
    @Override public boolean isMeuble() { return meuble; }

    @Override
    public boolean estAccessiblePMR() {
        return etage == ETAGE_REZ_DE_CHAUSSEE || numero.startsWith(BATIMENT_AVEC_ASCENSEUR);
    }

    @Override
    public double getSurfaceBalcon() {
        return 0;
    }

    @Override
    public void entretienApresDepart() {
    }
}
