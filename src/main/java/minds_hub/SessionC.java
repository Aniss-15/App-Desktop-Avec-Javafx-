package minds_hub;

import entities.Formation;
import entities.Session;
import entities.Gloabal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.SessionServ;
import services.formationServ;

import static javafx.collections.FXCollections.*;


public class SessionC implements Initializable {
    @FXML
    private DatePicker DateF;

    @FXML
    private ComboBox<String> ListeF;

    @FXML
    private JFXButton ajoutfx;

    @FXML
    private DatePicker dateD;

    @FXML
    private TextField nomS;
    @FXML
    private TableColumn<Session, String> ColNomS;
    @FXML
    private TableColumn<Session, String> colD;

    @FXML
    private TableColumn<Session, String> colF;

    @FXML
    private TableColumn<Session, String> colFr;
    @FXML
    private TableView<Session> tableS;

    private List<Formation> formationList;
    @FXML
    private TableColumn<Session,Integer> colid;
    @FXML
    private JFXButton Sexpiree;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherS();
        verifSessExpiBientot(); // Vérification initiale
       checksperiod(); // Lancer la vérification périodique


        Formation formation = new Formation();
        formationServ serviceFormation = new formationServ();
        List<Formation> formations = serviceFormation.AfficherFormation(); //
        List<String> nomFormations = formations.stream()
                .map(Formation::getNom)
                .collect(Collectors.toList());
        ListeF.setItems(observableArrayList(nomFormations));
        tableS.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Session selectedS = tableS.getSelectionModel().getSelectedItem();
                dateD.setValue(LocalDate.parse(selectedS.getDate_d()));
                DateF.setValue(LocalDate.parse(selectedS.getDate_f()));
                 nomS.setText(selectedS.getNomSession());

            }
        });


    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void addS(ActionEvent event) {
            boolean isValid = true;

            // Date validation
            if (dateD.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Date", "Veuillez saisir votre date de début.");
                dateD.setStyle("-fx-text-fill: red;");
                isValid = false;
            }

            if (DateF.getValue() == null || DateF.getValue().isEqual(dateD.getValue()) || DateF.getValue().isBefore(dateD.getValue())) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Date", "La date de fin doit être différente et après la date de début.");
                DateF.setStyle("-fx-text-fill: red;");
                isValid = false;
            }

            // Nom validation
            if (nomS.getText().isEmpty() || !nomS.getText().matches("[a-zA-Z]+")) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Nom", "Veuillez saisir un nom valide.");
                nomS.setStyle("-fx-text-fill: red;");
                isValid = false;
            }

            if (isValid) {
                try {
                    SessionServ ss = new SessionServ();
                    String date = String.valueOf(dateD.getValue());
                    String dateF = String.valueOf(DateF.getValue());
                    String nom = nomS.getText();
                    String form = ListeF.getValue();

                    Session s = new Session();
                    s.setDate_d(date);
                    s.setDate_f(dateF);
                    s.setNomSession(nom);

                    formationServ fs = new formationServ();
                    int formId = fs.getFormationIdB(form);

                    Formation formation = new Formation();
                    formation.setId(formId);
                    formation.setNom(form);

                    if (s.getFormations() == null) {
                        s.setFormations(new ArrayList<>());
                    }
                    s.getFormations().add(formation);

                    ss.ajouterSession(s);
                    clearFields();

                    showAlert(Alert.AlertType.INFORMATION, "Session ajoutée", "La session a été ajoutée avec succès.");
                    afficherS(); // Refresh or display session list
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout de la session : " + e.getMessage());
                }
            }
        }
    private void clearFields() {
        dateD.setValue(null);
        DateF.setValue(null);
        nomS.clear();
        ListeF.getSelectionModel().clearSelection();
    }

// Helper method to show alerts

    public void afficherS() {
        SessionServ ss = new SessionServ();
        //Suppose que c'est un ArrayList
        ObservableList<Session> list = FXCollections.observableArrayList(ss.AfficherSession());
        tableS.setItems(list);
        colD.setCellValueFactory(new PropertyValueFactory<Session, String>("date_d"));
        colF.setCellValueFactory(new PropertyValueFactory<Session, String>("date_f"));
        ColNomS.setCellValueFactory(new PropertyValueFactory<Session, String>("nomSession"));
        colFr.setCellValueFactory(new PropertyValueFactory<Session,String>("FormationsString"));
        colid.setCellValueFactory(new PropertyValueFactory<Session,Integer>("id"));

        }
        //  colid.setCellValueFactory(new PropertyValueFactory<Session,Integer>("id"));

    @FXML
    void updateS(ActionEvent event) {
        Session selectedS = tableS.getSelectionModel().getSelectedItem();
        if (selectedS != null) {
            try {
                selectedS.setDate_d(dateD.getValue().toString());
                selectedS.setDate_f(DateF.getValue().toString());
                selectedS.setNomSession(nomS.getText());
                String formnom = ListeF.getValue();
                formationServ fs = new formationServ();

               //recuperation de la formation objet par son nom en
                Formation selectedF= fs.AfficherFormation().stream()
                        .filter(f -> f.getNom().equals(formnom))
                        .findFirst()
                        .orElse(null);

                // Initialize a new list to set in the session
                List<Formation> updatf = new ArrayList<>();
                if (selectedF != null) {
                    updatf.add(selectedF);
                }
                selectedS.setFormations(updatf);

                SessionServ ss = new SessionServ();
                ss.UpdateSession(selectedS);
                showAlert(Alert.AlertType.INFORMATION, "Session Updated", "The session has been updated successfully.");
                afficherS(); // Refresh the displayed session list
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Update Error", "Error updating the session: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No session selected. Please select a session in the table.");
        }
    }

    @FXML
    void suppS(ActionEvent event) {
        SessionServ fs = new SessionServ();
        int ligne = tableS.getSelectionModel().getSelectedIndex();

        // Vérifier si un élément a bien été sélectionné.
        if (ligne >= 0) {
            // Obtenir l'élément sélectionné.
            Session s  = tableS.getItems().remove(ligne);
            List<Session> sessionsExpirantBientot = fs.getSessionBientot();
            Gloabal.expiringSessions.setAll(sessionsExpirantBientot);
            fs.DeleteSession(s);
            fs.getSessionBientot();
            showAlert(Alert.AlertType.INFORMATION, "Session", "Session supprime avec succes");


            System.out.println("supprime avec succes " + s);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "session", "Aucune session n'est sélectionnée pour la suppression.");
            System.out.println("Aucune formation n'est sélectionnée pour la suppression.");
        }

    }
    void refreshTableView() {
        tableS.setItems(null);
        tableS.layout();
        tableS.setItems(Gloabal.expiringSessions); // Ensure this is the list used elsewhere
        tableS.refresh();
    }

    private void verifSessExpiBientot() {
        SessionServ ss = new SessionServ();
        List<Session> sessionsExpirB = ss.getSessionBientot();
        if (!sessionsExpirB.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Sessions expirant bientôt", "Certaines sessions vont expirer dans moins de 5 jours.");
        }
    }
    private void checksperiod() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.hours(24), ev -> {
            verifSessExpiBientot();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    void expiree(ActionEvent event){
     try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/minds_hub/fxml/SessionExpiree.fxml"));
         Parent root = loader.load();
         Stage stage = new Stage();
         // Set the scene with the newly loaded layout
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }

    }



}


