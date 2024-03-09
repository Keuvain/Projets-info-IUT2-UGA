package fr.uga.iut2.genevent.modele;

/**
 * Enumeration du tpye de stand. Une stand peut être un stand de nourriture, de produits divers, une  buvette, ou un stand d'activités.
 */
public enum TypeStand {
    NOURRITURE, PRODUITSDIVERS, BUVETTE, ACTIVITES;

    @Override
    public String toString() {
        if(this.equals(NOURRITURE)){
            return "Nourriture";
        } else if (this.equals(PRODUITSDIVERS)) {
            return "Produits divers";
        } else if (this.equals(BUVETTE)) {
            return "Buvette";
        } else {
            return "Activités";
        }
    }
}
