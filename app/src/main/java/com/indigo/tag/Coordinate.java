package com.indigo.tag;

/**
 * Created by Shane on 5/20/2016.
 */
public class Coordinate {
    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
}
