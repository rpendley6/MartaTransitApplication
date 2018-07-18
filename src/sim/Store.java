package sim;

import information.ReadCSV;
import marta.Bus;
import marta.Route;
import marta.Stop;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    public final ArrayList<Bus> busList;
    public final ArrayList<Route> routeList;
    public final ArrayList<Stop> stopList;

    public Store(){
        busList = ReadCSV.getBuses();
        routeList = ReadCSV.getRoutes();
        stopList = ReadCSV.getStops();
    }

}
