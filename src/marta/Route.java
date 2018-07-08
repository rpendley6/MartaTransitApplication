package marta;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Route implements Serializable {
    private int id;
    private String name;
    private Stop[] path;

    public Route(int id, String name, Stop[] path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public Route(int id, String name) {
        this(id, name, new Stop[10]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return id == route.id &&
                Arrays.equals(path, route.path);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(path);
        return result;
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

    public Stop[] getPath() {
        return path;
    }

    public void setPath(Stop[] path) {
        this.path = path;
    }
}
