package fr.uga.iut2.genevent.modele;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe définissant ce qu'est une épreuve d'un tournoi de tracteur. Une épreuve est définie à partir d'un type d'épreuve et d'une durée.
 * Les types d'épreuve sont définis dans l'énumeration TypeEpreuve et une durée est composée d'une heure et d'une valeur en minutes.
 */
public class Epreuve implements Serializable {

    private static final long serialVersionUID = 1L;

    private TypeEpreuve type;

    private LocalTime duree;

    private LocalDate date ;

    private LocalTime horaire ;
    //contructeur

    /**
     * Définit une épreuve à partir d'un type issu de l'énumération TypeEpreuve, une heure et une valeur de minutes pour les combiner en une durée d'épreuve
     * en heures et minutes
     * @param type type de l'épreuve définit dans TypeEpreuve
     */
    public Epreuve(TypeEpreuve type, LocalTime duree,LocalDate date,LocalTime horaire){
        this.type = type;
        this.duree = duree ;
        this.date = date ;
        this.horaire = horaire ;
    }

    //methodes
    /**
     * getter du type de l'épreuve
     * @return type de l'épreuve
     */
    public TypeEpreuve getType() {
        return type;
    }

    /**
     * setter du type de l'épreuve
     * @param type noouveau type de l'épreuve
     */
    public void setType(TypeEpreuve type) {
        this.type = type;
    }

    /**
     * getter de la durée
     * @return la durée de l'épreuve en heure et minute
     */
    public LocalTime getDuree() {
        return duree;
    }

    /**
     * setter de la durée l'épreuve.
     * Combine une heure et une valeur de minute pour obtenir une nouvelle durée d'épreuve en heures et minutes
     */
    public void setDuree(LocalTime duree){
        this.duree = duree;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHoraire() {
        return horaire;
    }

    public void setHoraire(LocalTime horaire) {
        this.horaire = horaire;
    }

    public String afficherDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return date.format(formatter);
    }

    public String afficherDuree() {
        return duree.toString();
    }

    public String afficherHoraire() {
        return horaire.toString();
    }

}
