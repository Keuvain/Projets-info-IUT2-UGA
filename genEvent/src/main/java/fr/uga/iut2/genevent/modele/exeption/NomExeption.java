package fr.uga.iut2.genevent.modele.exeption;

public class NomExeption extends Exception {
    public NomExeption(){

    }
    public String toString(){
        return "Un nom ne peut pas être null ou vide";
    }
}
