package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.exeption.NomExeption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandTest {
    @Test
    void setNom() {
    assertThrows(NomExeption.class, () -> {
        new Stand(null, TypeStand.BUVETTE, 10, null);
    }, "Un intitulé ne peut pas être null.");
    
    assertThrows(NomExeption.class, () -> {
        new Stand("", TypeStand.BUVETTE, 10, null);
    }, "Un intitulé ne peut pas être vide.");
    }

}