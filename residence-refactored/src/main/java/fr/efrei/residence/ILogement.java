package fr.efrei.residence;

/**
 * Tout ce qu'on peut faire avec un logement de la résidence.
 */
public interface ILogement {

    String getNumero();

    TypeLogement getType();

    double getSurface();

    int getEtage();

    boolean isMeuble();

    boolean estLibre();

    boolean estAccessiblePMR();

    void loger(Etudiant e);

    void liberer(Etudiant e);

    int getNombrePlaces();

    int getPlacesOccupees();

    double getSurfaceBalcon();

    void entretienApresDepart();
}
