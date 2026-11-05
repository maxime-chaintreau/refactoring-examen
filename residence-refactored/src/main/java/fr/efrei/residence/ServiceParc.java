package fr.efrei.residence;

public class ServiceParc {

    private final LogementRepository logements;

    public ServiceParc(LogementRepository logements) {
        this.logements = logements;
    }

    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        if (logements.trouver(numero) != null) {
            System.out.println("Logement " + numero + " existe déjà");
            return null;
        }
        Logement l = FabriqueLogement.creer(type, numero, surface, etage, meuble, param);
        logements.ajouter(l);
        System.out.println("Logement ajouté : " + numero + " (" + type + ")");
        return l;
    }
}