package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Participant;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public class InformationCtrl extends AbstractCtrl{

    @FXML
    public GridPane gridPaneParticipants;
    @FXML
    public GridPane gridPaneEpreuves;
    @FXML
    public GridPane gridPaneStands;
    @FXML
    public Button buttonP;
    @FXML
    public Button buttonE;
    @FXML
    public Button buttonS;

    private boolean afficheP = false;
    private boolean afficheE = false;
    private boolean afficheS = false;

    @FXML
    public Label lbListeParticipants ;
    @FXML
    public Label lbListeEpreuves ;
    @FXML
    public Label lbListeStands;
    @FXML
    public Label dD;
    @FXML
    public Label pT;
    @FXML
    public Label pM;
    @FXML
    public Label lieu;
    @FXML
    public Label recompense;
    @FXML
    public Label nbP;
    @FXML
    public Label nbS;
    @FXML
    public Label pP;
    @FXML
    public Label pS;
    @FXML
    public Label nbAg;
    public Label nbSec;
    @FXML
    public Button modifierI;
    @FXML
    public Button modifierIn;
    @FXML
    public Button modifierP;

    @FXML
    public Button ajoutP;
    @FXML
    public Button modifierE;
    @FXML
    public Button modifierS;
    @FXML
    public Button retourBtn;


    @Override
    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);

        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Tournoi tCourant = this.tournoi;
        dD.setText("Du " + tCourant.afficherDateDebut() + " au " + tCourant.afficherDateFin());
        lieu.setText("A " + tCourant.getLieu());
        pT.setText("Prix du terrain : " + tCourant.getPrixTerrain() + " €");
        pM.setText("Prix du mètre linéaire : " + tCourant.getPrixMetreLineaire() + " €");
        recompense.setText("Montant de la récompense : " + tCourant.getRecompense() + " €");

        nbAg.setText("Nombre d'agents de sécurité : " + tCourant.getNbAgentSecuriteReco());
        nbSec.setText("Nombre de secouriste : " + tCourant.getNbSecouristeReco());

        nbP.setText("Nombre de participants : " + tCourant.getNbParticipants());
        nbS.setText("Nombre de spectateurs : " + tCourant.getNbPlacesSpec());
        pP.setText("Prix place participant : " + tCourant.getPrixParticipants() + " €");
        pS.setText("Prix place spectateur : " + tCourant.getPrixPlacesSpec() + " €");

    }


    @FXML
    private void afficheParticipantsButtonAction(ActionEvent event) {
        initModele(genEvent);
        initTournoi(tournoi);
        int i = 0;
        afficheP = !afficheP;
        if (afficheP) {
            for (Map.Entry part : tournoi.getParticipants().entrySet()) {
                Participant participant = (Participant) part.getValue();
                gridPaneParticipants.setHgap(30);
                gridPaneParticipants.setVgap(30);
                gridPaneParticipants.setAlignment(Pos.BASELINE_RIGHT);
                gridPaneParticipants.setStyle("-fx-background-color: #216db5");
                gridPaneParticipants.add(new Label("Nom Complet : " + participant.getNom() + " " + participant.getPrenom()), 0, i);
                gridPaneParticipants.add(new Label("Adresse Mail : " + participant.getEmail()), 1, i);
                gridPaneParticipants.add(new Label("Num Tél : " + participant.getNumTel()), 2, i);
                MenuItem menuItem1 = new MenuItem("Modifier"); menuItem1.setId("modifierbtn" + i);
                MenuItem menuItem2 = new MenuItem("Supprimer"); menuItem2.setId("supprimerbtn" + i);
                menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            InputStream fxmlStream = new FileInputStream("src/main/resources/fr/uga/iut2/genevent/vue/participant.fxml");
                            Scene scene = new Scene(fxmlLoader.load(fxmlStream));

                            ParticipantCtrl ctrl = fxmlLoader.getController();
                            ctrl.initVue(genEvent,tournoi);

                            int indice = 0 ;
                            for (HashMap.Entry<String, Participant> set :
                                   tournoi.getParticipants().entrySet()) {
                                Participant parCourant = set.getValue();
                                if (indice == Integer.parseInt(menuItem1.getId().substring(menuItem1.getId().length()-1))){
                                    ctrl.initModifier(true,parCourant);
                                }
                                indice++;
                            }
                            Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
                            fenetre.setScene(scene);
                            fenetre.show();
                            Logging.LOGGER.log(Level.INFO, "Redirection vers la modification du participant");

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                menuItem2.setOnAction(new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {

                        ButtonType supprimer = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
                        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

                        Alert dialogC = new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir supprimer cet élément ?",supprimer,annuler);
                        dialogC.setTitle("Confirmation de suppression");
                        dialogC.setHeaderText(null);
                        Logging.LOGGER.log(Level.WARNING, "Si vous supprimez le participant, vous perdrez ses informations");

                        Optional<ButtonType> answer = dialogC.showAndWait();
                        if (answer.get() == supprimer) {
                            int indice = 0 ;
                            for (HashMap.Entry<String, Participant> set :
                                    tournoi.getParticipants().entrySet()) {
                                if (indice == Integer.parseInt(menuItem1.getId().substring(menuItem1.getId().length()-1))){
                                    gridPaneParticipants.getChildren().clear();
                                    gridPaneParticipants.setStyle("-fx-background-color: #F4F4F4");
                                    afficheP = !afficheP ;
                                    afficheParticipantsButtonAction(new ActionEvent());
                                    Logging.LOGGER.log(Level.WARNING, "Suppression du participant");
                                }
                                indice++;
                            }
                        }

                    }
                });
                MenuButton btnPlus = new MenuButton("Options", null, menuItem1, menuItem2);
                gridPaneParticipants.add(btnPlus, 3, i);
                i++;
            }
            buttonP.setText("^");
        }else{
            gridPaneParticipants.setStyle("-fx-background-color: #F4F4F4");
            gridPaneParticipants.getChildren().clear();
            buttonP.setText("v");
            }
        }


    @FXML
    private void afficheEpreuvesButtonAction(ActionEvent event) {
        initModele(genEvent);
        initTournoi(tournoi);
        int cpt = 1;
        afficheE = !afficheE;
        if (afficheE) {
            for (int i = 0; i < this.tournoi.getEpreuves().size(); i++) {

                gridPaneEpreuves.setHgap(30);
                gridPaneEpreuves.setVgap(30);
                gridPaneEpreuves.setAlignment(Pos.BASELINE_RIGHT);
                gridPaneEpreuves.setStyle("-fx-background-color: #216db5");
                gridPaneEpreuves.add(new Label("Type : " + this.tournoi.getEpreuves().get(i).getType()), 0, i);
                gridPaneEpreuves.add(new Label("Date : " + this.tournoi.getEpreuves().get(i).getDate()), 2, i);
                gridPaneEpreuves.add(new Label("Horaire : " + this.tournoi.getEpreuves().get(i).getHoraire()), 3, i);
                gridPaneEpreuves.add(new Label("Durée : " + this.tournoi.getEpreuves().get(i).getDuree()), 1, i);
//                Button btnSuppr = new Button("Supprimer");
//                gridPaneEpreuves.add(btnSuppr, 4, i);
            }
            buttonE.setText("^");
        } else {
            gridPaneEpreuves.setStyle("-fx-background-color: #F4F4F4");
            gridPaneEpreuves.getChildren().clear();
            buttonE.setText("v");
        }
    }


    @FXML
    private void afficheStandsButtonAction(ActionEvent event) {
        initModele(genEvent);
        initTournoi(tournoi);
        afficheS = !afficheS;
        Tournoi tCourant = this.tournoi;
        if (afficheS) {
            for (int i = 0; i < tCourant.getStands().size(); i++) {

                gridPaneStands.setHgap(30);
                gridPaneStands.setVgap(30);
                gridPaneStands.setAlignment(Pos.BASELINE_RIGHT);
                gridPaneStands.setStyle("-fx-background-color: #216db5");
                gridPaneStands.add(new Label("Propriétaire : " + tCourant.getStands().get(i).getStandier().getPrenom() + " " + tCourant.getStands().get(i).getStandier().getNom()), 1, i);
                gridPaneStands.add(new Label("Nom : " + tCourant.getStands().get(i).getNom()), 0, i);
                gridPaneStands.add(new Label("Type : " + tCourant.getStands().get(i).getType()), 2, i);
                gridPaneStands.add(new Label("Longueur de la facade : " + tCourant.getStands().get(i).getmLineaire() + " m"), 3, i);
//                Button btnSuppr = new Button("Supprimer");
//                gridPaneStands.add(btnSuppr, 4, i);
            }
            buttonS.setText("^");
        }else {
            gridPaneStands.setStyle("-fx-background-color: #F4F4F4");
            gridPaneStands.getChildren().clear();
            buttonS.setText("v");
        }
    }

    @FXML
    private void ModifierButtonI(ActionEvent event) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader();
            String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Modifier-tournoi.fxml";
            InputStream fxmlStream = new FileInputStream(adress);
            Parent root = fxmlLoader.load(fxmlStream);

            Modifier1Ctrl ctrl = fxmlLoader.getController();
            ctrl.initCtrl(this);
            ctrl.initVue(this.genEvent,this.tournoi);

            Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);
            fenetre.setScene(scene);
            fenetre.show();
        }

    @FXML
    private void ModifierButtonP(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Modifier-tournoi-002.fxml";
        InputStream fxmlStream = new FileInputStream(adress);
        Parent root = fxmlLoader.load(fxmlStream);

        Modifier2Ctrl ctrl = fxmlLoader.getController();
        ctrl.initCtrl(this);
        ctrl.initVue(this.genEvent,this.tournoi);

        Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        fenetre.setScene(scene);
        fenetre.show();
    }

    @FXML
    private void AjoutParticipants(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        String adress = "src/main/resources/fr/uga/iut2/genevent/vue/participant.fxml";
        InputStream fxmlStream = new FileInputStream(adress);
        Parent root = fxmlLoader.load(fxmlStream);

        ParticipantCtrl ctrl = fxmlLoader.getController();
        ctrl.initCtrl(this);
        ctrl.initVue(this.genEvent,this.tournoi);
        ctrl.initModifier(false,null);

        Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        fenetre.setScene(scene);
        fenetre.show();
    }

    @FXML
    private void ModifierEpreuves(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Modifier-tournoi-003.fxml";
        InputStream fxmlStream = new FileInputStream(adress);
        Parent root = fxmlLoader.load(fxmlStream);

        Modifier3Ctrl ctrl = fxmlLoader.getController();
        ctrl.initCtrl(this);
        ctrl.initVue(this.genEvent,this.tournoi);

        Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        fenetre.setScene(scene);
        fenetre.show();
    }

    @FXML
    private void ModifierStands(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Modifier-tournoi-004.fxml";
        InputStream fxmlStream = new FileInputStream(adress);
        Parent root = fxmlLoader.load(fxmlStream);

        Modifier4Ctrl ctrl = fxmlLoader.getController();
        ctrl.initCtrl(this);
        ctrl.initVue(this.genEvent,this.tournoi);

        Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        fenetre.setScene(scene);
        fenetre.show();
    }

    @FXML
    private void retourButtonAction(ActionEvent event) throws IOException {
        ButtonType continuer = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert dialogC = new Alert(Alert.AlertType.WARNING, "Les modifications ne seront pas enregistrées. \n Continuer ?", continuer, annuler);
        dialogC.setTitle("Annuler la modification");
        dialogC.setHeaderText(null);

        Optional<ButtonType> answer = dialogC.showAndWait();
        if (answer.get() == continuer) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream fxmlStream = new FileInputStream("src/main/resources/fr/uga/iut2/genevent/vue/main-view.fxml");
            Scene scene = new Scene(fxmlLoader.load(fxmlStream));

            AccueilCtrl ctrl = fxmlLoader.getController();
            ctrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) retourBtn.getScene().getWindow();

            fenetre.setScene(scene);
            fenetre.show();
            Logging.LOGGER.log(Level.INFO, "Retour à l'accueil");
        }
    }

    @FXML
    private void devisButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        String adress = "src/main/resources/fr/uga/iut2/genevent/vue/devis.fxml";
        InputStream fxmlStream = new FileInputStream(adress);
        Parent root = fxmlLoader.load(fxmlStream);

        DevisCtrl ctrl = fxmlLoader.getController();
        ctrl.initCtrl(this);
        ctrl.initVue(this.genEvent, this.tournoi);

        Stage fenetre = new Stage(); fenetre.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        fenetre.setScene(scene);
        fenetre.show();
    }


}
