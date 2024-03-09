package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.exeption.NomExeption;

import java.io.Serializable;

/**
 * Classe définissant un stand à partir de son nom, le nombre de mètre linéaire de la façade,  le type du stand et son propriétaire.
 * Les types de stands possibles sont énumérés dans TypeStand.
 */
public class Stand implements Serializable {

    private static final long serialVersionUID = 1L;
    //attributs
    private String nom;
    private double mLineaire;
    private TypeStand type;
    private Standier proprietaire;

    //constructeur

    /**
     * Définit un Stand à partir de son nom, de son type, du nombre de mètre linéaire de sa facade et son propiétaire
     * @param nom nom du stand
     * @param type type du stand
     * @param mLineaire nombre de mètre linéaires de sa facade
     * @param standier propriétaire du stand
     */
    public Stand(String nom, TypeStand type, double mLineaire, Standier standier) {
        this.nom = nom;
        this.type = type;
        this.mLineaire = mLineaire;
        this.proprietaire = standier;
    }

    //méthodes

    /**
     * getter du nom du stand
     * @return nom du stand
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter du nom du stand
     * @param nom nouveaunom du stand
     */
    public void setNom(String nom) throws NomExeption {
        if (nom==null||nom.isEmpty()){
            throw new NomExeption();
        }
        this.nom = nom;
    }

    /**
     * getter du type du stand
     * @return type du stand
     */
    public TypeStand getType() {
        return type;
    }

    /**
     * setter du type de stand
     * @param type nouveau type du stand
     */
    public void setType(TypeStand type) {
        this.type = type;
    }

    /**
     * getter du nombre de mètre linéaire de la facade
     * @return nombre de mètre linéaire de la facade
     */
    public double getmLineaire() {
        return mLineaire;
    }

    /**
     * setter du nombre de mètre linéaire de la facade
     * @param mLineaire nouveau nombre de mètre linéaire
     */
    public void setmLineaire(double mLineaire) {
        this.mLineaire = mLineaire;
    }

    /**
     * getter du propriétaire du stand
     * @return standier qui gère le stand
     */
    public Standier getStandier() {
        return proprietaire;
    }

    /**
     * setter du propriétaire du stand
     * @param standier propriétaire du stand
     */
    public void setStandier(Standier standier) {
        this.proprietaire = standier;
    }
}
