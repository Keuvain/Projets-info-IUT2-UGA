package fr.uga.iut2.genevent.controleur;


import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Logging;
import fr.uga.iut2.genevent.modele.Tournoi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;

public class AccueilCtrl extends AbstractCtrl {

    @FXML
    public VBox vBox ;
    @FXML
    public VBox vBoxListTournois;
    public Button creerBtn;
    public Button btnSupprimer ;
    public Button btnAnnuler ;



    public void initVue(GenEvent genEvent, Tournoi tournoi) {
        this.genEvent = genEvent ;
        try {
            logoInitialize();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        if (genEvent.getListeTournois().size() != 0) {
            int i = 0 ;

            for (HashMap.Entry<String, Tournoi> set :
                    genEvent.getListeTournois().entrySet()) {
                Label nom = new Label();
                Label lieu = new Label();
                Label date = new Label();
                GridPane affichageTournoi = new GridPane(); affichageTournoi.setPrefWidth(900); affichageTournoi.setPadding(new Insets(0,10,0,10));

                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(20);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(20);
                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(40);
                ColumnConstraints col4 = new ColumnConstraints();
                col4.setPercentWidth(10); col4.setHalignment(HPos.RIGHT);

                RowConstraints row1 = new RowConstraints();
                row1.setValignment(VPos.CENTER);
                affichageTournoi.getColumnConstraints().addAll(col1,col2,col3 ,col4,col4);


                affichageTournoi.setStyle("-fx-border-color : #000000 ; -fx-background-color : #216db5");
                affichageTournoi.setMaxWidth(Region.USE_PREF_SIZE);
                Tournoi tCourant = set.getValue();

                nom.setText(set.getKey());
                nom.setFont(new Font("Arial", 20));
                nom.setPadding(new Insets(0,7,0,0));
                affichageTournoi.add(nom,0,0); nom.setPadding(new Insets(20,0,20,0));

                lieu.setText(tCourant.getLieu());
                lieu.setFont(new Font("Arial", 20));
                lieu.setPadding(new Insets(0,7,0,0));
                affichageTournoi.add(lieu,1,0); lieu.setPadding(new Insets(20,0,20,0));

                if (tCourant.getDuree() == 1) {
                    date.setText("le " + tCourant.afficherDateDebut());
                    date.setFont(new Font("Arial", 20));
                    date.setPadding(new Insets(0,7,0,0));
                    affichageTournoi.add(date,2,0);
                } else {
                    date.setText("du " + tCourant.afficherDateDebut()+" au " + tCourant.afficherDateFin());
                    date.setFont(new Font("Arial", 20));
                    date.setPadding(new Insets(0,7,0,0));
                    affichageTournoi.add(date,2,0); date.setPadding(new Insets(20,0,20,0));
                }

                Button btnAfficher = new Button("Afficher"); btnAfficher.setStyle("-fx-background-color : #d69901");
                btnAfficher.setPadding(new Insets(20,0,20,0));btnAfficher.setMinWidth(80);
                Button btnSupprimer = new Button("Supprimer"); btnSupprimer.setId(i+"");btnSupprimer.setStyle("-fx-background-color : #d69901");
                btnSupprimer.setPadding(new Insets(20,0,20,0)); btnSupprimer.setMinWidth(80);

                affichageTournoi.add(btnAfficher,3,0);
                affichageTournoi.add(btnSupprimer,4,0);

                btnAfficher.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            InputStream fxmlStream = new FileInputStream("src/main/resources/fr/uga/iut2/genevent/vue/Information-tournoi.fxml");
                            Scene scene = new Scene(fxmlLoader.load(fxmlStream));

                            InformationCtrl ctrl = fxmlLoader.getController();
                            int indice = 0 ;
                            for (HashMap.Entry<String, Tournoi> set :
                                    genEvent.getListeTournois().entrySet()) {
                                Tournoi tCourant = set.getValue();
                                if (indice == Integer.parseInt(btnSupprimer.getId())){
                                    tCourant.optimiserTournoi();
                                    ctrl.initVue(genEvent,tCourant);
                                }
                                indice++;
                            }
                            Stage fenetre = (Stage) creerBtn.getScene().getWindow();

                            fenetre.setScene(scene);
                            fenetre.show();
                            Logging.LOGGER.log(Level.INFO, "Affichage des informations du tournoi");
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });

                btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        ButtonType supprimer = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
                        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

                        Alert dialogC = new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir btnSupprimer cet élément ?",supprimer,annuler);
                        dialogC.setTitle("Confirmation de suppression");
                        Logging.LOGGER.log(Level.WARNING, "Si vous supprimez votre tournoi, vous perdrez toutes les informations associés ");
                        dialogC.setHeaderText(null);

                        Optional<ButtonType> answer = dialogC.showAndWait();
                        if (answer.get() == supprimer) {
                            genEvent.suppTournoi(tCourant);
                            vBoxListTournois.getChildren().remove(Integer.parseInt(btnSupprimer.getId()));
                            Logging.LOGGER.log(Level.WARNING, "Suppression du tournoi");
                        }
                    }
                });





                MenuItem menuItem1 = new MenuItem("affiche");menuItem1.setId("afficheTournoi"+i);
