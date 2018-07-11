package marta;

import javafx.beans.property.SimpleIntegerProperty;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Random;

import static information.ReadCSV.getRoutes;

public class Bus implements Comparable<Bus>, Serializable {
    private int id;
    private int route;
    private int location;
    private int riders;
    private int speed;
    private int stop;
    private int stopCount;
    private int timeToNext;
    private transient int nextExit;
    private transient int nextBoard;
    private transient SimpleIntegerProperty pId;
    private transient SimpleIntegerProperty pStop;
    private transient SimpleIntegerProperty pass;

    public Bus(int id, int route, int location, int riders, int speed, int stop, int stopCount) {
        this.id = id;
        pId = new SimpleIntegerProperty(id);
        this.route = route;
        this.location = location;
        this.riders = riders;
        pass = new SimpleIntegerProperty(riders);
        this.speed = speed;
        nextExit = exit();
        nextBoard = board();
        this.stop = stop;
        pStop = new SimpleIntegerProperty(stop);
        this.stopCount = stopCount;
        calcTimeToNext();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(id);
        out.writeInt(route);
        out.writeInt(location);
        out.writeInt(riders);
        out.writeInt(speed);
        out.writeInt(stop);
        out.writeInt(stopCount);
        out.writeInt(timeToNext);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        route = in.readInt();
        location = in.readInt();
        riders = in.readInt();
        speed = in.readInt();
        stop = in.readInt();
        stopCount = in.readInt();
        timeToNext = in.readInt();
        nextBoard = board();
        nextExit = exit();
        pId = new SimpleIntegerProperty(id);
        pStop = new SimpleIntegerProperty(stop);
        pass = new SimpleIntegerProperty(riders);
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
        setRiders(riders - nextExit);
        setRiders(riders += nextBoard);
        nextExit = exit();
        nextBoard = board();
        calcTimeToNext();
        moveStops();
    }

    /**
     * Calculates the number exiting either random between 2 and 5(inclusive) or 2 and the current number of riders(inclusive)
     * @return random int
     */
    private int exit() {
        if (riders < 5) {
            if (riders < 2) {
                return riders;
            }
            return randomNumber(2, riders);
        }
        return randomNumber(2, 5);
    }

    /**
     * Calculates the number boarding
     * @return random int between 0 and 10
     */
    private int board() {
        return randomNumber(2, 10);
    }

    /**
     * Generates a random number between min and max (inclusive).
     * @param min
     * @param max
     * @return random number
     */
    private int randomNumber(int min, int max){
        Random rand = new Random();
        int randomNumber = rand.nextInt((max - min) + 1) + min;
        return randomNumber;
    }

    private void moveStops() {
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
        setStop(getNextStop());
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
        for (Route r: getRoutes()) {
            if (r.getId() == route) {
                path = r.getPath();
            }
        }
        Stop current = path[stopCount];
        if (stopCount < path.length - 1) {
            next = path[stopCount + 1];
        } else {
            next = path[0];
        }
        double h = Math.abs(next.getLongitude() - current.getLongitude());
        double w = Math.abs(next.getLatitude() - current.getLatitude());
        double d = hypotenuse(h, w) * 70;
        DecimalFormat df = new DecimalFormat("#.##");
        d = Double.valueOf(df.format(d));
        return d;
    }

    /**
     *
     * @return the time to travel to the next stop based on distance and speed
     */
    private void calcTimeToNext() {
        setTimeToNext(1 + ((int) distance() * 60) / speed);
    }

    /**
     *
     * @param h difference in latitude
     * @param w difference in longitude
     * @return hypotenuse of differences
     */
    private double hypotenuse(double h, double w) {
        return Math.sqrt(h * h + w * w);
    }


    @Override
    public int compareTo(Bus b1) {
        return this.timeToNext - b1.getTimeToNext();
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    public void setTimeToNext(int timeToNext) {
        this.timeToNext = timeToNext;
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
        pId.set(this.id);
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
        pass.set(this.riders);
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
        pStop.set(this.stop);
    }

    public int getStopCount() {
        return stopCount;
    }

    public int getpId() {
        return pId.get();
    }

    public SimpleIntegerProperty pIdProperty() {
        return pId;
    }

    public int getpStop() {
        return pStop.get();
    }

    public SimpleIntegerProperty pStopProperty() {
        return pStop;
    }

    public int getPass() {
        return pass.get();
    }

    public SimpleIntegerProperty passProperty() {
        return pass;
    }
}
