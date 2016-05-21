package com.indigo.tag.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shane on 5/20/2016.
 */
public class Coordinate implements Parcelable {
    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private Coordinate(Parcel in){
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        
        Coordinate coordinate = (Coordinate) o;

        return coordinate.getLatitude() == this.getLatitude() &&
                coordinate.getLongitude() == this.getLongitude();
    }

    @Override
    public int hashCode() {
        final long latitudeFieldBits = Double.doubleToLongBits(latitude);
        final long longitudeFieldBits = Double.doubleToLongBits(longitude);

        int result = 17;
        result = 31 * result + (int) (latitudeFieldBits ^ (latitudeFieldBits >>> 32));
        result = 31 * result + (int) (longitudeFieldBits ^ (longitudeFieldBits >>> 32));

        return result;
    }

    @Override
    public String toString() {
        return "Latitude: " + latitude + "\n" +
                "Longitude: " + longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Parcelable.Creator<Coordinate> CREATOR
            = new Parcelable.Creator<Coordinate>() {

        @Override
        public Coordinate createFromParcel(Parcel source) {
            return new Coordinate(source);
        }

        @Override
        public Coordinate[] newArray(int size) {
            return new Coordinate[size];
        }
    };
}