package fr.efrei.residence;

import java.time.LocalDate;
import java.util.Set;

/**
 * Service principal de la résidence : inscriptions, attributions, baux, loyers, préavis, sorties.
 */
public class GestionResidence {
    
    private final ServiceInscription inscriptions;
    private final ServiceParc parc;
    private final ServiceAttribution attributions;
    private final ServiceBail baux;
    private final ServiceRapport rapport;

    public GestionResidence(ServiceInscription inscriptions, ServiceParc parc, ServiceAttribution attributions,
                            ServiceBail baux, ServiceRapport rapport) {
        this.inscriptions = inscriptions;
        this.parc = parc;
        this.attributions = attributions;
        this.baux = baux;
        this.rapport = rapport;
    }

    // ------------------------------------------------------------------ étudiants

    public Etudiant inscrireEtudiant(String nom, String prenom, String email, String telephone, String adresse,
                                     Profil profil, int echelonBourse, double ressources, boolean pmr,
                                     Canal canalPrefere) {
        return inscriptions.inscrire(nom, prenom, email, telephone, adresse, profil, echelonBourse, ressources, pmr,
                canalPrefere);
    }

    // ------------------------------------------------------------------ logements

    public Logement ajouterLogement(TypeLogement type, String numero, double surface, int etage, boolean meuble, int param) {
        return parc.ajouterLogement(type, numero, surface, etage, meuble, param);
    }

    public Bail attribuerLogement(Etudiant e, TypeLogement typeSouhaite, Set<Option> options, LocalDate dateSignature) {
        return attributions.attribuerLogement(e, typeSouhaite, options, dateSignature);
    }

    public boolean entrerDansLesLieux(String idBail, LocalDate date) {
        return baux.entrerDansLesLieux(idBail, date);
    }

    public boolean payerLoyer(String idBail, String mois, LocalDate datePaiement, double montant) {
        return baux.payerLoyer(idBail, mois, datePaiement, montant);
    }

    public boolean donnerPreavis(String idBail, LocalDate date) {
        return baux.donnerPreavis(idBail, date);
    }

    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return baux.sortir(idBail, date, retenuesDegats);
    }

    public boolean annulerBail(String idBail) {
        return baux.annulerBail(idBail);
    }

    public void relancerImpayes() {
        baux.relancerImpayes();
    }

    public String genererRapport() {
        return rapport.generer();
    }

    public void afficherRapport() {
        rapport.afficher();
    }
}
