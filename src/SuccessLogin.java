import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class SuccessLogin {

    @FXML Button logoutButton;

    @FXML
    public void logoutButtonHandler(ActionEvent event) throws IOException {
        logoutButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("/welcome/welcomeScreen.fxml")));
    }
}
