package fr.uga.iut2.genevent.modele;

import java.io.Serializable;

public class Standier extends Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    private Stand unStand;

    /**
     * getteur qui retourne un stand
     * @return unStand
     */
    public Stand getUnStand() {
        return unStand;
    }

    /**
     * setteur qui dédinit un stand
     * @param unStand
     */
    public void setUnStand(Stand unStand) {
        this.unStand = unStand;
    }

    /**
     * Définit un Standier à partir d'une Personne et d'un Stand
     * @param nom
     * @param prenom
     * @param email
     * @param numTel
     */
    public Standier(String nom, String prenom, String email, String numTel){
        super(email, nom, prenom, numTel);
    }
}
