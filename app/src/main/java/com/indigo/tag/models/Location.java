package com.indigo.tag.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shane on 5/21/2016.
 */
public class Location implements Parcelable {
    private String name;
    private Coordinate coordinate;

    public Location(String name, Coordinate coordinate) {
        this.name = name;
        this.coordinate = coordinate;
    }

    public Location(String name, double latitude, double longitude) {
        this(name, new Coordinate(latitude, longitude));
    }

    private Location(Parcel in) {
        name = in.readString();
        coordinate = in.readParcelable(Coordinate.class.getClassLoader());
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        return location.getName().equals(name) &&
                location.getCoordinate().equals(coordinate);
    }

    @Override
    public int hashCode() {
        int result = coordinate.hashCode();
        result = result * 31 + name.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(coordinate, flags);
    }

    public static final Creator<Location> CREATOR
            = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
