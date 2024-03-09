package fr.uga.iut2.genevent.modele;

/**
 * Enumeration du type de l'épreuve. Une épreuve peut être une épreuve de course, de labourage, d'obstacle ou de tractage.
 */
public enum TypeEpreuve {
    COURSE, LABOURAGE, OBSTACLE, TRACTAGE;

    @Override
    public String toString() {
        if(this.equals(COURSE)){
            return "Course";
        } else if (this.equals(LABOURAGE)) {
            return "Labourage";
        } else if (this.equals(OBSTACLE)) {
            return "Obstacle";
        } else {
            return "Tractage";
        }
    }
}
