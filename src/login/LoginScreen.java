package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreen {

    @FXML TextField usernameField;
    @FXML PasswordField passwordField;
    @FXML Button loginButton;
    @FXML Button backButton;
    @FXML Label errorMessage;

    @FXML
    public void loginButtonAction(ActionEvent event) throws IOException{
        if(usernameField.getText().equals("user") && passwordField.getText().equals("pass")) {
            //Success
            loginButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../information/mainList.fxml")));
        } else {
            //Error Screen
            errorMessage.setVisible(true);
        }
    }

    @FXML
    public void backButtonHandler(ActionEvent event) throws IOException {
        backButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../welcome/welcomeScreen.fxml")));
    }
}
