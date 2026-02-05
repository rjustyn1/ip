package ferdi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image ferdiImage = new Image(this.getClass().getResourceAsStream("/images/DaFerdi.png"));

    private Ferdi ferdi;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());
        dialogContainer.setFillWidth(true);
    }

    public void setFerdi(Ferdi ferdi) {
        this.ferdi = ferdi;
    }

    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        String ferdiText = ferdi.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getFerdiDialog(ferdiText, ferdiImage)
        );
        userInput.clear();
    }
}
