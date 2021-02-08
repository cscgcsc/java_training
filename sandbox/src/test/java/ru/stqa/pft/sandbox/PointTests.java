package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testCoincidentCoordinates() {
        Point p1 = new Point(10.0, 10.0);
        Point p2 = new Point(10.0, 10.0);
        Assert.assertEquals(p1.calculateDistance(p1, p2), 0.0, "Distance between (" + p1.x + ", " + p1.y + ") and (" + p2.x + ", " + p2.y + ") is calculated incorrectly");
    }

    @Test
    public void testZeroCoordinates() {
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(0.0, 0.0);
        Assert.assertEquals(p1.calculateDistance(p1, p2), 0.0, "Distance between (" + p1.x + ", " + p1.y + ") and (" + p2.x + ", " + p2.y + ") is calculated incorrectly");
    }

    @Test
    public void testNegativeFractionsCoordinates() {
        Point p1 = new Point(-578.443578, -999.999999);
        Point p2 = new Point(-146.865767, -267.346567);
        Assert.assertEquals(p1.calculateDistance(p1, p2), 850.3178572562913, "Distance between (" + p1.x + ", " + p1.y + ") and (" + p2.x + ", " + p2.y + ") is calculated incorrectly");
    }

    @Test
    public void testPositiveWholeCoordinates() {
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(4.0, 5.0);
        Assert.assertEquals(p1.calculateDistance(p1, p2), 5.0, "Distance between (" + p1.x + ", " + p1.y + ") and (" + p2.x + ", " + p2.y + ") is calculated incorrectly");
    }
}
