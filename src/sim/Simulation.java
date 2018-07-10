package sim;

import marta.*;

import java.io.Serializable;
import java.util.PriorityQueue;

public class Simulation implements Serializable {

    public int ticks;
    public Store data;
    public PriorityQueue<Bus> pQueue;

    public Simulation() {
        ticks = 0;
        data = new Store();
        pQueue = new PriorityQueue<>(data.busList);
    }

    public Bus nextToArrive() {
        Bus nextBus = pQueue.remove();
        for(Object b: pQueue.toArray()) {
            Bus bus = (Bus) b;
            bus.setTimeToNext(bus.getTimeToNext() - nextBus.getTimeToNext());
        }
        nextBus.arrive();
        pQueue.add(nextBus);
        return nextBus;
    }
}
