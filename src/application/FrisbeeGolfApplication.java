package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrisbeeGolfApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the main window
        Parent root = FXMLLoader.load(getClass().getResource("/application/view/FrisbeeGolfView.fxml"));

        // Create the scene with the loaded UI layout and set the CSS stylesheet
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // Set the title and the scene for the stage, then show it
        primaryStage.setTitle("Frisbee Golf App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}
