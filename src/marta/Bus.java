package marta;

import java.util.Objects;
import java.util.Random;

public class Bus {
    private int id;
    private int route;
    private int location;
    private int riders;
    private int speed;
    private int nextExit;
    private int nextBoard;

    public Bus(int id, int route, int location, int riders, int speed) {
        this.id = id;
        this.route = route;
        this.location = location;
        this.riders = riders;
        this.speed = speed;
        nextExit = exit();
        nextBoard = board();
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
}
