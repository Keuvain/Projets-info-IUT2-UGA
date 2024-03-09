package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.exeption.NomExeption;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TournoiTest{

    @Test
    void definirSurfaceMinimum() throws NomExeption {
        Tournoi tournoi = new Tournoi();
        tournoi.setNbParticipants(2);
        tournoi.setNbPlacesSpec(60);
        Participant participant1 = new Participant("baba", "boubou", "ba@bou.fr", "0677889910");
        Participant participant2 = new Participant("tata", "toto", "ta@to.fr", "0678910111");
        Stand stand1 = new Stand("Friandises", TypeStand.NOURRITURE, 3.4, null);
        Stand stand2 = new Stand("Boire un coup", TypeStand.BUVETTE, 2.7, null);
        Epreuve epreuve1 = new Epreuve(TypeEpreuve.COURSE, LocalTime.of(1,20), LocalDate.now(), LocalTime.now());
        Epreuve epreuve2 = new Epreuve(TypeEpreuve.LABOURAGE, LocalTime.of(0,40), LocalDate.now(), LocalTime.of(14, 30));
        Epreuve epreuve3 = new Epreuve(TypeEpreuve.COURSE, LocalTime.of(1,0), LocalDate.now(), LocalTime.of(15,15));
        tournoi.addParticipants(participant1);
        tournoi.addParticipants(participant2);
        tournoi.addStand(stand1);
        tournoi.addStand(stand2);
        tournoi.addEpreuve(epreuve1);
        tournoi.addEpreuve(epreuve2);
        tournoi.addEpreuve(epreuve3);
        tournoi.definirSurfaceMinimum();
        assertEquals(tournoi.getSurfaceMinimum(), 3764);
    }
}