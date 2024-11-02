package minds_hub;

import com.jfoenix.controls.JFXButton;
import entities.Session;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Node;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class MenuC implements Initializable {
    @FXML
    private Label Menu1;

    @FXML
    private Label MenuClose;

    @FXML
    private ImageView exit;

    @FXML
    private AnchorPane slider;
    @FXML
    private JFXButton addf;

    @FXML
    private JFXButton adds;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         {
            exit.setOnMouseClicked(mouseEvent -> {
                System.exit(0);

            });
            slider.setTranslateX(-176);
            Menu1.setOnMouseClicked(event -> {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);

                slide.setToX(0);
                slide.play();

                slider.setTranslateX(-176);

                slide.setOnFinished((ActionEvent e) -> {
                    Menu1.setVisible(false);
                    MenuClose.setVisible(true);
                });
            });

            MenuClose.setOnMouseClicked(event -> {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);

                slide.setToX(-176);
                slide.play();

                slider.setTranslateX(0);

                slide.setOnFinished((ActionEvent e) -> {
                    Menu1.setVisible(true);
                    MenuClose.setVisible(false);
                });
            });
        }
*/
    }
    @FXML
    void addformation(ActionEvent event) {
        try {
            // Load the new FXML document
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/minds_hub/fxml/Formation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            // Set the scene with the newly loaded layout
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, add error handling here
        }

    }

    @FXML
    void addsession(MouseEvent event) {

    }

    @FXML
    void homd(MouseEvent event) {

    }
    private VBox createQuizCard(Session session) {
        VBox card = new VBox(10);
        card.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-color: black; -fx-background-color: #f4f4f4;");
        Label titleLabel = new Label(session.getDate_d());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label questionCount = new Label("Questions: " + session.toString());
        Button startButton = new Button("Start");
        //startButton.setOnAction(event -> handleStartQuiz(quiz));
        card.getChildren().addAll(titleLabel, questionCount, startButton);
        return card; }

}
