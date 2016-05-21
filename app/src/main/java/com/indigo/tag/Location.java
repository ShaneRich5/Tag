package com.indigo.tag;

/**
 * Created by Shane on 5/21/2016.
 */
public class Location {
    private String name;
    private Coordinate coordinate;

    public Location(String name, Coordinate coordinate) {
        this.name = name;
        this.coordinate = coordinate;
    }

    public Location(String name, double latitude, double longitude) {
        this(name, new Coordinate(latitude, longitude));
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