//                menuItem1.setStyle("-fx-background-color : #ff7a00");
                MenuItem menuItem2 = new MenuItem("Supprimer");menuItem2.setId("suppTournoi"+i);
//                menuItem2.setStyle("-fx-background-color : #ff7a00");
                MenuButton option = new MenuButton("Options", null, menuItem1, menuItem2);
                option.setStyle("-fx-background-color : #D69901");
//                affichageTournoi.getChildren().add(option);
                HBox.setMargin(option,new Insets(0,0,10,0));
                i++;

                menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            InputStream fxmlStream = new FileInputStream("src/main/resources/fr/uga/iut2/genevent/vue/Information-tournoi.fxml");
                            Scene scene = new Scene(fxmlLoader.load(fxmlStream));

                            InformationCtrl ctrl = fxmlLoader.getController();
                            int indice = 0 ;
                            for (HashMap.Entry<String, Tournoi> set :
                                    genEvent.getListeTournois().entrySet()) {
                                Tournoi tCourant = set.getValue();
                                if (indice == Integer.parseInt(menuItem1.getId().substring(menuItem1.getId().length()-1))){
                                    tCourant.optimiserTournoi();
                                    ctrl.initVue(genEvent,tCourant);
                                }
                                indice++;
                            }

                            Stage fenetre = (Stage) creerBtn.getScene().getWindow();

                            fenetre.setScene(scene);
                            fenetre.show();
                            Logging.LOGGER.log(Level.INFO, "Affichage des informations du tournoi");
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
                menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        ButtonType supprimer = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
                        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

                        Alert dialogC = new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir btnSupprimer cet élément ?",supprimer,annuler);
                        dialogC.setTitle("Confirmation de suppression");
                        Logging.LOGGER.log(Level.WARNING, "Si vous supprimez votre tournoi, vous perdrez toute les données avec");
                        dialogC.setHeaderText(null);

                        Optional<ButtonType> answer = dialogC.showAndWait();
                        if (answer.get() == supprimer) {
                            genEvent.suppTournoi(tCourant);
                            vBoxListTournois.getChildren().remove(Integer.parseInt(menuItem2.getId().substring(menuItem2.getId().length()-1)));
                            Logging.LOGGER.log(Level.WARNING, "Suppression du tournoi");
                        }
                    }
                });
                vBoxListTournois.getChildren().add(affichageTournoi);
            }
        }
    }

    private Stage launchMessage(String adresse) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream fxmlStream = new FileInputStream(adresse);
            Scene scene = new Scene(fxmlLoader.load(fxmlStream));
            fxmlLoader.setController(this);
            Stage fenetreMessage = new Stage();
            fenetreMessage.setScene(scene);
            fenetreMessage.initModality(Modality.APPLICATION_MODAL);
            return fenetreMessage ;
        } catch (IOException e) {

        }
        return null ;
    }

    @FXML
    private void CreerButtonAction(ActionEvent event) throws IOException {
        FXMLLoader mainViewLoader = new FXMLLoader();
        String address = "src/main/resources/fr/uga/iut2/genevent/vue/Creer-tournoi.fxml";
        InputStream fxmlStream = new FileInputStream(address);
        Parent root =  mainViewLoader.load(fxmlStream);


        Stage fenetre = (Stage) creerBtn.getScene().getWindow();
        Scene mainScene = new Scene(root);

        Etape1Ctrl cntrl = mainViewLoader.getController();
        cntrl.initVue(this.genEvent,new Tournoi());

        fenetre.setScene(mainScene);
        fenetre.show();
        Logging.LOGGER.log(Level.INFO, "Lancement de la création du tournoi");
    }

}

