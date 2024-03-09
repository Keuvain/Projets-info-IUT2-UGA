package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;


public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private String nom;
    private String prenom;
    private String email;
    private String numTel;
    private final Map<String, Tournoi> evenementsAdministres;  // association qualifiée par le nom

    /**
     * Définit une personne à partir d'un nom, d'un prenom, d'un email et d'un numéro de téléphone.
     * @param email email de la personne au format x@y.d
     * @param nom nom de la personne
     * @param prenom prénom de la personne
     * @param numTel numéo de téléphone de la personne
     */
    public Personne(String email, String nom, String prenom, String numTel) {
        assert EmailValidator.getInstance(false, false).isValid(email);
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.evenementsAdministres = new HashMap<>();
    }

    /**
     * getteur du nom de la personne
     * @return nom de la personne
     */
    public String getNom() {
        return nom;
    }

    /**
     * setteur du nom de la personne
     * @param nom nom à attribuer à la personne
     */
    public void setNom(String nom) {
        this.nom = nom.toUpperCase();
    }

    /**
     * getteur du prénom de la personne
     * @return prenom de la personne
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * setteur du prénom de la personne
     * @param prenom prenom à attribuer à la personne
     */
    public void setPrenom(String prenom) {
        this.prenom = capitalize(prenom);
    }

    /**
     * getteur du mail de la personne
     * @return email de la personne
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getteur du numéro de telephone de la personne
     * @return numéro de téléphone de la personne
     */
    public String getNumTel() {
        return numTel;
    }

    /**
     * setteur du numéro de téléphone de la personne
     * @param numTel numéro de téléphone à attribuer à la personne
     */
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void ajouteEvenementAdministre(Tournoi evt) {
        assert !this.evenementsAdministres.containsKey(evt.getNom());
        this.evenementsAdministres.put(evt.getNom(), evt);
    }

    public static String capitalize(String chaine) {
        return chaine.toUpperCase().charAt(0) + chaine.toLowerCase().substring(1);
    }
}
