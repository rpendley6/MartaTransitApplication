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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
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
    @FXML Canvas map;

    private static double hscale = 1;
    private static double wscale = 1;

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

        setupMap();
        drawMap();
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
        sim.nextToArrive();
        drawMap();
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
        drawMap();
    }

    void setupMap() {
        double height = 0.0;
        double width = 0.0;
        for (Stop s: sim.data.stopList) {
            if (s.getLongitude() > width) {
                width = s.getLongitude();
            }
            if (s.getLatitude() > height) {
                height = s.getLatitude();
            }
        }
        hscale = (430 - 100) / height;
        wscale = (800 - 100) / width;
    }

    private void drawMap() {
        GraphicsContext graphics = map.getGraphicsContext2D();
        graphics.clearRect(0,0, 800, 430);
        Image stopImg = new Image("gui/stop.png", 5, 5, false, true);
        graphics.setFont(new Font(12));
        //Draw Stops
        for(Stop s: sim.data.stopList) {
            graphics.drawImage(stopImg, s.getLongitude() * wscale + 50, s.getLatitude() * hscale + 50);
            graphics.strokeText(s.getName(), s.getLongitude() * wscale + 40, s.getLatitude() * hscale + 70);
        }

        //Draw Lines
        for(Route r: sim.data.routeList) {
            Stop[] path = r.getPath();
            int i = 0;
            while (i < path.length - 1 && path[i + 1] != null) {
                graphics.strokeLine(path[i].getLongitude() * wscale + 50, path[i].getLatitude() * hscale + 50,
                        path[i + 1].getLongitude() * wscale + 50, path[i + 1].getLatitude() * hscale + 50);
                i++;
            }
            graphics.strokeLine(path[i].getLongitude() * wscale + 50, path[i].getLatitude() * hscale + 50,
                    path[0].getLongitude() * wscale + 50, path[0].getLatitude() * hscale + 50);
        }

        Image busImg = new Image("/gui/bus.png", 20.0, 20.0, false, true);

        //Place Buses
        for (Bus b: sim.data.busList) {
            Stop stop = b.getCurrentStop();
            graphics.drawImage(busImg, stop.getLongitude() * wscale + 40, stop.getLatitude() * hscale + 40);
            if (stop.busesAtStop().size() <= 1) {
                graphics.strokeText(String.valueOf(b.getId()), stop.getLongitude() * wscale + 60, stop.getLatitude() * hscale + 40);
            } else {
                String multiBusLabel = "";
                for (Bus b1 : stop.busesAtStop()) {
                    if (b1 != null) {
                        multiBusLabel += b1.getId() + ", ";
                    }
                }
                if (multiBusLabel.length() > 3) {
                    multiBusLabel = multiBusLabel.substring(0, multiBusLabel.length() - 2);
                }
                graphics.strokeText(multiBusLabel, stop.getLongitude() * wscale + 60, stop.getLatitude() * hscale + 40);
            }
        }
    }

}
