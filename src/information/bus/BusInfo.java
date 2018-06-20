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

    public void initData(Bus pick) {
        idLabel.setText(String.valueOf(pick.getId()));
        routeLabel.setText(String.valueOf(pick.getRoute()));
        locationLabel.setText(String.valueOf(pick.getLocation()));
        riderLabel.setText(String.valueOf(pick.getRiders()));
        speedLabel.setText(String.valueOf(pick.getSpeed()));
    }

}
