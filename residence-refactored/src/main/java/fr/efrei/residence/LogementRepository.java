package fr.efrei.residence;

import java.util.List;

public interface LogementRepository {

    void ajouter(Logement l);

    Logement trouver(String numero);

    List<Logement> tous();
}