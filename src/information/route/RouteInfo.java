package information.route;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import marta.Route;
import marta.Stop;

public class RouteInfo {

    @FXML Label idLabel;
    @FXML Label nameLabel;
    @FXML Label stopsLabel;

    public void initData(Route route) {
        idLabel.setText(String.valueOf(route.getId()));
        nameLabel.setText(route.getName());
        stopsLabel.setText("");
        Stop[] array = route.getPath();
        for(Stop s:array) {
            if(s!=null) {
                stopsLabel.setText(stopsLabel.getText().concat(s.getName() + ", "));
             }
        }
        stopsLabel.setText(stopsLabel.getText().substring(0, stopsLabel.getText().length() - 2));
    }
}
