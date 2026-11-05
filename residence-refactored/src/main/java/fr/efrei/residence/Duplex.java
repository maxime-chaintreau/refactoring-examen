package fr.efrei.residence;

public class Duplex extends LogementIndividuel {

    public Duplex(String numero, double surface, int etage, boolean meuble) {
        super(numero, TypeLogement.DUPLEX, surface, etage, meuble);
    }
}