package fr.efrei.residence;

import java.util.List;

public interface BailRepository {

    void sauvegarder(Bail b);

    void mettreAJour(Bail b);

    Bail trouver(String id);

    List<Bail> tous();
}