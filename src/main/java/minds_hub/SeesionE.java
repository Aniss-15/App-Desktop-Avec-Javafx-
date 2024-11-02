package minds_hub;

import entities.Formation;
import entities.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import services.SessionServ;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SeesionE implements Initializable {
    @FXML
    private ListView<String> sessionListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
        SessionServ ss=new SessionServ();
        ObservableList<Session> listsessionexp = FXCollections.observableArrayList(ss.getSessionBientot());
    }

    public void afficher(){
        SessionServ ss = new SessionServ();
        List<Session> sessions = ss.getSessionBientot();
        for (Session session : sessions) {
            String text = "ID: " + session.getId() +
                    ", Nom: " + session.getNomSession() +
                    ", Début: " + session.getDate_d() +
                    ", Fin: " + session.getDate_f();
            if (session.getFormations() != null) {
                text += ", Formations: ";
                for (Formation formation : session.getFormations()) {
                    text += formation.getNom() + ", ";
                    text+=formation.getDescription();
                }
                text = text.substring(0, text.length() - 2); // Retire la dernière virgule et espace
            }
            sessionListView.getItems().add(text);
        }

    }
}
