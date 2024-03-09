package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Tournoi;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class Etape5Ctrl extends AbstractCtrl{

    @FXML
    public Button enregistrerBtn ;
    public Label lbNom ;
    public Label lbDateLieu;;
    public Label lbSpectateurs ;
    public Label lbParticipants;
    public Label lbStands ;
    public Label lbEpreuves ;
    public Label lbSuperficie ;
    public Label lbIntervenants ;
    public Label lbPrixSpectateurs;
    public Label lbPrixParticipants ;
    public Label lbPrixMLineaire ;
    public Label lbPrixRecompense ;

    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);
        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        afficherInfos();
    }



    @FXML
    private void enregistrerButtonAction(ActionEvent event) throws IOException {
        genEvent.addTournoi(this.tournoi);
        JavaFXGUI gui = new JavaFXGUI(new Controleur(genEvent));
        gui.informerUtilisateur("Le tournoi a bien été enregistré", true);

        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream fxmlStream = new FileInputStream("src/main/resources/fr/uga/iut2/genevent/vue/main-view.fxml");
        Scene scene = new Scene(fxmlLoader.load(fxmlStream));

        AccueilCtrl ctrl = fxmlLoader.getController();

        ctrl.initVue(this.genEvent,null);

        Stage fenetre = (Stage) enregistrerBtn.getScene().getWindow();

        fenetre.setScene(scene);
        fenetre.show();
        Logging.LOGGER.log(Level.INFO, "Le tournoi a été enregistré");

    }

    @FXML
    private void retourButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream fxmlStream = new FileInputStream("src/main/resources/fr/uga/iut2/genevent/vue/Creer-tournoi-004.fxml");
        Scene scene = new Scene(fxmlLoader.load(fxmlStream));

        Etape4Ctrl ctrl = fxmlLoader.getController();
        ctrl.initVue(this.genEvent,this.tournoi);

        Stage fenetre = (Stage) enregistrerBtn.getScene().getWindow();

        fenetre.setScene(scene);
        fenetre.show();
        Logging.LOGGER.log(Level.INFO, "Retour à l'étape 4 et enregistrement des informations");
    }

    private void afficherInfos() {
        tournoi.optimiserTournoi();

        lbNom.setText("Tournoi " + tournoi.getNom());

        if (tournoi.getDateDebut().isEqual(tournoi.getDateFin())){
            lbDateLieu.setText("le " + tournoi.afficherDateDebut());
        } else {
            lbDateLieu.setText("du " + tournoi.afficherDateDebut() + " au " + tournoi.afficherDateFin());
        }
        lbDateLieu.setText(lbDateLieu.getText()+ "à " + tournoi.getLieu());

        lbSpectateurs.setText(tournoi.getNbPlacesSpec() + " spectateurs");
        lbParticipants.setText(tournoi.getNbParticipants() + " participants");

        lbStands.setText(tournoi.getStands().size() + " stands" );
        lbEpreuves.setText(tournoi.getEpreuves().size() + " épreuves");

        lbSuperficie.setText(tournoi.getSurfaceMinimum() + " mètres carrés de surface nécessaire (coût moyen de " + tournoi.getPrixTerrain() + " €");

        lbIntervenants.setText(tournoi.getNbAgentSecuriteReco() + " agents de sécurité et " + tournoi.getNbSecouristeReco() + " secouristes recommandés");

        lbPrixSpectateurs.setText(tournoi.getPrixPlacesSpec() + " € pour le spectateur");
        lbPrixParticipants.setText(tournoi.getPrixParticipants() + " € pour le participant");

        lbPrixMLineaire.setText(tournoi.getPrixMetreLineaire() + " € le mètre linéaire");
        lbPrixRecompense.setText(tournoi.getRecompense() + " € la valeur de la récompense ");
    }
}
