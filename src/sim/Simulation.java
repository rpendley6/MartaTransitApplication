package sim;

import marta.*;

import java.io.Serializable;
import java.util.PriorityQueue;

public class Simulation implements Serializable {

    public Store data;
    private PriorityQueue<Bus> pQueue;

    public Simulation() {
        data = new Store();
        pQueue = new PriorityQueue<>(data.busList);
    }

    public void nextToArrive() {
        Bus nextBus = pQueue.remove();
        for(Object b: pQueue.toArray()) {
            Bus bus = (Bus) b;
            bus.setTimeToNext(bus.getTimeToNext() - nextBus.getTimeToNext());
        }
        nextBus.arrive();
        pQueue.add(nextBus);
    }

    public Store getData() { return this.data; }
}
