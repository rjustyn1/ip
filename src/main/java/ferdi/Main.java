package ferdi;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String DATA_PATH = "./src/main/java/data/ferdi.txt";

    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load MainWindow.fxml", e);
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/view/styles.css").toExternalForm());

        stage.setTitle("Ferdi");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        MainWindow controller = fxmlLoader.getController();
        controller.setFerdi(new Ferdi(DATA_PATH));
    }
}
