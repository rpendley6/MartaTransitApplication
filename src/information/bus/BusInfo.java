package information.bus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import marta.Bus;

public class BusInfo {
    @FXML Label idLabel;
    @FXML Label routeLabel;
    @FXML Label locationLabel;
    @FXML Label riderLabel;
    @FXML Label speedLabel;
    @FXML Label currentStopLabel;
    @FXML Label nextStopLabel;
    @FXML Label distanceToNextLabel;
    @FXML Label timeUntilNextLabel;
    @FXML Label numberExitingLabel;
    @FXML Label numberBoardingLabel;
    @FXML Label updatedCountLabel;

    public void initData(Bus pick) {
        idLabel.setText(String.valueOf(pick.getId()));
        routeLabel.setText(String.valueOf(pick.getRoute()));
        locationLabel.setText(String.valueOf(pick.getLocation()));
        riderLabel.setText(String.valueOf(pick.getRiders()));
        speedLabel.setText(String.valueOf(pick.getSpeed()));

        numberExitingLabel.setText(String.valueOf(pick.getNextExit()));
        numberBoardingLabel.setText(String.valueOf(pick.getNextBoard()));
        updatedCountLabel.setText(String.valueOf(pick.getRiders() - pick.getNextExit() + pick.getNextBoard()));
    }

}
