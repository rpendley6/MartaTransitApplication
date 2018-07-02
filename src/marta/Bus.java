package marta;

import java.util.Objects;
import java.util.Random;

import static information.ReadCSV.getRoutes;

public class Bus {
    private int id;
    private int route;
    private int location;
    private int riders;
    private int speed;
    private int nextExit;
    private int nextBoard;
    private int stop;
    private int stopCount;

    public Bus(int id, int route, int location, int riders, int speed, int stop, int stopCount) {
        this.id = id;
        this.route = route;
        this.location = location;
        this.riders = riders;
        this.speed = speed;
        nextExit = exit();
        nextBoard = board();
        this.stop = stop;
        this.stopCount = stopCount;
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

    /**
     * Performs the changes on the riders based on the numbers generated and then generates
     * a new set of nextExit and nextBoard numbers
     */
    public void arrive() {
        riders -= nextExit;
        riders += nextBoard;
        nextExit = exit();
        nextBoard = board();
    }

    /**
     * Calculates the number exiting either random between 2 and 5(inclusive) or 2 and the current number of riders(inclusive)
     * @return random int
     */
    public int exit() {
        return riders < 5 ? randomNumber(2, riders) : randomNumber(2, 5);
    }

    /**
     * Calculates the number boarding
     * @return random int between 0 and 10
     */
    public int board() {
        return randomNumber(0, 10);
    }

    /**
     * Generates a random number between min and max (inclusive).
     * @param min
     * @param max
     * @return random number
     */
    public int randomNumber(int min, int max){
        Random rand = new Random();
        int randomNumber = rand.nextInt((min - max) + 1) + min;
        return randomNumber;
    }

    public void moveStops() {
        Stop[] path = null;
        for (Route r: getRoutes()) {
            if (r.getId() == route) {
                path = r.getPath();
            }
        }
        if (stopCount < path.length - 1) {
            stopCount++;
        } else {
            stopCount = 0;
        }
        stop = getNextStop();
    }
    /**
     *
     * @return the id of the next stop on the route
     */
    public int getNextStop() {
        Stop[] path = null;
        for (Route r: getRoutes()) {
            if (r.getId() == route) {
                path = r.getPath();
            }
        }
        if (stopCount < path.length - 1) {
            return path[stopCount + 1].getId();
        } else {
            return path[0].getId();
        }
    }

    /**
     *
     * @return the distance between the current stop and next stop
     */
    public double distance() {
        Stop[] path = null;
        Stop next;
        Stop current = null;
        for (Route r: getRoutes()) {
            if (r.getId() == route) {
                path = r.getPath();
            }
        }
        if (stopCount < path.length - 1) {
            next = path[stopCount + 1];
        } else {
            next = path[0];
        }
        double h = Math.abs(next.getLongitude() - current.getLongitude());
        double w = Math.abs(next.getLatitude() - current.getLatitude());
        return hypotenuse(h, w);
    }

    /**
     *
     * @return the time to travel to the next stop based on distance and speed
     */
    public double timeToNext() {
        return distance() / speed;
    }

    /**
     *
     * @param h difference in latitude
     * @param w difference in longitude
     * @return hypotenuse of differences
     */
    public double hypotenuse(double h, double w) {
        return Math.sqrt(h * h + w * w);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, location, riders, speed);
    }

    public int getNextExit() {
        return nextExit;
    }

    public int getNextBoard() {
        return nextBoard;
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

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }
}
