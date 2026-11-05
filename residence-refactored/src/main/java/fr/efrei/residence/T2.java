package fr.efrei.residence;

public class T2 extends LogementIndividuel {

    private final double surfaceBalcon;

    public T2(String numero, double surface, int etage, boolean meuble, double surfaceBalcon) {
        super(numero, TypeLogement.T2, surface, etage, meuble);
        this.surfaceBalcon = surfaceBalcon;
    }

    @Override
    public double getSurfaceBalcon() {
        return surfaceBalcon;
    }
}