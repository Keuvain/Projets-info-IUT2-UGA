package fr.uga.iut2.genevent.modele;
import fr.uga.iut2.genevent.controleur.Etape3Ctrl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    // Récupération du logger
    public static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getPackageName());

    public static void main(String[] args) {

        LOGGER.log(Level.INFO, "Informations enregistrées");
        LOGGER.log(Level.WARNING, "");
    }
}

