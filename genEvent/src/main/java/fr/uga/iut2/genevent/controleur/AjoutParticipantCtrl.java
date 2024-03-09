package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;

public class AjoutParticipantCtrl extends AbstractCtrl{
    @FXML
    public VBox vBoxParticipant;
    @FXML
    public TextField tfNom;
    @FXML
    public TextField tfPrenom;
    @FXML
    public TextField tfEmail;
    @FXML
    public TextField tfNumTel;

    @FXML
    public Button retour;
    @FXML
    public Button valider;


    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);
        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ajoutParticipant(0);
        Button btnSupp = (Button) getNodeByRowColumnIndex(3,0,(GridPane) vBoxParticipant.getChildren().get(0));
        btnSupp.setDisable(true);
    }

    @FXML
    private void validerButtonAction(ActionEvent event) throws IOException {

        if (donneesValides()) {
            enregistrerInfos();

            FXMLLoader fxmlLoader = new FXMLLoader();
            String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Information-tournoi.fxml";
            InputStream fxmlStream = new FileInputStream(adress);
            Parent root = fxmlLoader.load(fxmlStream);

            InformationCtrl ctrl = fxmlLoader.getController();
            ctrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) valider.getScene().getWindow();
            Scene scene = new Scene(root);
            fenetre.setScene(scene);
            fenetre.show();
            Logging.LOGGER.log(Level.INFO, "Informations enregistrées");
        }
    }


    @FXML
    private void retourButtonAction(ActionEvent event) throws IOException {
        ButtonType continuer = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert dialogC = new Alert(Alert.AlertType.WARNING, "Les modifications ne seront pas enregistrées. \n Continuer ?", continuer, annuler);
        dialogC.setTitle("Annuler la modification");
        Logging.LOGGER.log(Level.WARNING, "Les informations présente à cette étape ne seront pas sauvegardés");
        dialogC.setHeaderText(null);

        Optional<ButtonType> answer = dialogC.showAndWait();
        if (answer.get() == continuer) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            String adress = "src/main/resources/fr/uga/iut2/genevent/vue/Information-tournoi.fxml";
            InputStream fxmlStream = new FileInputStream(adress);
            Parent root = fxmlLoader.load(fxmlStream);

            InformationCtrl ctrl = fxmlLoader.getController();
            ctrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) retour.getScene().getWindow();
            Scene scene = new Scene(root);
            fenetre.setScene(scene);
            fenetre.show();
            Logging.LOGGER.log(Level.INFO, "Retour au informations");
        }
    }


    /**
     * Ajout d'un formulaire pour ajouter un participant quand le boutton "+" est clicker
     * @param event
     */
    @FXML
    private void ajoutParticipantButtonAction(ActionEvent event) {
        ajoutParticipant(vBoxParticipant.getChildren().size());
    }


    private boolean donneesValides() {
        boolean verif = true ;
        for (Node node : vBoxParticipant.getChildren()) {
            GridPane partcipant = (GridPane) node ;

        }

        return verif ;
    }

    /**
     * Ajout des paramétre pour ajouter un participant et création d'un nouveau participant au tournoi
     * @param indice
     */
    private void ajoutParticipant(int indice) {
        if (vBoxParticipant.getChildren().size() > 0) {
            Button btnSupp = (Button) getNodeByRowColumnIndex(3, 0, (GridPane) vBoxParticipant.getChildren().get(0));
            btnSupp.setDisable(false);
        }
        GridPane gpParticipant = new GridPane();gpParticipant.setPrefWidth(500); gpParticipant.setMaxHeight(300);
        gpParticipant.setHgap(20); gpParticipant.setVgap(15); gpParticipant.setAlignment(Pos.BASELINE_RIGHT);
        gpParticipant.setStyle("-fx-background-color:cce5ff ; -fx-border-color: #000000");

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(15); row2.setValignment(VPos.TOP);
        gpParticipant.getRowConstraints().addAll(row1,row2,row1,row2);

        TextField tfNom = new TextField();
        tfNom.setPromptText("Nom");
        tfNom.setMaxWidth(150);
        tfNom.setId("tfNomProp" + indice);
        gpParticipant.add(tfNom, 1, 0);
        Label lErreurNomProp = new Label("");
        gpParticipant.add(lErreurNomProp, 1, 1);

        TextField tfPrenom = new TextField();
        tfPrenom.setMaxWidth(150);
        tfPrenom.setPromptText("Prénom");


        tfPrenom.setId("tfPrenomProp" + indice);
        gpParticipant.add(tfPrenom, 2, 0);
        Label lErreurPrenomProp = new Label("");
        gpParticipant.add(lErreurPrenomProp, 2, 1);

        TextField tfMail = new TextField();
        tfMail.setMaxWidth(150);
        tfMail.setPromptText("Mail");


        tfMail.setId("tfMailProp" + indice);
        gpParticipant.add(tfMail, 1, 2);
        Label lErreurMailProp = new Label("");
        gpParticipant.add(lErreurMailProp, 1, 3);

        TextField tfTel = new TextField();
        numberOnly(tfTel);
        limitedLength(tfTel,10);
        tfTel.setPromptText("Téléphone");

        tfTel.setMaxWidth(150);
        tfTel.setId("tfTelProp" + indice);
        gpParticipant.add(tfTel, 2, 2);
        Label lErreurTelProp = new Label("");
        gpParticipant.add(lErreurTelProp, 2, 3);

        Button btnSuppEpreuve = new Button("-");btnSuppEpreuve.setId("btnSuppEpreuve" + indice);
        actionSupp(btnSuppEpreuve);
        gpParticipant.add(btnSuppEpreuve,3,0);

        lErreurNomProp.setStyle("-fx-font-size: 12 ; -fx-text-fill: #B22222;");
        lErreurPrenomProp.setStyle("-fx-font-size: 12 ; -fx-text-fill: #B22222;");
        lErreurMailProp.setStyle("-fx-font-size: 12 ; -fx-text-fill: #B22222;");
        lErreurTelProp.setStyle("-fx-font-size: 12 ; -fx-text-fill: #B22222;");


        vBoxParticipant.getChildren().add(gpParticipant);
//        Participant participant = new Participant(tfNom.getText(),tfPrenom.getText(),tfMail.getText(),tfTel.getText());
//        tournoi.addParticipants(participant);
    }


    private void actionSupp(Button btn) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vBoxParticipant.getChildren().remove(Integer.parseInt(btn.getId().substring(btn.getId().length()-1)));
                ObservableList<Node> childrens = vBoxParticipant.getChildren();
                int i = 0 ;
                for (Node node : childrens) {
                    GridPane epreuve = (GridPane) node ;
                    Button btn = (Button) getNodeByRowColumnIndex(3,0,epreuve);
                    btn.setId("btnSuppEpreuve" + i);
                }

                if (vBoxParticipant.getChildren().size() == 1) {
                    Button btnSupp = (Button) getNodeByRowColumnIndex(3, 0, (GridPane) vBoxParticipant.getChildren().get(0));
                    btnSupp.setDisable(true);
                }
            }
        });
    }

    private void enregistrerInfos() {
        for (Node node : vBoxParticipant.getChildren()) {
            GridPane participantCourant = (GridPane) node;

            tfNom = (TextField) getNodeByRowColumnIndex(1,0, participantCourant);
            String nom = tfNom.getText();

            tfPrenom = (TextField) getNodeByRowColumnIndex(2,0, participantCourant);
            String prenom = tfPrenom.getText();

            tfEmail = (TextField) getNodeByRowColumnIndex(1,2, participantCourant);
            String email = tfEmail.getText();

            tfNumTel = (TextField) getNodeByRowColumnIndex(2,2, participantCourant);
            String numTel = tfNumTel.getText();


            Participant participant = new Participant(nom,prenom,email,numTel);

            tournoi.addParticipants(participant);
        }
        tournoi.optimiserTournoi();
    }
}
