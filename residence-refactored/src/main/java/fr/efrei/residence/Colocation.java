package fr.efrei.residence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Colocation extends Logement {

    private final int places;
    private final List<Etudiant> colocataires = new ArrayList<>();

    public Colocation(String numero, double surface, int etage, boolean meuble, int places) {
        super(numero, TypeLogement.COLOCATION, surface, etage, meuble);
        this.places = places;
    }

    @Override
    public boolean estLibre() {
        return colocataires.size() < places;
    }

    @Override
    public void loger(Etudiant e) {
        if (!estLibre()) {
            throw new IllegalStateException("Colocation " + numero + " complète");
        }
        colocataires.add(e);
    }

    @Override
    public void liberer(Etudiant e) {
        colocataires.remove(e);
    }

    @Override
    public int getNombrePlaces() {
        return places;
    }

    @Override
    public int getPlacesOccupees() {
        return colocataires.size();
    }

    public List<Etudiant> getColocataires() {
        return Collections.unmodifiableList(colocataires);
    }

    @Override
    public void entretienApresDepart() {
        System.out.println("[ENTRETIEN] Nettoyage des parties communes de la colocation " + numero);
    }
}
