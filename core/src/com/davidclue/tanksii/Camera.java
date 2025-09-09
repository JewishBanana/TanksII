package com.davidclue.tanksii;

import com.davidclue.tanksii.entities.Entity;

public class Camera {
	
	private double x, y, maxX, maxY, farX, farY;
	private double zoom = 1.0;
	private Entity trackingObject;
	private int tileWidth, tileHeight;
//	private boolean zoomTest;
	
	public Camera(int camX, int camY) {
		x = camX;
		y = camY;
		farX = x + TanksII.WIDTH;
		farY = y + TanksII.HEIGHT;
	}
	public void tick() {
//		if (!zoomTest) {
//			setZoom(zoom += 0.01);
//			if (zoom > 3.0)
//				zoomTest = true;
//		} else {
//			setZoom(zoom -= 0.005);
//			if (zoom < 0.01)
//				zoomTest = false;
//		}
		if (trackingObject != null) {
			x = (trackingObject.getX() + (trackingObject.getWidth() / 2) - (TanksII.WIDTH / 2 / zoom));
			y = (trackingObject.getY() + (trackingObject.getHeight() / 2) - (TanksII.HEIGHT / 2 / zoom));
			x = x < 0 ? maxX > 0 ? 0 : maxX / 2 : x > maxX ? maxX > 0 ? maxX : maxX / 2 : x;
			y = y < 0 ? maxY > 0 ? 0 : maxY / 2 : y > maxY ? maxY > 0 ? maxY : maxY / 2 : y;
			farX = x + (TanksII.WIDTH / zoom);
			farY = y + (TanksII.HEIGHT / zoom);
		}
	}
	public void setZoom(double multiplier) {
		zoom = multiplier;
		maxX = Math.min((tileWidth * TanksII.tileSize) - (TanksII.WIDTH / zoom / 2), (tileWidth * TanksII.tileSize) - (TanksII.WIDTH / zoom));
		maxY = Math.min((tileHeight * TanksII.tileSize) - (TanksII.HEIGHT / zoom / 2), (tileHeight * TanksII.tileSize) - (TanksII.HEIGHT / zoom));
	}
	public double getZoom() {
		return zoom;
	}
	public float getScreenX(double objX) {
		return (float) ((objX - x) * zoom);
	}
	public float getScreenY(double objY) {
		return (float) ((objY - y) * zoom);
	}
	public double getLevelX(double screenX) {
		return screenX / zoom + x;
	}
	public double getLevelY(double screenY) {
		return screenY / zoom + y;
	}
	public Entity getTrackingObject() {
		return trackingObject;
	}
	public void setTrackingObject(Entity object) {
		trackingObject = object;
	}
	public double getX() {
		return x;
	}
	public void setX(double newX) {
		x = newX;
	}
	public double getY() {
		return y;
	}
	public void setY(double newY) {
		y = newY;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double newMaxX) {
		maxX = newMaxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double newMaxY) {
		maxY = newMaxY;
	}
	public double getFarX() {
		return farX;
	}
	public void setFarX(double newFarX) {
		farX = newFarX;
	}
	public double getFarY() {
		return farY;
	}
	public void setFarY(double newFarY) {
		farY = newFarY;
	}
	public void setTileDimensions(int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		setZoom(zoom);
	}
	public int getTileWidth() {
		return tileWidth;
	}
	public int getTileHeight() {
		return tileHeight;
	}
}
