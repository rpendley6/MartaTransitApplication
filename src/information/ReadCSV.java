package information;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import marta.Bus;
import marta.Route;
import marta.Stop;

import java.io.*;
import java.util.ArrayList;

public class ReadCSV {

    public static String CSVPATH = null;

    public static void chooseFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Import File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CVS (.csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        CSVPATH = file.getPath();
    }

    public static ArrayList<Bus> getBuses() {
        String csvFileToRead = CSVPATH;
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        ArrayList<Bus> busArray = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                if (data[0].equals("bus")) {
                    int id = Integer.parseInt(data[1]);
                    int route = Integer.parseInt(data[2]);
                    int location = Integer.parseInt(data[3]);
                    int riders = Integer.parseInt(data[4]);
                    int speed = Integer.parseInt(data[6]);
                    int stop = -1;
                    for (Route r: getRoutes()) {
                        if (r.getId() == route) {
                            stop = r.getPath()[0].getId();
                        }
                    }
                    int stopCount = 0; //intitializes buses at beginning of route
                    Bus newBus = new Bus(id, route, location, riders, speed, stop, stopCount);
                    busArray.add(newBus);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return busArray;
    }
    public static ArrayList<Stop> getStops() {
        String csvFileToRead = CSVPATH;
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        ArrayList<Stop> stopArray = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                if (data[0].equals("stop")) {
                    int id = Integer.parseInt(data[1]);
                    String name = data[2];
                    double latitude = Double.parseDouble(data[4]);
                    double longitude = Double.parseDouble(data[5]);
                    Stop newStop = new Stop(id, name, latitude, longitude);
                    stopArray.add(newStop);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stopArray;
    }
    public static ArrayList<Route> getRoutes() {
        String csvFileToRead = CSVPATH;
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        ArrayList<Route> routeArray = new ArrayList<>();
        ArrayList<Stop> stopArray = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                if (data[0].equals("stop")) {
                    int id = Integer.parseInt(data[1]);
                    String name = data[2];
                    double latitude = Double.parseDouble(data[4]);
                    double longitude = Double.parseDouble(data[5]);
                    Stop newStop = new Stop(id, name, latitude, longitude);
                    stopArray.add(newStop);
                }
                if (data[0].equals("route")) {
                    int id = Integer.parseInt(data[1]);
                    String name = data[3];
                    Stop[] stops = new Stop[10];
                    int n = 0;
                    for (int i = 4; i <= 13; i++) {
                        for (Stop s: stopArray) {
                            if (Integer.parseInt(data[i]) == s.getId()) {
                                stops[n] = s;
                                n++;
                            }
                        }
                    }
                    Route newRoute = new Route(id, name, stops);
                    routeArray.add(newRoute);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return routeArray;
    }
}