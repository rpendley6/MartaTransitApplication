package information;

import information.bus.BusInfo;
import information.route.RouteInfo;
import information.stop.StopInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import marta.Bus;
import marta.Route;
import marta.Stop;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainList implements Initializable {

    public static ArrayList<Bus> busList;
    public static ArrayList<Route> routeList;
    public static ArrayList<Stop> stopList;

    @FXML ListView busUIList;
    @FXML ListView routesUIList;
    @FXML ListView stopsUIList;
    @FXML Button logoutButton;

    @FXML
    public void logout(ActionEvent event) throws IOException{
        logoutButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../welcome/welcomeScreen.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        busList = ReadCSV.getBuses();
        routeList = ReadCSV.getRoutes();
        stopList = ReadCSV.getStops();

        ObservableList oListBus = FXCollections.observableArrayList();
        for(Bus bus: busList) {
            oListBus.add(bus.getId());
        }
        busUIList.setItems(oListBus);

        ObservableList oListStop = FXCollections.observableArrayList();
        for(Stop s: stopList) {
            oListStop.add(s.getName());
        }
        stopsUIList.setItems(oListStop);

        ObservableList oListRoute = FXCollections.observableArrayList();
        for(Route r: routeList){
            oListRoute.add(r.getName());
        }
        routesUIList.setItems(oListRoute);
    }

    @FXML
    public void selectBus(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bus/busInfo.fxml"));
            stage.setScene(new Scene(loader.load()));

            BusInfo control = loader.getController();
            control.initData(busList.get(busUIList.getSelectionModel().getSelectedIndex()));

            stage.show();
        }
    }

    @FXML
    public void selectStop(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("stop/stopInfo.fxml"));
            stage.setScene(new Scene(loader.load()));

            StopInfo control = loader.getController();
            control.initData(stopList.get(stopsUIList.getSelectionModel().getSelectedIndex()));

            stage.show();
        }
    }

    @FXML
    public void selectRoute(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("route/routeInfo.fxml"));
            stage.setScene(new Scene(loader.load()));

            RouteInfo control = loader.getController();
            control.initData(routeList.get(routesUIList.getSelectionModel().getSelectedIndex()));

            stage.show();
        }
    }
}
