package sim;

import information.ReadCSV;
import marta.Bus;
import marta.Route;
import marta.Stop;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable {
    public List<Bus> busList;
    public List<Route> routeList;
    public List<Stop> stopList;

    public Store(){
        busList = ReadCSV.getBuses();
        routeList = ReadCSV.getRoutes();
        stopList = ReadCSV.getStops();
    }

}
