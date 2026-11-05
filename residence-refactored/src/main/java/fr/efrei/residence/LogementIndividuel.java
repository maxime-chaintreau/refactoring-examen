package fr.efrei.residence;

public abstract class LogementIndividuel extends Logement {

    private Etudiant occupant;

    protected LogementIndividuel(String numero, TypeLogement type, double surface, int etage, boolean meuble) {
        super(numero, type, surface, etage, meuble);
    }

    public Etudiant getOccupant() {
        return occupant;
    }

    @Override
    public boolean estLibre() {
        return occupant == null;
    }

    @Override
    public void loger(Etudiant e) {
        if (occupant != null) {
            throw new IllegalStateException("Logement " + numero + " déjà occupé");
        }
        occupant = e;
    }

    @Override
    public void liberer(Etudiant e) {
        occupant = null;
    }

    @Override
    public int getNombrePlaces() {
        return 1;
    }

    @Override
    public int getPlacesOccupees() {
        return occupant == null ? 0 : 1;
    }
}