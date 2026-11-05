package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionsTest {

    private static final double CENTIME = 0.001;

    private final Loyer cent = () -> 100.0;

    private static Etudiant etudiant(Profil profil, Canal canal) {
        return new Etudiant("ETU-1", "Martin", "Alice", "alice@mail.fr", "+33601020304", "1 rue A",
                profil, 0, 3000, false, canal);
    }

    // ------------------------------------------------------------------ DUPLEX

    @Test
    void laFabriqueCreeUnDuplex() {
        Logement l = FabriqueLogement.creer(TypeLogement.DUPLEX, "D01", 40, 1, false, 0);
        assertEquals(Duplex.class, l.getClass());
        assertEquals(TypeLogement.DUPLEX, l.getType());
    }

    @Test
    void duplexLoyerDeBase() {
        // 40 m² x 12,50 + 80 = 580
        assertEquals(580.0, new LoyerDeBase(new Duplex("D01", 40, 1, false)).montant(), CENTIME);
    }

    @Test
    void duplexUnSeulOccupantEtPasDeBalcon() {
        Duplex d = new Duplex("D01", 40, 1, false);
        assertEquals(1, d.getNombrePlaces());
        assertEquals(0.0, d.getSurfaceBalcon(), CENTIME);
        assertTrue(d.estLibre());
        d.loger(etudiant(Profil.STANDARD, Canal.EMAIL));
        assertFalse(d.estLibre());
        assertEquals(1, d.getPlacesOccupees());
    }

    @Test
    void duplexDevisComplet() {
        Logement d = new Duplex("D01", 40, 1, false);
        assertEquals(580.0, new CalculateurLoyer().calculerLoyer(d, Profil.STANDARD, 0, EnumSet.noneOf(Option.class)), CENTIME);
        assertEquals(35.0, new CalculateurLoyer().calculerCharges(d), CENTIME);
    }

    // ------------------------------------------------------------------ local vélo

    @Test
    void localVeloAjoute6Euros() {
        assertEquals(106.0, new OptionLocalVelo(cent).montant(), CENTIME);
    }

    @Test
    void localVeloAppliqueApresLAssuranceEtAvantLaRemise() {
        Logement t1 = new T1("X01", 30, 1, false);
        Option[] options = {Option.ASSURANCE, Option.LOCAL_VELO};
        // standard : 440 + 9 + 6 = 455
        assertEquals(455.0, new CalculateurLoyer().calculerLoyer(t1, Profil.STANDARD, 0, EnumSet.of(options[0], options[1])), CENTIME);
        // alternant : la remise de 10 % s'applique aussi sur le local vélo : 455 x 0,90 = 409,50
        assertEquals(409.5, new CalculateurLoyer().calculerLoyer(t1, Profil.ALTERNANT, 0, EnumSet.of(options[0], options[1])), CENTIME);
    }

    @Test
    void localVeloApparaitEnDernierSurLaQuittance() {
        assertEquals("local vélo", Option.LOCAL_VELO.getLibelle());
        Option[] ordre = EnumSet.allOf(Option.class).toArray(new Option[0]);
        assertEquals(Option.LOCAL_VELO, ordre[ordre.length - 1]);
    }

    // ------------------------------------------------------------------ SPORTIF

    @Test
    void reglesDuProfilSportif() {
        RegleProfil r = RegleProfil.pour(Profil.SPORTIF);
        assertEquals(0.15, r.tauxRemise(0), CENTIME);
        assertTrue(r.estSolvable(2000, 1000));
        assertFalse(r.estSolvable(1999.99, 1000));
        assertEquals(500.0, r.caution(500), CENTIME);
        assertEquals(50.0, r.fraisDossier(), CENTIME);
        assertEquals(15, r.joursPreavis());
        assertEquals(15.0, r.penaliteRetard(300), CENTIME);
        assertEquals(10.0, r.penaliteRetard(100), CENTIME);
        assertFalse(r.recoitCopieEmailEnAnglais());
        assertFalse(r.verifierInscription(0, "0601020304").isPresent());
    }

    @Test
    void devisSportifAvecRemiseDe15Pourcent() {
        Logement t1 = new T1("X01", 30, 1, false);
        assertEquals(374.0, new CalculateurLoyer().calculerLoyer(t1, Profil.SPORTIF, 0, EnumSet.noneOf(Option.class)), CENTIME);
    }

    // ------------------------------------------------------------------ PUSH

    @Test
    void lePushEnvoieLeSujetALEmailDeLEtudiant() {
        PushService push = new PushService();
        new NotificationPush(push).envoyer(etudiant(Profil.STANDARD, Canal.PUSH), "Votre bail BAIL-001", "Bienvenue");
        assertEquals(1, push.getJournal().size());
        assertEquals("alice@mail.fr", push.getJournal().get(0)[0]);
        assertEquals("Votre bail BAIL-001", push.getJournal().get(0)[1]);
    }

    @Test
    void leServiceDeNotificationUtiliseLeCanalPush() {
        PushService push = new PushService();
        EmailService email = new EmailService();
        ServiceNotification notifications = new ServiceNotification(Map.of(
                Canal.EMAIL, new NotificationEmail(email),
                Canal.PUSH, new NotificationPush(push)));
        notifications.notifier(etudiant(Profil.STANDARD, Canal.PUSH), "Quittance 2026-09", "Merci");
        assertEquals(1, push.getJournal().size());
        assertEquals(0, email.getJournal().size());
    }

    @Test
    void unInternationalEnPushRecoitAussiLEmailEnAnglais() {
        PushService push = new PushService();
        EmailService email = new EmailService();
        ServiceNotification notifications = new ServiceNotification(Map.of(
                Canal.EMAIL, new NotificationEmail(email),
                Canal.PUSH, new NotificationPush(push)));
        notifications.notifier(etudiant(Profil.INTERNATIONAL, Canal.PUSH), "Quittance 2026-09", "Merci");
        assertEquals(1, push.getJournal().size());
        assertEquals(1, email.getJournal().size());
        assertEquals("[EN] Quittance 2026-09", email.getJournal().get(0)[1]);
    }

    @Test
    void onPeutSInscrireAvecLeCanalPushEtLeProfilSportif() {
        ServiceInscription inscriptions = new ServiceInscription(new Database());
        Etudiant e = inscriptions.inscrire("Martin", "Alice", "alice@mail.fr", null, null,
                Profil.SPORTIF, 0, 2000, false, Canal.PUSH);
        assertTrue(e != null);
        assertEquals(Profil.SPORTIF, e.getProfil());
    }
}