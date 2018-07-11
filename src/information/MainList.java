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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marta.Bus;
import marta.Route;
import marta.Stop;
import sim.Simulation;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainList implements Initializable {

    public static ArrayList<Bus> busList;
    public static ArrayList<Route> routeList;
    public static ArrayList<Stop> stopList;
    public static Simulation sim = new Simulation();

    @FXML ListView busUIList;
    @FXML ListView routesUIList;
    @FXML ListView stopsUIList;
    @FXML Button logoutButton;
    @FXML Button loadButton;
    @FXML Button saveButton;
    @FXML TableView<Bus> simList;
    @FXML TableColumn<Bus, Integer> busCol;
    @FXML TableColumn<Bus, Integer> stopCol;
    @FXML TableColumn<Bus, Integer> passCol;

    @FXML
    public void logout(ActionEvent event) throws IOException{
        logoutButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("../welcome/welcomeScreen.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        busList = sim.data.busList;
        routeList = sim.data.routeList;
        stopList = sim.data.stopList;

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

        busCol.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("pId"));
        stopCol.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("pStop"));
        passCol.setCellValueFactory(new PropertyValueFactory<Bus, Integer>("pass"));
        simList.getItems().setAll(busList);
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

    @FXML
    public void step(ActionEvent event) {
        System.out.println("Step");
        sim.nextToArrive();
        simList.getItems().setAll(sim.data.busList);
    }

    @FXML
    public void saveButtonPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Simulation File");
        File file = fileChooser.showSaveDialog(saveButton.getScene().getWindow());
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ObjectOutputStream oOut = new ObjectOutputStream(fOut);
            oOut.writeObject(sim);
            oOut.close();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadButtonPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Simulation File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Simulation (.sim)", "*.sim"));
        File file = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
        try {
            FileInputStream fIn = new FileInputStream(file);
            ObjectInputStream oIn = new ObjectInputStream(fIn);
            sim = (Simulation) oIn.readObject();
            oIn.close();
            fIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        simList.getItems().setAll(sim.data.busList);
    }
}
