package welcome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeScreen {

    @FXML Button loginButton;
    @FXML Button registerButton;

    @FXML
    public void loginButtonHandler(ActionEvent event) throws IOException {
        loginButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../login/loginScreen.fxml")));
    }

    @FXML
    public void registerButtonHandler(ActionEvent event) throws IOException{
        registerButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../register/registrationScreen.fxml")));
    }
}
