package fr.efrei.residence;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Un test unitaire par couche du loyer. Chaque décorateur est testé seul autour d'un loyer fixe de 100 €.
 */
class DecorateursLoyerTest {

    private static final double CENTIME = 0.001;

    private final Loyer cent = () -> 100.0;

    @Test
    void loyerDeBaseStudioAppliqueLeMinimum() {
        Logement petitStudio = new Studio("X01", 20, 1, false); 
        assertEquals(350.0, new LoyerDeBase(petitStudio).montant(), CENTIME);
    }

    @Test
    void loyerDeBaseT1() {
        Logement t1 = new T1("X02", 30, 1, false);
        assertEquals(440.0, new LoyerDeBase(t1).montant(), CENTIME);
    }

    @Test
    void meubleMajoreDe15Pourcent() {
        assertEquals(115.0, new MajorationMeuble(cent).montant(), CENTIME);
    }

    @Test
    void rezDeChausseeMinoreDe5Pourcent() {
        assertEquals(95.0, new MinorationRezDeChaussee(cent).montant(), CENTIME);
    }

    @Test
    void menageAppliqueLeCoefficientRecu() {
        assertEquals(108.0, new OptionMenage(cent, 1.08).montant(), CENTIME);
        assertEquals(104.0, new OptionMenage(cent, 1.04).montant(), CENTIME);
    }

    @Test
    void parkingAjoute45Euros() {
        assertEquals(145.0, new OptionParking(cent).montant(), CENTIME);
    }

    @Test
    void laverieAjoute12Euros() {
        assertEquals(112.0, new OptionLaverie(cent).montant(), CENTIME);
    }

    @Test
    void fibreAjoute20Euros() {
        assertEquals(120.0, new OptionFibre(cent).montant(), CENTIME);
    }

    @Test
    void assuranceAjoute9Euros() {
        assertEquals(109.0, new OptionAssurance(cent).montant(), CENTIME);
    }

    @Test
    void remiseAppliqueLeTauxRecu() {
        assertEquals(90.0, new RemiseProfil(cent, 0.10).montant(), CENTIME);
        assertEquals(100.0, new RemiseProfil(cent, 0).montant(), CENTIME);
    }

    @Test
    void lOrdreCompteLeMenageAvantLesPrixFixes() {
        Loyer loyer = new OptionParking(new OptionMenage(cent, 1.08));
        assertEquals(153.0, loyer.montant(), CENTIME);
    }

    @Test
    void lAssemblageNAjoutePasLaFibreEnColocation() {
        Logement coloc = new Colocation("X03", 60, 1, false, 3);
        double sansFibre = new CalculateurLoyer().calculerLoyer(coloc, Profil.STANDARD, 0, EnumSet.noneOf(Option.class));
        double avecFibre = new CalculateurLoyer().calculerLoyer(coloc, Profil.STANDARD, 0, EnumSet.of(Option.FIBRE));
        assertEquals(sansFibre, avecFibre, CENTIME);
    }
}