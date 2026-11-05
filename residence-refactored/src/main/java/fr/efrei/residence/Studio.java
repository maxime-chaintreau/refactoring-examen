package fr.efrei.residence;

public class Studio extends LogementIndividuel {

    public Studio(String numero, double surface, int etage, boolean meuble) {
        super(numero, TypeLogement.STUDIO, surface, etage, meuble);
    }
}
