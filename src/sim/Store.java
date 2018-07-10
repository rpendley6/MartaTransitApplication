package sim;

import information.ReadCSV;
import marta.Bus;
import marta.Route;
import marta.Stop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Store implements Serializable {
    public ArrayList<Bus> busList;
    public ArrayList<Route> routeList;
    public ArrayList<Stop> stopList;

    public Store(){
        busList = ReadCSV.getBuses();
        routeList = ReadCSV.getRoutes();
        stopList = ReadCSV.getStops();
    }

}
