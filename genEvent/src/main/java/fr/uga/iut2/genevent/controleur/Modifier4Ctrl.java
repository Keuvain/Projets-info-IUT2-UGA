package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Level;

public class Modifier4Ctrl extends AbstractCtrl {

    @FXML
    public VBox vBoxStands;
    public BackgroundFill bacFill=new BackgroundFill(Color.web("#216db5"), CornerRadii.EMPTY, Insets.EMPTY);
    public Background back=new Background(bacFill);
    private int indice = 0;




    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);
        vBoxStands.setSpacing(30);
        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (tournoi.getStands() != null && tournoi.getStands().size() > 0) {
            affichageInfos();
        } else {
            ajoutStand(indice);
        }
    }

    @FXML
    private void validerButtonAction(ActionEvent event) throws IOException {

        tournoi.getStands().clear();

        if (donneesValides()) {

           enregistrerInfos();

            infosCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) validerBtn.getScene().getWindow();

            Logging.LOGGER.log(Level.INFO, "Informations enregistrées");
            fenetre.close();
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

            infosCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) validerBtn.getScene().getWindow();

            fenetre.close();
            Logging.LOGGER.log(Level.INFO, "Retour au informations");
        }
    }

    @FXML
    private void ajoutStandButtonAction(ActionEvent event) {
        ajoutStand(vBoxStands.getChildren().size());
    }

    private void ajoutStand(int indice) {

        GridPane stand = new GridPane(); stand.setPrefWidth(550); stand.setStyle("-fx-background-color: #0A2B58 ; -fx-border-color: #ffffff");
        stand.setHgap(20); stand.setVgap(0); stand.setAlignment(Pos.TOP_CENTER);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(28); row2.setValignment(VPos.TOP);
        stand.getRowConstraints().addAll(row1,row2,row1,row2,row1,row2);



        Label nom = new Label("Nom");
        nom.setMinWidth(100);
        nom.setTextFill(Color.WHITE);
        stand.add(nom, 0, 0);

        TextField tfNom = new TextField();
        tfNom.setMinWidth(100);
        stand.add(tfNom, 1, 0);
        Label lErreurNom = new Label("");
        stand.add(lErreurNom, 1, 1);

        ComboBox typeStand = new ComboBox();
        typeStand.setMinWidth(100);
        typeStand.setPromptText("Type");
        typeStand.setId("cbType" + indice);
        typeStand.getItems().addAll(TypeStand.BUVETTE, TypeStand.NOURRITURE, TypeStand.ACTIVITES, TypeStand.PRODUITSDIVERS);
        stand.add(typeStand, 2, 0);
        Label lErreurType = new Label("");
        stand.add(lErreurType, 2, 1);

        Button btnSuppStand = new Button("-");
        btnSuppStand.setId("btnSuppStand" + indice);
        actionSupp(btnSuppStand);
        stand.add(btnSuppStand, 4, 0);

        Label mLineaire = new Label("Mètre linéaire : ");
        mLineaire.setTextFill(Color.WHITE);
        stand.add(mLineaire, 0, 2);
        TextField tfMLineaire = new TextField();
        tfMLineaire.setPromptText("en mètres");
        tfMLineaire.setMaxWidth(100);
        tfMLineaire.setId("tfMLineaire" + indice);
        stand.add(tfMLineaire, 1, 2);
        Label lErreurMLineaire = new Label("");
        stand.add(lErreurMLineaire, 1, 3);

        Label nomProp=new Label("Propriétaire : ");
        nomProp.setTextFill(Color.WHITE);
        stand.add(nomProp, 0, 4);
        TextField tfNomProp = new TextField();
        tfNomProp.setPromptText("Nom");
        tfNomProp.setMaxWidth(100);
        tfNomProp.setId("tfNomProp" + indice);
        stand.add(tfNomProp, 1, 4);
        Label lErreurNomProp = new Label("");
        stand.add(lErreurNomProp, 1, 5);

        TextField tfPrenomProp = new TextField();
        tfPrenomProp.setPromptText("Prénom");
        tfPrenomProp.setMaxWidth(100);
        tfPrenomProp.setId("tfPrenomProp" + indice);
        stand.add(tfPrenomProp, 2, 4);
        Label lErreurPrenomProp = new Label("");
        stand.add(lErreurPrenomProp, 2, 5);

        TextField tfMailProp = new TextField();
        tfMailProp.setPromptText("Mail");
        tfMailProp.setMaxWidth(100);
        tfMailProp.setId("tfMailProp" + indice);
        stand.add(tfMailProp, 1, 6);
        Label lErreurMailProp = new Label("");
        stand.add(lErreurMailProp, 1, 7);

        TextField tfTelProp = new TextField();
        numberOnly(tfTelProp);
        limitedLength(tfTelProp,10);
        tfTelProp.setPromptText("Téléphone");

        tfTelProp.setMaxWidth(100);
        tfTelProp.setId("tfTelProp" + indice);
        stand.add(tfTelProp, 2, 6);
        Label lErreurTelProp = new Label("");
        stand.add(lErreurTelProp, 2, 7);

        lErreurNom.setStyle("-fx-font-size: 13 ; -fx-text-fill : #d69901");
        lErreurMLineaire.setStyle("-fx-font-size: 13 ; -fx-text-fill : #d69901");
        lErreurNomProp.setStyle("-fx-font-size: 13 ; -fx-text-fill : #d69901");
        lErreurPrenomProp.setStyle("-fx-font-size: 13 ; -fx-text-fill : #d69901");
        lErreurMailProp.setStyle("-fx-font-size: 13 ; -fx-text-fill : #d69901");
        lErreurTelProp.setStyle("-fx-font-size: 13 ; -fx-text-fill : #d69901");

        vBoxStands.getChildren().add(stand);



        this.indice++;
    }

    private void actionSupp(Button btn) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vBoxStands.getChildren().remove(Integer.parseInt(btn.getId().substring(btn.getId().length() - 1)));
                ObservableList<Node> childrens = vBoxStands.getChildren();
                int i = 0;
                for (Node node : childrens) {
                    GridPane epreuve = (GridPane) node;
                    Button btn = (Button) getNodeByRowColumnIndex(4, 0, epreuve);
                    btn.setId("btnSuppEpreuve" + i);
                }

            }
        });
    }


    private boolean donneesValides() {
        boolean verif = true;


        for (Node node : vBoxStands.getChildren()) {
            GridPane standCourant = (GridPane) node;


            TextField tfNom = (TextField) getNodeByRowColumnIndex(1, 0, standCourant);
            if (tfNom.getText().isEmpty() || tfNom.getText().isBlank()) {
                verif = false;
                tfNom.setStyle("-fx-text-box-border: #d69901");
                Label lErreurNom = (Label) getNodeByRowColumnIndex(1, 1, standCourant);
                lErreurNom.setText("Nom incorrect");
            }

            ComboBox cbType = (ComboBox) getNodeByRowColumnIndex(2, 0, standCourant);
            TypeStand type = (TypeStand) cbType.getValue();
            if (cbType.getValue() == null) {
                verif = false;
            }

            TextField tfMLineaire = (TextField) getNodeByRowColumnIndex(1, 2, standCourant);
            if (tfMLineaire.getText().isEmpty()) {
                verif = false;
                tfMLineaire.setStyle("-fx-text-box-border: #d69901; ");
                Label lErreurMLineaire = (Label) getNodeByRowColumnIndex(1, 3, standCourant);
                lErreurMLineaire.setText("Non renseigné");
            } else {
                try {
                    double metre = Double.parseDouble(tfMLineaire.getText());
                    if (metre <= 0) {
                        verif = false;
                        tfMLineaire.setStyle("-fx-text-box-border: #d69901;");
                        Label lErreurMLineaire = (Label) getNodeByRowColumnIndex(1, 3, standCourant);
                        lErreurMLineaire.setText("Donnée non valide");
                    }
                } catch (NumberFormatException e) {
                    verif = false;
                    tfMLineaire.setStyle("-fx-text-box-border: #d69901");
                    Label lErreurMLineaire = (Label) getNodeByRowColumnIndex(1, 3, standCourant);
                    lErreurMLineaire.setText("Mauvais format");
                }
            }

            TextField tfNomProp = (TextField) getNodeByRowColumnIndex(1, 4, standCourant);
            if (tfNomProp.getText().isEmpty() || tfNomProp.getText().isBlank()) {
                verif = false;
                tfNomProp.setStyle("-fx-text-box-border: #d69901");
                Label lErreurNomProp = (Label) getNodeByRowColumnIndex(1, 5, standCourant);
                lErreurNomProp.setText("Non renseigné");
            }

            TextField tfPrenomProp = (TextField) getNodeByRowColumnIndex(2, 4, standCourant);
            if (tfPrenomProp.getText().isEmpty() || tfPrenomProp.getText().isBlank()) {
                verif = false;
                tfPrenomProp.setStyle("-fx-text-box-border: #d69901");
                Label lErreurPrenomProp = (Label) getNodeByRowColumnIndex(2, 5, standCourant);
                lErreurPrenomProp.setText("Non renseigné");
            }

            TextField tfMailProp = (TextField) getNodeByRowColumnIndex(1, 6, standCourant);
            if (tfMailProp.getText().isEmpty() || tfMailProp.getText().isBlank()) {
                verif = false;
                tfMailProp.setStyle("-fx-text-box-border: #d69901");
                Label lErreurMailProp = (Label) getNodeByRowColumnIndex(1, 7, standCourant);
                lErreurMailProp.setText("Non renseigné");
            } else if (!tfMailProp.getText().contains("@") || (!tfMailProp.getText().contains(".fr") && !tfMailProp.getText().contains(".com"))) {
                verif = false;
                tfMailProp.setStyle("-fx-text-box-border: #d69901");
                Label lErreurMailProp = (Label) getNodeByRowColumnIndex(1, 7, standCourant);
                lErreurMailProp.setText("Mauvais format");
            }

            TextField tfTelProp = (TextField) getNodeByRowColumnIndex(2, 6, standCourant);
            if (tfTelProp.getText().isEmpty() || tfTelProp.getText().length() != 10) {
                verif = false;
                tfTelProp.setStyle("-fx-text-box-border: #d69901;");
                Label lErreurTelProp = (Label) getNodeByRowColumnIndex(2, 7, standCourant);
                lErreurTelProp.setText("Mauvaise saisie");
            }
        }
        return verif;
    }

    private void enregistrerInfos() {
        for (Node node : vBoxStands.getChildren()) {
            GridPane standCourant = (GridPane) node;


            TextField tfNom = (TextField) getNodeByRowColumnIndex(1, 0, standCourant);
            String nom = tfNom.getText();

            ComboBox cbType = (ComboBox) getNodeByRowColumnIndex(2, 0, standCourant);
            TypeStand type = (TypeStand) cbType.getValue();

            TextField tfMLineaire = (TextField) getNodeByRowColumnIndex(1, 2, standCourant);
            double metre = Double.parseDouble(tfMLineaire.getText());


            TextField tfNomProp = (TextField) getNodeByRowColumnIndex(1, 4, standCourant);
            String nomProp = tfNomProp.getText();


            TextField tfPrenomProp = (TextField) getNodeByRowColumnIndex(2, 4, standCourant);
            String prenomProp = tfPrenomProp.getText();


            TextField tfMailProp = (TextField) getNodeByRowColumnIndex(1, 6, standCourant);
            String mailProp = tfMailProp.getText();


            TextField tfTelProp = (TextField) getNodeByRowColumnIndex(2, 6, standCourant);
            String telProp = tfTelProp.getText();


            Standier prop = new Standier(nomProp, prenomProp, mailProp, telProp);
            Stand stand = new Stand(nom, type, metre, prop);
            tournoi.addStand(stand);
        }
        tournoi.optimiserTournoi();
        tournoi.genererDevis();
    }


        public void affichageInfos() {
            for (int i = 0; i < tournoi.getStands().size(); i++) {
                Stand stand = tournoi.getStands().get(i);
                ajoutStand(i);
                GridPane epreuveCourante = (GridPane) vBoxStands.getChildren().get(vBoxStands.getChildren().size()-1);

                TextField tfNom = (TextField) getNodeByRowColumnIndex(1, 0, epreuveCourante);
                tfNom.setText(stand.getNom());

                ComboBox cbType = (ComboBox) getNodeByRowColumnIndex(2, 0, epreuveCourante);
                cbType.setValue(stand.getType());

                TextField tfMLineaire = (TextField) getNodeByRowColumnIndex(1, 2, epreuveCourante);
                tfMLineaire.setText("" + stand.getmLineaire());


                Standier prop = stand.getStandier();

                TextField tfNomProp = (TextField) getNodeByRowColumnIndex(1, 4, epreuveCourante);
                tfNomProp.setText(prop.getNom());

                TextField tfPrenomProp = (TextField) getNodeByRowColumnIndex(2, 4, epreuveCourante);
                tfPrenomProp.setText(prop.getPrenom());

                TextField tfMailProp = (TextField) getNodeByRowColumnIndex(1, 6, epreuveCourante);
                tfMailProp.setText(prop.getEmail());

                TextField tfTelProp = (TextField) getNodeByRowColumnIndex(2, 6, epreuveCourante);
                tfTelProp.setText(prop.getNumTel());
            }
        }
    }
