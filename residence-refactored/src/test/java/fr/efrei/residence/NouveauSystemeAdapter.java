package fr.efrei.residence;

import fr.efrei.residence.contrat.Canal;
import fr.efrei.residence.contrat.DemandeAttribution;
import fr.efrei.residence.contrat.DemandeDevis;
import fr.efrei.residence.contrat.DemandeInscription;
import fr.efrei.residence.contrat.DemandeLogement;
import fr.efrei.residence.contrat.EtatBail;
import fr.efrei.residence.contrat.EtatLogement;
import fr.efrei.residence.contrat.Notification;
import fr.efrei.residence.contrat.Option;
import fr.efrei.residence.contrat.StatutBail;
import fr.efrei.residence.contrat.SystemeResidence;
import fr.efrei.residence.contrat.TypeLogement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.EnumSet;

/**
 * Branche le code historique sur les tests de contrat.
 * <p>
 * Tout le « bricolage » nécessaire pour tester l'ancien code (état statique à vider,
 * conversion des codes {@code String}, booléens d'options…) est confiné ici.
 */
public class NouveauSystemeAdapter implements SystemeResidence {

    private Residence residence;
    private Database db;
    private EmailService email;
    private SmsService sms;
    private CourrierService courrier;
    private CalculateurLoyer calculateur;
    private GenerateurQuittance quittances;
private GestionResidence gestion;

    @Override
    public void reinitialiser() {
        residence = new Residence();
        db = new Database();
        email = new EmailService();
        sms = new SmsService();
        courrier = new CourrierService();
        calculateur = new CalculateurLoyer();
        quittances = new GenerateurQuittance();
        ServiceNotification notifications = new ServiceNotification(
                new NotificationEmail(email), new NotificationSms(sms), new NotificationCourrier(courrier));
        gestion = new GestionResidence(
                new ServiceInscription(db),
                new ServiceParc(residence),
                new ServiceAttribution(residence, db, calculateur, notifications),
                new ServiceBail(db, notifications),
                new ServiceRapport(residence, db, sms, courrier));
    }

    @Override
    public Optional<String> inscrire(DemandeInscription d) {
        Etudiant e = gestion.inscrireEtudiant(d.nom(), d.prenom(), d.email(), d.telephone(), d.adresse(),
                versProfil(d.profil()), d.echelonBourse(), d.ressourcesMensuelles(), d.pmr(), fr.efrei.residence.Canal.valueOf(d.canalPrefere().name()));
        return Optional.ofNullable(e).map(Etudiant::getId);
    }

    @Override
    public boolean ajouterLogement(DemandeLogement d) {
        int param = d.type() == TypeLogement.COLOCATION ? d.places() : d.surfaceBalcon();
        return gestion.ajouterLogement(versType(d.type()), d.numero(), d.surface(), d.etage(), d.meuble(), param) != null;
    }

    @Override
    public Optional<EtatLogement> logement(String numero) {
        Logement l = residence.trouver(numero);
        if (l == null) {
            return Optional.empty();
        }
        int occupants = l.getPlacesOccupees();
        return Optional.of(new EtatLogement(l.getNumero(), TypeLogement.valueOf(l.getType().name()), l.getNombrePlaces(),
                occupants, l.estAccessiblePMR()));
    }

    @Override
    public OptionalDouble devisLoyer(DemandeDevis d) {
        Logement l = residence.trouver(d.numeroLogement());
        if (l == null) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(calculateur.calculerLoyer(l, versProfil(d.profil()), d.echelonBourse(), versOptions(d.options())));
    }

    @Override
    public Optional<String> attribuer(DemandeAttribution d) {
        Etudiant e = db.trouverParId(d.refEtudiant());
        if (e == null) {
            return Optional.empty();
        }
        Bail b = gestion.attribuerLogement(e, versType(d.type()), versOptions(d.options()), d.dateSignature());
        return Optional.ofNullable(b).map(Bail::getId);
    }

    @Override
    public Optional<EtatBail> consulter(String idBail) {
        Bail b = db.trouver(idBail);
        if (b == null) {
            return Optional.empty();
        }
        return Optional.of(new EtatBail(b.getId(), b.getLogement().getNumero(), StatutBail.valueOf(b.getStatut().name()),
                b.getLoyerHorsCharges(), b.getCharges(), b.getLoyerChargesComprises(), b.getCaution(),
                b.getFraisDossier(), b.getDateFin(), b.soldeDu, b.cautionRestituee));
    }

    @Override
    public boolean entrer(String idBail, LocalDate date) {
        return gestion.entrerDansLesLieux(idBail, date);
    }

    @Override
    public boolean payer(String idBail, YearMonth mois, LocalDate date, double montant) {
        return gestion.payerLoyer(idBail, mois.toString(), date, montant);
    }

    @Override
    public boolean donnerPreavis(String idBail, LocalDate date) {
        return gestion.donnerPreavis(idBail, date);
    }

    @Override
    public boolean sortir(String idBail, LocalDate date, double retenuesDegats) {
        return gestion.sortir(idBail, date, retenuesDegats);
    }

    @Override
    public boolean annuler(String idBail) {
        return gestion.annulerBail(idBail);
    }

    @Override
    public Optional<String> quittance(String idBail, YearMonth mois) {
        return Optional.ofNullable(db.trouver(idBail)).map(b -> quittances.generer(b, mois.toString()));
    }

    @Override
    public void relancerImpayes() {
        gestion.relancerImpayes();
    }

    @Override
    public String rapport() {
        return gestion.genererRapport();
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> notifications = new ArrayList<>();
        for (String[] mail : email.getJournal()) {
            notifications.add(new Notification(Canal.EMAIL, mail[0], mail[1]));
        }
        for (String[] texto : sms.getJournal()) {
            notifications.add(new Notification(Canal.SMS, texto[0], texto[1]));
        }
        for (String[] lettre : courrier.getJournal()) {
            notifications.add(new Notification(Canal.COURRIER, lettre[0], lettre[1]));
        }
        return notifications;
    }

    private static Set<fr.efrei.residence.Option> versOptions(Set<Option> options) {
        Set<fr.efrei.residence.Option> resultat = EnumSet.noneOf(fr.efrei.residence.Option.class);
        for (Option o : options) {
            resultat.add(fr.efrei.residence.Option.valueOf(o.name()));
        }
        return resultat;
    }

    private static fr.efrei.residence.Profil versProfil(fr.efrei.residence.contrat.Profil p) {
        return fr.efrei.residence.Profil.valueOf(p.name());
    }

    private static fr.efrei.residence.TypeLogement versType(TypeLogement t) {
        return fr.efrei.residence.TypeLogement.valueOf(t.name());
    }
}
