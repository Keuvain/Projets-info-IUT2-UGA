package fr.uga.iut2.genevent.modele;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EpreuveTest {

    @Test
    void setDuree() {
        Epreuve epreuve = new Epreuve(TypeEpreuve.LABOURAGE, LocalTime.of(0,30), LocalDate.of(2020, 11, 20), LocalTime.of(14, 20));
        epreuve.setDuree(LocalTime.of(1,20));
        assertEquals(epreuve.getDuree().toString(), "01:20");
    }

    @Test
    void setDureeErreur() {
        Epreuve epreuve = new Epreuve(TypeEpreuve.LABOURAGE, LocalTime.of(1,30), LocalDate.of(2020, 11, 20), LocalTime.of(14, 20));
        assertThrows(DateTimeException.class, () -> epreuve.setDuree(LocalTime.of(141, 2)));
    }
}