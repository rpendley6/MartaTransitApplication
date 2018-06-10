package register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationScreen {
    @FXML TextField usernameField;
    @FXML TextField passwordField;
    @FXML Button registerButton;
    @FXML Button backButton;

    @FXML
    public void registerButtonHandler(ActionEvent event) {

    }

    @FXML
    public void backButtonHandler(ActionEvent event) throws IOException {
        backButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../welcome/welcomeScreen.fxml")));
    }
}
