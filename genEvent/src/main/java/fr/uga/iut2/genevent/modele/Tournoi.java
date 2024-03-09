package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class Tournoi implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private GenEvent genevent;
    private String nom = null;

    private String lieu = null;
    private Double prixTerrain = null;
    private LocalDate dateDebut = null;
    private LocalDate dateFin = null;
    private Integer nbPlacesSpec = null;
    private Integer nbParticipants = null;
    private Double prixPlacesSpec = null;
    private Double prixParticipants = null;
    private HashMap<String,Participant> participants = new HashMap<>();
    private ArrayList<Stand> stands = new ArrayList<>();
    private ArrayList<Epreuve> epreuves = new ArrayList<>();
    private Double prixMetreLineaire = null;
    private Integer nbAgentSecuriteReco = null;
    private Integer nbSecouristeReco = null;
    private Double recompense = null;
    private Integer surfaceMinimum = 40000;

    private Devis devis;

    // Invariant de classe : !dateDebut.isAfter(dateFin)
    //     On utilise la négation ici pour exprimer (dateDebut <= dateFin), ce
    //     qui est équivalent à !(dateDebut > dateFin).


    /**
     * Constructeur d'un tournoi. Le constructeur est vide car
     * le tournoi est construit au fur et à mesure, à travers différentes étapes.
     */
    public Tournoi() {

    }

    /**
     * Retourner le nom du tournoi
     * @return intitulé du tournoi
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Modifier le nom du tournoi
     * @param nom nouvel intitulé du tournoi
     */
    public void setNom(String nom) {
        this.nom = nom ;
    }

    /**
     * Retourner le lieu du tournoi
     * @return lieu du tournoi
     */
    public String getLieu() {
        return lieu;
    }
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    /**
     * Retourner la date du début du tournoi
     * @return date du début du tournoi
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Retourner la date de fin du tournoi
     * @return date de fin du tournoi
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * Définir la date du tournoi
     * @param dateDebut début du tournoi
     * @param dateFin fin du tournoi
     */
    public void setDate(LocalDate dateDebut, LocalDate dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin ;
    }

    public long getDuree() {
        return ChronoUnit.DAYS.between(getDateDebut(), getDateFin());
    }

    /**
     * Retourner le prix du terrain
     * @return prix du terrain
     */
    public double getPrixTerrain() {
        return prixTerrain;
    }
    public void setPrixTerrain() {
        this.prixTerrain = surfaceMinimum*0.10;
    }

    /**
     * Retourner le nombre de places spectateurs
     * @return nombre de places visiteurs
     */
    public Integer getNbPlacesSpec() {
        return nbPlacesSpec;
    }
    public void setNbPlacesSpec(Integer nbPlacesSpec) {
        this.nbPlacesSpec = nbPlacesSpec;
    }

    /**
     * Retourner le nombre de participants du tournoi
     * @return nombre de participants
     */
    public Integer getNbParticipants() {
        return nbParticipants;
    }
    public void setNbParticipants(Integer nbParticipants) {
        this.nbParticipants = nbParticipants;
    }

    /**
     * Retourner le prix de la place spectateur
     * @return prix de la place
     */
    public double getPrixPlacesSpec() {
        return prixPlacesSpec;
    }

    /**
     * Modifier le prix des places spectateurs
     * @param prixPlacesSpec nouveau prix
     */
    public void setPrixPlacesSpec(double prixPlacesSpec) {
        this.prixPlacesSpec = prixPlacesSpec;
    }

    /**
     * Retourner le prix de l'inscription des participants
     * @return prix de l'inscription
     */
    public double getPrixParticipants() {
        return prixParticipants;
    }

    /**
     * Retourner le prix de l'inscription des participants
     * @param prixParticipants prix de l'inscription
     */
    public void setPrixParticipants(double prixParticipants) {
        this.prixParticipants = prixParticipants;
    }

    /**
     * Retourner la liste des participants
     * @return ensemble des participantsindice
     */
    public HashMap<String, Participant> getParticipants() {
        return participants;
    }

    /**
     * Ajouter un participant au tournoi. Vérifie la place disponible.
     * @param participant nouveau participant
     */
    public void addParticipants(Participant participant) {
        if (verifierInscriptionPossible()) {
            participants.put(participant.getEmail(), participant);
        }
    }

    /**
     * Supprimer un participant, représenté par son adresse mail.
     * @param mail adresse mail du participant
     * @return le participant supprimé
     */
    public Participant removeParticipant(String mail) {
        return participants.remove(mail) ;
    }

    /**
     * getter qui retourne le prix du mètre lineaire
     * @return prix du mètre linéaire de stand
     */
    public double getPrixMetreLineaire() {
        return prixMetreLineaire;
    }

    /**
     * setter qui definit le prix du mètre lineaire
     * @param prixMetreLineaire nouveau prix du mètre linéaire
     */
    public void setPrixMetreLineaire(double prixMetreLineaire) {
        this.prixMetreLineaire = prixMetreLineaire;
    }


    /**
     * getter qui retourne le nombre d'agent de sécurité
     * @return nombre d'agent de sécurité
     */
    public int getNbAgentSecuriteReco() {
        return nbAgentSecuriteReco;
    }

    /**
     * setter qui definit le nombre d'agent de sécurité recommandé
     */
    public void setNbAgentSecuriteReco(int nbAgentSecuriteReco) {
        this.nbAgentSecuriteReco = nbAgentSecuriteReco;
    }

    /**
     * getter qui retourne le nombre de secouriste
     * @return nombre de secouriste
     */
    public int getNbSecouristeReco() {
        return nbSecouristeReco;
    }

    /**
     * setter qui definit le nombre de secouriste recommandé
     */
    public void setNbSecouristeReco(int nbSecouristeReco) {
        this.nbSecouristeReco = nbSecouristeReco;
    }

    /**
     * getter qui retourne la recompense
     * @return récompense pour le vainqueur
     */
    public double getRecompense() {
        return recompense;
    }

    /**
     * setter qui definit le prix de la recompense
     * @param recompense récompense pour le vainqueu du tournoi
     */
    public void setRecompense(double recompense) {
        this.recompense = recompense;
    }

    public Devis getDevis() {
        if (devis == null) {
            genererDevis();
        }
        return this.devis;
    }

    /**
     * Fait le devis du tournoi
     * @return Résumé des coûts pour réaliser le tournoi
     */
    public void genererDevis(){
        this.devis = new Devis(this);
    }


    /**
     * Permet de définir la surface nécessaire à la réalisation du tournoi.
     * La surface est calculée en fonction :
     *  - du nombre de participants (et donc de tracteurs), à hauteur de 20 mètres carrés par participant.
     *  - des stands, à hauteur du nombre de mètres linéaires du stand + 1 en supposant que chaque stand fais 3 m de profondeur.
     *  - des épreuves, à hauteur de 200 mètres carrés par participants pour le labourage, 1500 mètres carrés pour une course, 500 mètres carrés pour une course d'obstacle et 300 mètres carrés pour le tractage.
     */
    public void definirSurfaceMinimum() {
        int surfMin = 5*nbPlacesSpec;
        surfMin += nbParticipants* 20;
        for(Stand stand : stands) {
            surfMin += (Math.round(stand.getmLineaire())+1)*3;
        }
        for(Epreuve epreuve : epreuves) {
            if(epreuve.getType() == TypeEpreuve.LABOURAGE) {
                surfMin += 200*nbParticipants;
            }
            else if(epreuve.getType() == TypeEpreuve.COURSE) {
                surfMin += 1500;
            }
            else if(epreuve.getType() == TypeEpreuve.OBSTACLE) {
                surfMin += 500;
            } else {
                surfMin += 300;
            }
        }
        this.surfaceMinimum = surfMin;
        setPrixTerrain();
    }

    /**
     * Definit le nombre ideal d'intervenants
     * Selon la surface minimum nécessaire définie
     */
    private void optimisationStaff() {
        int nbPersonne = nbParticipants+nbPlacesSpec;
        int nbS = surfaceMinimum/3000;
        int nbS2 = nbPersonne/300;
        setNbSecouristeReco(Math.max(nbS2, nbS));
        int nbA = surfaceMinimum/2000;
        int nbA2 = nbPersonne/200;
        setNbAgentSecuriteReco(Math.max(nbA2, nbA));
    }

    /**
     * Optimise le prix des places de spectateurs en fonction des intervenants et du prix du terrain.
     */
    private void optimiserPrixPlaces() {
        double coutTotal = prixTerrain+((nbAgentSecuriteReco*80)+(nbSecouristeReco*90)*(dateFin.getDayOfYear()-dateDebut.getDayOfYear()));
        if (nbPlacesSpec != 0) {
            setPrixPlacesSpec(Math.min(Math.round(((coutTotal * 0.8) / nbPlacesSpec) * 100.0 ) / 100.0, 15));
        } else {
            setPrixPlacesSpec(0);

        }
    }

    /**
     * Optimise le prix des places de participants en fonction des intervenants et du terrain.
     */
    private void optimiserPrixParticipants() {
        double coutTotal = prixTerrain+((nbAgentSecuriteReco*80)+(nbSecouristeReco*90)*(dateFin.getDayOfYear()-dateDebut.getDayOfYear()));
        if (nbPlacesSpec != 0) {
            setPrixParticipants(Math.min(Math.round(((coutTotal * 0.3) / nbParticipants) * 100.0) / 100.0, 50));
        } else {
            setPrixPlacesSpec(0);
        }
    }



    /**
     * Definit le prix idéal du mètre lineaire
     */
    private void optimiserMLineaire(){
        double coutTotal = prixTerrain+((nbAgentSecuriteReco*80)+(nbSecouristeReco*90)*(dateFin.getDayOfYear()-dateDebut.getDayOfYear()));
        double mLinTot = 0;
        for (Stand stand : stands) {
            mLinTot += stand.getmLineaire();
        }
        if (mLinTot != 0) {
            setPrixMetreLineaire(Math.round(((coutTotal * 0.5) / mLinTot) * 100.0) / 100.0);
        } else {
            setPrixMetreLineaire(0);
        }
    }


    /**
     * Definit la recompense en fonction de la marge du tournoi
     */
    private void optimiserRecompense(){
        double coutTotal = prixTerrain+((nbAgentSecuriteReco*80)+(nbSecouristeReco*90)*(dateFin.getDayOfYear()-dateDebut.getDayOfYear()));
        double mLinTot = 0;
        for (Stand stand : stands) {
            mLinTot += stand.getmLineaire();
        }
        setRecompense(Math.round((coutTotal*0.05) * 100.0)/100.0);
    }

    public void optimiserTournoi() {
        definirSurfaceMinimum();
        optimisationStaff();
        optimiserMLineaire();
        optimiserPrixPlaces();
        optimiserPrixParticipants();
        optimiserRecompense();
    }

    /**
     * Vérifier s'il reste de la place pour inscrire un participant.
     * @return  true si possible, false sinon
     */
    private boolean verifierInscriptionPossible() {
        return participants.size() < nbParticipants;
    }

    public String afficherDateDebut() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return dateDebut.format(formatter);

    }

    public String afficherDateFin() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return dateFin.format(formatter);
    }

    /**
     * Getter des stands du tournois
     * @return liste des stands du tournoi
     */
    public ArrayList<Stand> getStands() {
        return stands;
    }

    /**
     * Ajoute un stand à la liste des stands
     * @param stand stand à ajouter à la liste
     */
    public void addStand(Stand stand) {
        stands.add(stand);
    }

    /**
     * Getter des épreuves du tournois
     * @return liste des épreuves du tournoi
     */
    public ArrayList<Epreuve> getEpreuves() {
        return epreuves;
    }

    /**
     * Ajoute une épreuve à la liste d'épreuve
     * @param epreuve épreuve à ajouter à la liste
     */
    public void addEpreuve(Epreuve epreuve) {
        epreuves.add(epreuve);
    }


    /**
     * Getter de la surface minimum
     * @return surface minimum de terrain requis
     */
    public int getSurfaceMinimum() {
        return surfaceMinimum;
    }


}


