package ru.stqa.pft.sandbox;

public class Point {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double calculateDistance(Point pointFirst, Point pointSecond) {
        return Math.sqrt(Math.pow((pointSecond.x - pointFirst.x), 2) + Math.pow((pointSecond.y - pointFirst.y), 2));
    }
}
