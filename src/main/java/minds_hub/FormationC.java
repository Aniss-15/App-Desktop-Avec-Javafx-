package minds_hub;

import entities.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import services.formationServ;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXButton;


public class FormationC implements Initializable {


    @FXML
    private ComboBox<String> ComboboxN;

    @FXML
    private TextArea DescriptFx;

    @FXML
    private TextArea ThemFx;

    @FXML
    private ComboBox<String> comboBoxNiv;

    @FXML
    private TextField nbreFx;
    @FXML
    private JFXButton addF;
    @FXML
    private TableColumn<Formation, String> ColDescrip;

    @FXML
    private TableColumn<Formation, String> ColNiv;
    @FXML
    private TableColumn<Formation, Integer> id;

    @FXML
    private TableColumn<Formation, String> ColNom;

    @FXML
    private TableColumn<Formation, String> ColThem;

    @FXML
    private TableColumn<Formation, Integer> Colnbre;
    @FXML
    private TableView<Formation> table;
    @FXML
    private JFXButton removeFx;
    @FXML
    private JFXButton Conf;
    @FXML
    private TextField textF;
    private final String FILE_PATH = "data.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        afficherFormations();
        ObservableList<String> list = FXCollections.observableArrayList("Parole en publique", "Gestion de Stress & Temps", "gestion de prononciation", "Management & gestion equipes", "developpement de competences numeriques");
        ComboboxN.setItems(list);
        ObservableList<String> list1 = FXCollections.observableArrayList("beginner", "Intermediaire", "Advanced");
        comboBoxNiv.setItems(list1);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Formation selectedF = table.getSelectionModel().getSelectedItem();
                ComboboxN.setValue(selectedF.getNom());
                DescriptFx.setText(selectedF.getDescription());
                ThemFx.setText(selectedF.getThematique());
                nbreFx.setText(String.valueOf(selectedF.getNbr_participant()));
                comboBoxNiv.setValue(selectedF.getNiveau());
            }
        });

    }
    public ComboBox<String> getComboboxN() {
        return ComboboxN;
    }


    public TextArea getDescriptFx() {
        return DescriptFx;
    }

    public TextArea getThemFx() {
        return ThemFx;
    }

    public ComboBox<String> getComboBoxNiv() {
        return comboBoxNiv;
    }

    public TextField getNbreFx() {
        return nbreFx;
    }

    @FXML
    void addF(ActionEvent event) {

        boolean isValid = true;
        String nom = ComboboxN.getValue();
        String niveau = comboBoxNiv.getValue();
        formationServ fs = new formationServ();

        if (fs.nameExists(nom)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Nom déjà utilisé");
            alert.setContentText("Le nom de la formation existe déjà dans la base de données. Veuillez en choisir un autre.");
            alert.showAndWait();
            return;
        }
        if (DescriptFx.getText().isEmpty() || !DescriptFx.getText().matches("[a-zA-Z]+")) {
            DescriptFx.setText("Veuillez saisir une description valide (exp: description sans chiffres)");
            DescriptFx.setStyle("-fx-text-fill: red;");
            isValid = false;
        }
        if (ThemFx.getText().isEmpty() || !ThemFx.getText().matches("-[a-z]+")) {
            ThemFx.setText("Veuillez saisir une Thematique valide (exp: Thematique sans chiffres y compris -)");
            ThemFx.setStyle("-fx-text-fill: Red");
            isValid = false;
        }
        if (nbreFx.getText().isEmpty() || Integer.parseInt(nbreFx.getText()) <= 0) {
            nbreFx.setText("Entrez un nombre positif");
            nbreFx.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (isValid) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Formation f = new Formation(nom, DescriptFx.getText(), ThemFx.getText(), Integer.parseInt(nbreFx.getText()), niveau);
                fs = new formationServ();
                fs.addFormation(f);
                alert.setContentText("Ajout effectue avec succes.");
                alert.showAndWait();
                afficherFormations();
            } catch (NumberFormatException e) {
                System.out.println("Erreur de format du nombre : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred while adding the response: " + e.getMessage());
            }
        }
    }

    public void afficherFormations() {
        formationServ fs = new formationServ();
      //Suppose que c'est un ArrayList
        ObservableList<Formation> list = FXCollections.observableArrayList(fs.AfficherFormation());
        table.setItems(list);
        ColNom.setCellValueFactory(new PropertyValueFactory<Formation, String>("Nom"));
        ColDescrip.setCellValueFactory(new PropertyValueFactory<Formation, String>("Description"));
        ColThem.setCellValueFactory(new PropertyValueFactory<Formation, String>("Thematique"));
        Colnbre.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("nbr_participant"));
        ColNiv.setCellValueFactory(new PropertyValueFactory<Formation, String>("Niveau"));
        id.setCellValueFactory(new PropertyValueFactory<Formation,Integer>("id"));

    }


    @FXML
    void remove(ActionEvent event) {
        formationServ fs = new formationServ();
        int ligne = table.getSelectionModel().getSelectedIndex();

        // Vérifier si un élément a bien été sélectionné.
        if (ligne >= 0) {
            // Obtenir l'élément sélectionné.
            Formation f = table.getItems().remove(ligne);
            fs.DeleteFormation(f);
            showAlert(Alert.AlertType.INFORMATION, "formation ", "formation supprime avec succes");
            afficherFormations();
            System.out.println("supprime avec succes " + f);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "formation ", "Aucune formation n'est sélectionnée pour la suppression.");
            System.out.println("Aucune formation n'est sélectionnée pour la suppression.");
        }
    }
    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML

    void upf(ActionEvent event)throws IOException {
        Formation selectedF = table.getSelectionModel().getSelectedItem();
        if (selectedF != null) {

            try {
                String nom = ComboboxN.getValue();
                String description = DescriptFx.getText().trim();
                String Thematique = ThemFx.getText().trim();
                int nbr_participant= Integer.parseInt(nbreFx.getText());
                String niveau = comboBoxNiv.getValue();

                Formation updateF = new Formation(selectedF.getId(), nom, description, Thematique, nbr_participant, niveau);
                formationServ fs = new formationServ();

                fs.updateFormation(updateF);
                showAlert(Alert.AlertType.INFORMATION, "Formation updated  ", "Formation was successfully updated.");
afficherFormations();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Erreur", "Error updating Formation: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No Course Selected. Please select a course in the table.");
        }
    }


    @FXML
    void ajouternouveauF(ActionEvent event) {
        ComboboxN.getItems().add(textF.getText());
        textF.clear();

    }
}







