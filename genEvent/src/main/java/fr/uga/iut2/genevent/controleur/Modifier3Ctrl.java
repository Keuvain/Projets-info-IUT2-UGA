package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.logging.Level;

public class Modifier3Ctrl extends AbstractCtrl{

    @FXML
    public VBox vBoxEpreuves;

    public Label lDuree;
    public Label lDate;
    public Label lHoraire;
    public Label lDateErreur;



    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        initModele(genEvent);
        initTournoi(tournoi);

        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        if (tournoi.getEpreuves().size() > 0) {
            affichageInfos();
        } else {
            ajoutEpreuve(0);
        }

        Button btnSupp = (Button) getNodeByRowColumnIndex(4,0,(GridPane) vBoxEpreuves.getChildren().get(0));
        btnSupp.setDisable(true);
    }

    private void resetAll() {
        for (Node node : vBoxEpreuves.getChildren()) {
            GridPane epreuveCourante = (GridPane) node;

            TextField tfDuree = (TextField) getNodeByRowColumnIndex(3, 0, epreuveCourante);
            tfDuree.setStyle("-fx-text-box-border: #C0C0C0 ;-fx-focus-color: #C0C0C0;");


            TextField tfDate = (TextField) getNodeByRowColumnIndex(1, 2, epreuveCourante);
            tfDate.setStyle("-fx-text-box-border: #C0C0C0 ;-fx-focus-color: #C0C0C0;");

            TextField tfHoraire = (TextField) getNodeByRowColumnIndex(3, 2, epreuveCourante);
            tfHoraire.setStyle("-fx-text-box-border: #C0C0C0 ;-fx-focus-color: #C0C0C0;");
        }
    }

    @FXML
    private void validerButtonAction(ActionEvent event){

        tournoi.getEpreuves().clear();

        if (donneesValides()){
            enregistrerInfos();

            infosCtrl.initVue(this.genEvent, this.tournoi);

            Stage fenetre = (Stage) validerBtn.getScene().getWindow();

            Logging.LOGGER.log(Level.INFO, "Informations enregistrées");
            fenetre.close();
        }
    }

    @FXML
    private void retourButtonAction() throws IOException {

            ButtonType continuer = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert dialogC = new Alert(Alert.AlertType.WARNING, "Les modifications ne seront pas enregistrées. \n Continuer ?", continuer, annuler);
            dialogC.setTitle("Annuler la modification");
            Logging.LOGGER.log(Level.WARNING, "Les informations présentes à cette étape ne seront pas sauvegardées");
            dialogC.setHeaderText(null);

            Optional<ButtonType> answer = dialogC.showAndWait();
            if (answer.get() == continuer) {

                infosCtrl.initVue(this.genEvent, this.tournoi);

                Stage fenetre = (Stage) validerBtn.getScene().getWindow();

                fenetre.close();
                Logging.LOGGER.log(Level.INFO, "Retour aux informations");
            }
    }

    @FXML
    private void ajoutEpreuveButtonAction(ActionEvent event) {
        ajoutEpreuve(vBoxEpreuves.getChildren().size());
    }


    private boolean donneesValides() {
        boolean verif = true;
        for (Node node : vBoxEpreuves.getChildren()) {
            GridPane epreuveCourante = (GridPane) node;

            ComboBox cbType = (ComboBox) getNodeByRowColumnIndex(1, 0, epreuveCourante);
            TypeEpreuve type = (TypeEpreuve) cbType.getValue();
            if (cbType.getValue() == null) {
                verif = false;
            }

            GridPane gpDate = (GridPane) getNodeByRowColumnIndex(2,0,epreuveCourante);
            DatePicker dpDate = (DatePicker) getNodeByRowColumnIndex(1, 0, gpDate);
            if (dpDate.getEditor().getText() == null) {   // Si la date de début est vide
                dpDate.setStyle("-fx-text-box-border: #d69901");
                lDateErreur.setText("date manquante");
                lDateErreur.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

                verif = false;
            } else {
                try {   // Vérifie le format de la date
                    LocalDate date = LocalDate.parse(dpDate.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    dpDate.setStyle("-fx-text-box-border: #d69901");
                    verif = false;
                    lDateErreur.setText("Date non valide");
                    lDateErreur.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

                }
            }

            GridPane gpHoraire = (GridPane) getNodeByRowColumnIndex(1, 2, epreuveCourante);

            Spinner<Integer> sHeureHoraire = (Spinner<Integer>) getNodeByRowColumnIndex(0, 1, gpHoraire);
            Spinner<Integer> sMinutesHoraire = (Spinner<Integer>) getNodeByRowColumnIndex(2,1,gpHoraire);
            if (sHeureHoraire.getEditor().getText().isEmpty() ||sMinutesHoraire.getEditor().getText().isEmpty()) {
                verif = false;
                sHeureHoraire.setStyle("-fx-text-box-border: #d69901");
                lHoraire.setText("Non renseigné");
                lHoraire.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

            } else {
                try {
                    int heure = Integer.parseInt(sHeureHoraire.getEditor().getText());
                    int min = Integer.parseInt(sMinutesHoraire.getEditor().getText());
                    LocalTime horaireEP = LocalTime.of(heure, min);
                } catch (NumberFormatException | DateTimeException e) {
                    verif = false;
                    sHeureHoraire.setStyle("-fx-text-box-border: #d69901;");
                    sMinutesHoraire.setStyle("-fx-text-box-border: #d69901");
                    lHoraire.setText("Mauvais format");
                    lHoraire.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

                }
            }


            GridPane gpDuree = (GridPane) getNodeByRowColumnIndex(2, 2, epreuveCourante);

            Spinner<Integer> sHeureDuree = (Spinner<Integer>) getNodeByRowColumnIndex(0, 1, gpDuree);
            Spinner<Integer> sMinutesDuree = (Spinner<Integer>) getNodeByRowColumnIndex(2,1, gpDuree);
            if (sHeureDuree.getEditor().getText().isEmpty() || sMinutesDuree.getEditor().getText().isEmpty()) {
                verif = false;
                sHeureDuree.setStyle("-fx-text-box-border: #d69901");
                lDuree.setText("Non renseigné");
                lDuree.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

            } else {
                try {
                    int heure = Integer.parseInt(sHeureDuree.getEditor().getText());
                    int min = Integer.parseInt(sMinutesDuree.getEditor().getText());
                    LocalTime dureeEP = LocalTime.of(heure, min);
                    if (heure == 0 && min == 0){
                        sHeureDuree.setStyle("-fx-text-box-border: #d69901");
                        sMinutesDuree.setStyle("-fx-text-box-border: #d69901");
                        lDuree.setText("Non renseigné");
                        lDuree.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

                        verif = false ;
                    }
                } catch (NumberFormatException | DateTimeException e) {
                    verif = false;
                    sHeureDuree.setStyle("-fx-text-box-border:#d69901");
                    sMinutesDuree.setStyle("-fx-text-box-border: #d69901");
                    lDuree.setText("Mauvais format");
                    lDuree.setStyle("-fx-font-size: 13 ; -fx-text-fill: #d69901");

                }
            }
        }
        return verif;
    }


    private void ajoutEpreuve(int indice) {
        if (vBoxEpreuves.getChildren().size() > 0) {
            Button btnSupp = (Button) getNodeByRowColumnIndex(4, 0, (GridPane) vBoxEpreuves.getChildren().get(0));
            btnSupp.setDisable(false);
        }


        GridPane epreuve = new GridPane(); epreuve.setPrefWidth(550); epreuve.setStyle("-fx-background-color: #0A2B58 ; -fx-border-color: #000000");
        epreuve.setHgap(20); epreuve.setVgap(0); epreuve.setAlignment(Pos.BOTTOM_LEFT);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(1);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(35);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(45);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(1);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(10); col5.setHalignment(HPos.LEFT);
        epreuve.getColumnConstraints().addAll(col1,col2,col3 ,col4,col5);

        Button btnSuppEpreuve = new Button("-");btnSuppEpreuve.setId("btnSuppEpreuve" + indice);
        actionSupp(btnSuppEpreuve); GridPane.setMargin(btnSuppEpreuve,new Insets(10,0,0,0));
        epreuve.add(btnSuppEpreuve,4,0);

        ComboBox typeEpreuve = new ComboBox(); typeEpreuve.setPromptText("Type");typeEpreuve.setId("cbType" + indice); GridPane.setMargin(typeEpreuve,new Insets(10,0,0,0));
        typeEpreuve.getItems().addAll(TypeEpreuve.COURSE,TypeEpreuve.LABOURAGE,TypeEpreuve.OBSTACLE,TypeEpreuve.TRACTAGE);
        epreuve.add(typeEpreuve,1,0);

        GridPane gpDate = new GridPane(); gpDate.setAlignment(Pos.BOTTOM_CENTER); gpDate.setPrefWidth(250);
        lDate = new Label("Date"); lDate.setPadding(new Insets(0,35,0,0));
        gpDate.add(lDate,0,0);
        lDate.setTextFill(Color.WHITE);
        DatePicker dpDate = new DatePicker();dpDate.setMaxWidth(150);dpDate.setId("dpDate" + indice);
        dpDate.setEditable(false);
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(tournoi.getDateDebut()) || item.isAfter(tournoi.getDateFin())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        dpDate.setDayCellFactory(dayCellFactory);
        gpDate.add(dpDate,1,0);
        epreuve.add(gpDate,2,0);
        lDateErreur=new Label("");
        epreuve.add(lDateErreur,2,1);
        lDateErreur.setTextFill(Color.web("d69901"));

        GridPane horaire = new GridPane(); horaire.setAlignment(Pos.BOTTOM_CENTER); horaire.setPrefWidth(250);
        Label nomHoraire = new Label("Horaire"); nomHoraire.setPadding(new Insets(0,0,10,0));
        horaire.add(nomHoraire,0,0);
        nomHoraire.setTextFill(Color.WHITE);
        Spinner<Integer> sHeureHoraire = new Spinner<Integer>(); sHeureHoraire.setPrefWidth(80);
        sHeureHoraire.setEditable(true);
        setIncrDecr(sHeureHoraire, 1,1,24,0);
        horaire.add(sHeureHoraire,0,1);
        Label heureHoraire = new Label("h"); heureHoraire.setPrefWidth(25); heureHoraire.setPadding(new Insets(5));
        horaire.add(heureHoraire,1,1);
        heureHoraire.setTextFill(Color.WHITE);
        Spinner<Integer> sMinHoraire = new Spinner<Integer>(); sMinHoraire.setPrefWidth(80);
        sMinHoraire.setEditable(true);
        setIncrDecr(sMinHoraire, 5,5,60,0);
        horaire.add(sMinHoraire,2,1);
        Label minutesHoraire = new Label("m");
        minutesHoraire.setPrefWidth(80);
        minutesHoraire.setTextFill(Color.WHITE);
        minutesHoraire.setPadding(new Insets(5));
        horaire.add(minutesHoraire,3,1);
        lHoraire=new Label("");
        horaire.add(lHoraire,0,2);

        epreuve.add(horaire,1,2);


        GridPane duree = new GridPane(); duree.setAlignment(Pos.BOTTOM_CENTER); duree.setPrefWidth(250);
        Label nomDuree = new Label("Duree"); nomDuree.setPadding(new Insets(0,0,10,0));
        duree.add(nomDuree,0,0);
        nomDuree.setTextFill(Color.WHITE);
        Spinner<Integer> sHeureDuree = new Spinner<Integer>(); sHeureDuree.setPrefWidth(50);sHeureDuree.setEditable(true);
        setIncrDecr(sHeureDuree, 1,1,24,0);
        duree.add(sHeureDuree,0,1);
        Label heure = new Label("h"); heure.setPrefWidth(25); heure.setPadding(new Insets(5));
        duree.add(heure,1,1);
        heure.setTextFill(Color.WHITE);
        Spinner<Integer> sMinDuree = new Spinner<Integer>(); sMinDuree.setPrefWidth(50);sMinDuree.setEditable(true);
        setIncrDecr(sMinDuree, 5,5,60,0);
        duree.add(sMinDuree,2,1);
        Label minutes = new Label("m");minutes.setPrefWidth(25);minutes.setPadding(new Insets(5));
        duree.add(minutes,3,1);
        minutes.setTextFill(Color.WHITE);
        lDuree=new Label("");
        duree.add(lDuree,0,2);

        epreuve.add(duree,2,2);


        vBoxEpreuves.getChildren().add(epreuve);

    }


    private void setIncrDecr(Spinner spinner, int increment, int decrement,int limitIncr, int limitDecr) {
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory<Integer>() {

                    @Override
                    public void decrement(int steps) {
                        if (this.getValue()-decrement < limitDecr) {
                            this.setValue(limitIncr-decrement);
                        } else {
                            this.setValue(this.getValue() - decrement);
                        }
                    }

                    @Override
                    public void increment(int steps) {
                        if (this.getValue()+increment >= limitIncr) {
                            this.setValue(limitDecr);
                        } else {
                            this.setValue(this.getValue() + decrement);
                        }
                    }

                };

        // Default value for Spinner
        valueFactory.setValue(0);

        spinner.setValueFactory(valueFactory);

        spinner.getEditor().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    spinner.increment(increment);
                    break;
                case DOWN:
                    spinner.decrement(decrement);
                    break;
            }
        });

    }

    private void actionSupp(Button btn) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                vBoxEpreuves.getChildren().remove(Integer.parseInt(btn.getId().substring(btn.getId().length()-1)));
                ObservableList<Node> childrens = vBoxEpreuves.getChildren();
                int i = 0 ;
                for (Node node : childrens) {
                    GridPane epreuve = (GridPane) node ;
                    Button btn = (Button) getNodeByRowColumnIndex(4,0,epreuve);
                    btn.setId("btnSuppEpreuve" + i);
                }

                if (vBoxEpreuves.getChildren().size() == 1) {
                    Button btnSupp = (Button) getNodeByRowColumnIndex(4, 0, (GridPane) vBoxEpreuves.getChildren().get(0));
                    btnSupp.setDisable(true);
                }
            }
        });
    }

    private void resetMouseAction(TextField tf, Label l) {
        tf.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                l.setText("");
                tf.setStyle("-fx-text-box-border: #c0c0c0; -fx-focus-color: #c0c0c0;");
            }
        });
    }

    private void enregistrerInfos() {
        for (Node node : vBoxEpreuves.getChildren()) {
            GridPane epreuveCourante = (GridPane) node;

            ComboBox cbType = (ComboBox) getNodeByRowColumnIndex(1, 0, epreuveCourante);
            TypeEpreuve type = (TypeEpreuve) cbType.getValue();

            GridPane gpDate = (GridPane) getNodeByRowColumnIndex(2,0,epreuveCourante);
            DatePicker dpDate = (DatePicker) getNodeByRowColumnIndex(1, 0, gpDate);
            LocalDate date = LocalDate.parse(dpDate.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            GridPane gpHoraire = (GridPane) getNodeByRowColumnIndex(1, 2, epreuveCourante);

            Spinner<Integer> sHeureHoraire = (Spinner<Integer>) getNodeByRowColumnIndex(0, 1, gpHoraire);
            Spinner<Integer> sMinutesHoraire = (Spinner<Integer>) getNodeByRowColumnIndex(2,1,gpHoraire);

            int heure = Integer.parseInt(sHeureHoraire.getEditor().getText());
            int min = Integer.parseInt(sMinutesHoraire.getEditor().getText());
            LocalTime horaire = LocalTime.of(heure, min);

            GridPane gpDuree = (GridPane) getNodeByRowColumnIndex(2, 2, epreuveCourante);

            Spinner<Integer> sHeureDuree = (Spinner<Integer>) getNodeByRowColumnIndex(0, 1, gpDuree);
            Spinner<Integer> sMinutesDuree = (Spinner<Integer>) getNodeByRowColumnIndex(2,1, gpDuree);

            heure = Integer.parseInt(sHeureDuree.getEditor().getText());
            min = Integer.parseInt(sMinutesDuree.getEditor().getText());
            LocalTime duree = LocalTime.of(heure, min);

            Epreuve epreuve = new Epreuve(type,duree,date,horaire);

            tournoi.addEpreuve(epreuve);
        }
        tournoi.optimiserTournoi();
        tournoi.genererDevis();
    }

    public void affichageInfos() {
        for(int i = 0 ; i < tournoi.getEpreuves().size() ; i++) {
            Epreuve ep = tournoi.getEpreuves().get(i);
            ajoutEpreuve(i);
            GridPane epreuveCourante = (GridPane) vBoxEpreuves.getChildren().get(vBoxEpreuves.getChildren().size()-1);
            ComboBox cbType = (ComboBox) getNodeByRowColumnIndex(1, 0, epreuveCourante);
            cbType.setValue(ep.getType());

            GridPane gpDate = (GridPane) getNodeByRowColumnIndex(2,0,epreuveCourante);
            DatePicker dpDate = (DatePicker) getNodeByRowColumnIndex(1, 0, gpDate);
            dpDate.setValue(ep.getDate());

            GridPane gpHoraire = (GridPane) getNodeByRowColumnIndex(1, 2, epreuveCourante);

            Spinner<Integer> sHeureHoraire = (Spinner<Integer>) getNodeByRowColumnIndex(0, 1, gpHoraire);
            Spinner<Integer> sMinutesHoraire = (Spinner<Integer>) getNodeByRowColumnIndex(2,1,gpHoraire);
            LocalTime horaire = ep.getHoraire();
            sHeureHoraire.getEditor().setText(horaire.getHour()+"");
            sMinutesHoraire.getEditor().setText(horaire.getMinute()+"");


            GridPane gpDuree = (GridPane) getNodeByRowColumnIndex(2, 2, epreuveCourante);
            Spinner<Integer> sHeureDuree = (Spinner<Integer>) getNodeByRowColumnIndex(0, 1, gpDuree);
            Spinner<Integer> sMinutesDuree = (Spinner<Integer>) getNodeByRowColumnIndex(2,1, gpDuree);
            LocalTime duree = ep.getDuree();
            sHeureDuree.getEditor().setText(duree.getHour()+"");
            sMinutesDuree.getEditor().setText(duree.getMinute()+"");

        }
    }
}
