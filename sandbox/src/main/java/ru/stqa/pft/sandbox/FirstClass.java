package ru.stqa.pft.sandbox;

public class FirstClass {
	public static void main(String[] args) {

		System.out.println("Hello world!");

		Point p1 = new Point(10.45646456, -20.0);
		Point p2 = new Point(15.0, 25.0);

		double scale = Math.pow(10, 2);
		double distance = Math.round(p1.calculateDistance(p1, p2) * scale) / scale;
		System.out.println("Distance: " + distance);
	}		
}	