package com.indigo.tag;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Shane on 5/20/2016.
 */
public class CoordinateTest {

    @Test
    public void testEquals() throws Exception {
        final Coordinate coordinate = new
    }

    @Test
    public void testGetLatitude() throws Exception {
        final Coordinate coordinate = new Coordinate(15.3, 60.34);
        assertEquals("Failed to access latitude", coordinate.getLatitude(), 15.3, 0.1);
        assertNotEquals("Loading incorrect latitude", coordinate.getLatitude(), 100.25, 0.0001);
    }

    @Test
    public void testGetLongitude() throws Exception {
        final Coordinate coordinate = new Coordinate(15.3, 20.101);
        assertEquals("Failed to access latitude", coordinate.getLongitude(), 20.101, 0.001);
        assertNotEquals("Loading incorrect latitude", coordinate.getLongitude(), 100.25, 0.0001);
    }
}