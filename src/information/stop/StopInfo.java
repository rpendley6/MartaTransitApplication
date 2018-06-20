package information.stop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import marta.Stop;

public class StopInfo {

    @FXML Label idLabel;
    @FXML Label nameLabel;
    @FXML Label latLabel;
    @FXML Label longLabel;

    public void initData(Stop stop) {
        idLabel.setText(String.valueOf(stop.getId()));
        nameLabel.setText(stop.getName());
        latLabel.setText(String.valueOf(stop.getLatitude()));
        longLabel.setText(String.valueOf(stop.getLongitude()));
    }
}
