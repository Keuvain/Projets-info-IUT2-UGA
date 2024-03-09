package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GenEvent implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

//    private final HashMap<String, Personne> utilisateurs;  // association qualifiée par l'email
    private final HashMap<String, Tournoi> tournoisMap;  // association qualifiée par le nom

    public GenEvent() {
        this.tournoisMap = new HashMap<>();
    }


    /**
     * Ajouter un tournoi à la plateforme.
     * @param unTournoi tourner à ajouter à la liste
     */
    public void addTournoi(Tournoi unTournoi) {
        tournoisMap.put(unTournoi.getNom(),unTournoi);
    }

    /**
     * Supprime un tournoi à la plateforme.
     * @param unTournoi
     */
    public void suppTournoi(Tournoi unTournoi){tournoisMap.remove(unTournoi.getNom());}

    /**
     * Retourner la liste des tournois enregistrés sur la plateforme.
     * @return la liste des tournois
     */
    public HashMap<String, Tournoi>  getListeTournois(){
        return tournoisMap ;

    }

    /**
     * Retourner un tournoi de la liste à partir de son nom.
     * @param nomTournoi nom du tournoi
     * @return le tournoi s'il est trouvé, null sinon
     */
    public Tournoi getTournoi(String nomTournoi) {
        return tournoisMap.get(nomTournoi);
    }

    //    public boolean ajouteUtilisateur(String email, String nom, String prenom, String numTel) {
//        if (this.utilisateurs.containsKey(email)) {
//            return false;
//        } else {
//            this.utilisateurs.put(email, new Personne(email, nom, prenom, numTel));
//            return true;
//        }
//    }




}
