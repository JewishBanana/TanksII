package com.davidclue.tanksii;

import com.davidclue.tanksii.entities.Entity;

public class Camera {
	
	private static double x, y, maxX, maxY, farX, farY;
	private static Entity trackingObject;
	
	public Camera(int camX, int camY) {
		x = camX;
		y = camY;
		farX = x + TanksII.WIDTH;
		farY = y + TanksII.HEIGHT;
	}
	public static void tick() {
		if (trackingObject != null) {
			x = trackingObject.getX() - TanksII.WIDTH/2 + (trackingObject.getWidth()/2);
			y = trackingObject.getY() - TanksII.HEIGHT/2 + (trackingObject.getHeight()/2);
			x = x < 0 ? 0 : x > maxX ? maxX : x;
			y = y < 0 ? 0 : y > maxY ? maxY : y;
			farX = x + TanksII.WIDTH;
			farY = y + TanksII.HEIGHT;
		}
	}
	public static float getFixedX(double tempX) {
		return (float) (tempX - x);
	}
	public static float getFixedY(double tempY) {
		return (float) (tempY - y);
	}
	public static Entity getTrackingObject() {
		return trackingObject;
	}
	public static void setTrackingObject(Entity object) {
		trackingObject = object;
	}
	public static double getX() {
		return x;
	}
	public static void setX(double newX) {
		x = newX;
	}
	public static double getY() {
		return y;
	}
	public static void setY(double newY) {
		y = newY;
	}
	public static double getMaxX() {
		return maxX;
	}
	public static void setMaxX(double newMaxX) {
		maxX = newMaxX;
	}
	public static double getMaxY() {
		return maxY;
	}
	public static void setMaxY(double newMaxY) {
		maxY = newMaxY;
	}
	public static double getFarX() {
		return farX;
	}
	public static void setFarX(double newFarX) {
		farX = newFarX;
	}
	public static double getFarY() {
		return farY;
	}
	public static void setFarY(double newFarY) {
		farY = newFarY;
	}
}
