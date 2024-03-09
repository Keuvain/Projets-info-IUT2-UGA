package fr.uga.iut2.genevent.modele;

import java.io.Serializable;

public class Participant extends Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Définit un participant à partir d'un nom, d'un prenom, d'un email et d'un numéro de téléphone.
     * @param nom
     * @param prenom
     * @param email
     * @param numTel
     */
    public Participant(String nom, String prenom, String email, String numTel){
        super(email, nom, prenom, numTel);
    }

}
