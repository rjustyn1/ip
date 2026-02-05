package ferdi;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    private static final String USER_DIALOG_STYLE = "user-dialog";
    private static final String FERDI_DIALOG_STYLE = "ferdi-dialog";

    @FXML
    private Label text;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String s, Image i) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/DialogBox.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load DialogBox.fxml", e);
        }

        text.setText(s);
        displayPicture.setImage(i);
        text.setWrapText(true);

        this.getStyleClass().add("dialog-box");
        text.getStyleClass().add("dialog-text");
        displayPicture.getStyleClass().add("dialog-image");
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String s, Image i) {
        DialogBox dialogBox = new DialogBox(s, i);
        dialogBox.getStyleClass().add(USER_DIALOG_STYLE);
        return dialogBox;
    }

    public static DialogBox getFerdiDialog(String s, Image i) {
        DialogBox dialogBox = new DialogBox(s, i);
        dialogBox.flip();
        dialogBox.getStyleClass().add(FERDI_DIALOG_STYLE);
        return dialogBox;
    }
}
