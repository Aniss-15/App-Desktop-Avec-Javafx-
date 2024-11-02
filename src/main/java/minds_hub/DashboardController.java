package minds_hub;


/*import com.example.mindshubjavafx.Entities.Cours;*/
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class DashboardController {

    @FXML
    private final VBox pnItems = null;

    private final ObservableList<Node> itemList = FXCollections.observableArrayList();

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenu;

    @FXML
    private StackPane dynamicstack;
    @FXML
    private VBox QuizPane;
    @FXML
    private VBox coursesPane;
    @FXML
    private VBox competitionPane;
    @FXML
    private VBox sessionPane;
    @FXML
    private VBox reclamationPane;
    @FXML
    private VBox userPane;
    @FXML
    private VBox lessonsPane;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button newCustomer;

    @FXML
    private Button btnItemsMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;
    @FXML
    private Button allCoursesCheckBox;

    @FXML
    private Button addCourseCheckBox;
    @FXML
    private Button allLessons;
    @FXML private  Button allSessionCheckBox;
    @FXML private Button addSessionCheckBox;
    @FXML
    private Button addLesson;
    @FXML
    private Button updateLesson;
    @FXML
    private Button updateCourseCheckBox;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Button allQuestionsCheckBox;
    @FXML
    private Button addQuestionCheckBox;
    @FXML
    private Button updateQuestionCheckBox;
    @FXML
    private VBox QuestionPane;
    @FXML
    private Pane pnlMenus;


    @FXML
    private void initialize() {

    }




    @FXML
    private void toggleCoursesPane(ActionEvent event) {
        coursesPane.setVisible(!coursesPane.isVisible());
        coursesPane.setManaged(!coursesPane.isManaged());

    }

    @FXML
    private void toggleQuizPane(ActionEvent event) {
        QuizPane.setVisible(!QuizPane.isVisible());
        QuizPane.setManaged(!QuizPane.isManaged());

    }

    @FXML
    private void toggleCompetitionPane(ActionEvent event) {
        competitionPane.setVisible(!competitionPane.isVisible());
        competitionPane.setManaged(!competitionPane.isManaged());

    }

    @FXML
    private void toggleSessionPane(ActionEvent event) {
        sessionPane.setVisible(!sessionPane.isVisible());
        sessionPane.setManaged(!sessionPane.isManaged());

    }

    @FXML
    private void toggleQuestionPane(ActionEvent event) {
        QuestionPane.setVisible(!QuestionPane.isVisible());
        QuestionPane.setManaged(!QuestionPane.isManaged());

    }

    @FXML
    private void toggleReclamationPane(ActionEvent event) {
        reclamationPane.setVisible(!reclamationPane.isVisible());
        reclamationPane.setManaged(!reclamationPane.isManaged());

    }

    @FXML
    private void toggleUserPane(ActionEvent event) {
        userPane.setVisible(!userPane.isVisible());
        userPane.setManaged(!userPane.isManaged());

    }

    @FXML
    private void toggleLessonsPane(ActionEvent event) {
        lessonsPane.setVisible(!lessonsPane.isVisible());
        lessonsPane.setManaged(!lessonsPane.isManaged());
    }

    @FXML
    public void handleClicks(ActionEvent event) {
        try {
            Node view = null;

            if (event.getSource() == btnItemsMenus) {

                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Admin/UsersCrud/Users.fxml")));

            } else if (event.getSource() == btnItemsMenus) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Admin/MenuItem/MenuItems.fxml")));
            } else if (event.getSource() == btnMenu) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Admin/Menu/Menu.fxml")));
            }
            if (event.getSource() == allCoursesCheckBox) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mindshubjavafx/views/Cours/ShowCourses.fxml")));
            } else if (event.getSource() == addCourseCheckBox) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mindshubjavafx/views/Cours/AddCours.fxml")));
            } else if (event.getSource() == updateCourseCheckBox) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mindshubjavafx/views/Cours/UpdateCourse.fxml")));

            } else if (event.getSource() == allLessons) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mindshubjavafx/views/leçons/Showlessons.fxml")));

            } else if (event.getSource() == addLesson) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mindshubjavafx/views/leçons/AddLecon.fxml")));

            } else if (event.getSource() == updateLesson) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mindshubjavafx/views/leçons/UpdateLesson.fxml")));
            }
            else if (event.getSource() ==allSessionCheckBox) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/minds_hub/fxml/Formation.fxml")));
            }
            else if (event.getSource() ==addSessionCheckBox) {
                view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/minds_hub/fxml/Session.fxml")));
            }
            if (view != null) {
                dynamicstack.getChildren().clear();
                dynamicstack.getChildren().setAll(view);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
//            showAlert("Load Error", "The FXML file was not found.");
        } catch (Exception e) {
            e.printStackTrace();
//            showAlert("Load Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

   /* @Override
    public void onCourseSelected(Cours cours) {
        showCourseDetails(cours);
    }*/

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

   /* public void showCourseDetails(Cours cours) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/views/CoursFront/CourseDetails.fxml"));
            Node detailsView = loader.load();
            CourseDetailsController controller = loader.getController();

            controller.populateCourseDetails(cours);
            System.out.println("Loaded details view for: " + cours.getNomCours());

            dynamicstack.getChildren().clear();
            dynamicstack.getChildren().setAll(detailsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}