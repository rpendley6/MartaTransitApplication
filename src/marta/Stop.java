package marta;

import information.*;

import java.io.Serializable;
import java.util.*;

public class Stop implements Serializable {
    private int id;
    private String name;
    private double latitude;
    private double longitude;

    public Stop(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;
        Stop stop = (Stop) o;
        return id == stop.id &&
                Double.compare(stop.latitude, latitude) == 0 &&
                Double.compare(stop.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Set<Bus> busesAtStop() {
        Set<Bus> busesAtStop = new HashSet<>();
        for (Bus b: MainList.sim.data.busList) {
            if (b.getLocation() == this.getId()) {
                busesAtStop.add(b);
            }
        }

        return busesAtStop;
    }
}
