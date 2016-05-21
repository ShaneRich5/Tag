package com.indigo.tag.models;

import com.indigo.tag.models.Coordinate;
import com.indigo.tag.models.Location;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Shane on 5/21/2016.
 */
public class LocationTest {

    @Test
    public void testEquals() throws Exception {
        Coordinate coordinateA = new Coordinate(134.5563, 225.456);
        Coordinate coordinateC = new Coordinate(24.32, 245.46);

        Location locationA = new Location("DownTown", coordinateA);
        Location locationB = new Location("DownTown", coordinateA);
        Location locationC = new Location("NotDownTown", coordinateC);


        assertTrue("Equals method invalid", locationA.equals(locationB));
        assertFalse("Equals method invalid", locationB.equals(locationC));
    }
}