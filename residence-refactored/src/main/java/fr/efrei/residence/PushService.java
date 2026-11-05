package fr.efrei.residence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PushService {

    private final List<String[]> journal = new ArrayList<>();

    public void envoyer(String email, String sujet) {
        journal.add(new String[]{email, sujet});
        System.out.println("[PUSH] -> " + email + " : " + sujet);
    }

    public List<String[]> getJournal() {
        return Collections.unmodifiableList(journal);
    }
}