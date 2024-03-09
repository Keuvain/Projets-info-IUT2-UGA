package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.modele.Tournoi;

import java.time.LocalDate;
import java.util.Set;


public abstract class IHM {
    /**
     * Classe conteneur pour les informations saisies à propos d'un
     * {@link fr.uga.iut2.genevent.modele.Personne}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
//    public static class InfosUtilisateur {
//        public final String email;
//        public final String nom;
//        public final String prenom;
//        public final String numTel ;
//
//        public InfosUtilisateur(final String email, final String nom, final String prenom, final String numTel) {
//            this.email = email;
//            this.nom = nom;
//            this.prenom = prenom;
//            this.numTel = numTel ;
//        }
//    }

    /**
     * Classe conteneur pour les informations saisies pour un nouvel
     * {@link Tournoi}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
    public static class InfosNouvelEvenement {
        public final String nom;
        public final LocalDate dateDebut;
        public final LocalDate dateFin;
//        public final InfosUtilisateur admin;

        public InfosNouvelEvenement(final String nom, final LocalDate dateDebut, final LocalDate dateFin /*final InfosUtilisateur admin*/) {
            assert !dateDebut.isAfter(dateFin);
            this.nom = nom;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
//            this.admin = admin;
        }
    }

    /**
     * Rend actif l'interface Humain-machine.
     *
     * L'appel est bloquant : le contrôle est rendu à l'appelant une fois que
     * l'IHM est fermée.
     *
     */
    public abstract void demarrerInteraction();

    /**
     * Affiche un message d'information à l'attention de l'utilisa·teur/trice.
     *
     * @param msg Le message à afficher.
     *
     * @param succes true si le message informe d'une opération réussie, false
     *     sinon.
     */
    public abstract void informerUtilisateur(final String msg, final boolean succes);

    /**
     * Récupère les informations à propos d'un
     * {@link fr.uga.iut2.genevent.modele.Personne}.
     *
     */
    public abstract void saisirUtilisateur();

    /**
     * Récupère les informations nécessaires à la création d'un nouvel
     * {@link Tournoi}.
     *
     * @param nomsExistants L'ensemble des noms d'évenements qui ne sont plus
     *     disponibles.
     *
     */
    public abstract void saisirNouvelEvenement(final Set<String> nomsExistants);
}
