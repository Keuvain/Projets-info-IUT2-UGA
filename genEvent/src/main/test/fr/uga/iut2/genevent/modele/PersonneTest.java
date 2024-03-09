package fr.uga.iut2.genevent.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

    @Test
    void setNom() {
        Personne unePers = new Personne("mamasita@mama.com", null,"Flavien", "05050505");
        unePers.setNom("momo");
        assertEquals(unePers.getNom(),"MOMO");
    }

    @Test
    void setPrenom() {
        Personne unePers = new Personne("mamasita@mama.com", null,null, "05050505");
        unePers.setPrenom("momo");
        assertEquals(unePers.getPrenom(),"Momo");
    }

    @Test
    void getEmail() {
        Personne unePers = new Personne("mamasita@fsfs.fr", "Riondet", "Flavien", "05050505");
    }
}