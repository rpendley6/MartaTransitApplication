package marta;

import java.util.Objects;

public class Bus {
    private int id;
    private int route;
    private int location;
    private int riders;
    private int speed;

    public Bus(int id, int route, int location, int riders, int speed) {
        this.id = id;
        this.route = route;
        this.location = location;
        this.riders = riders;
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bus)) return false;
        Bus bus = (Bus) o;
        return id == bus.id &&
                route == bus.route &&
                speed == bus.speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, location, riders, speed);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getRiders() {
        return riders;
    }

    public void setRiders(int riders) {
        this.riders = riders;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
