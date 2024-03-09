package fr.uga.iut2.genevent.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandierTest {

    @Test
    void setUnStand() {
        Standier standier=new Standier("flavien", null,"ezfuifhezuif@gmail.com", "05050505");
        standier.setPrenom("momo");
        assertEquals(standier.getPrenom(),"Momo");
    }
}