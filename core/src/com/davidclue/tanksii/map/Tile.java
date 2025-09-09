package com.davidclue.tanksii.map;

import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Tile {
	
	protected TileType type;
	protected Level level;
	protected int tileX, tileY;
	protected double x, y, width, height;
	protected int light;
	
	public Tile(Level level, int tileX, int tileY) {
		this.level = level;
		this.tileX = tileX;
		this.tileY = tileY;
		this.x = tileX * TanksII.tileSize;
		this.y = tileY * TanksII.tileSize;
	}
	public void render(double x, double y, ShapeDrawer drawer) {
	}
	public void update() {
	}
	public void setType(TileType type, boolean updateAdjacent) {
		level.setTile(tileX, tileY, type);
		if (updateAdjacent)
			for (TileFace face : TileFace.adjacentFaces)
				getRelative(face).update();
	}
	public void setType(TileType type) {
		setType(type, true);
	}
	public Tile getRelative(TileFace face) {
		switch (face) {
		case UP:
			return level.getTileChunk(tileX, tileY-1);
		case DOWN:
			return level.getTileChunk(tileX, tileY+1);
		case LEFT:
			return level.getTileChunk(tileX-1, tileY);
		case RIGHT:
			return level.getTileChunk(tileX+1, tileY);
		default:
			return level.getVoidTile();
		}
	}
	public Queue<Tile> getRelatives(boolean includeDiagnols) {
		Queue<Tile> tiles = new ArrayDeque<>(Arrays.asList(getRelative(TileFace.UP), getRelative(TileFace.DOWN), getRelative(TileFace.LEFT), getRelative(TileFace.RIGHT)));
		if (includeDiagnols)
			tiles.addAll(Arrays.asList(getRelative(TileFace.TOP_LEFT), getRelative(TileFace.TOP_RIGHT), getRelative(TileFace.BOTTOM_RIGHT), getRelative(TileFace.BOTTOM_LEFT)));
		return tiles;
	}
	public void addLight(int light) {
		byte r = (byte) (Math.min(getRedLighting() + (light & 15), 15));
		byte g = (byte) (Math.min(getGreenLighting() + ((light >> 4) & 15), 15));
		byte b = (byte) (Math.min(getBlueLighting() + ((light >> 8) & 15), 15));
		byte a = (byte) (Math.min(getAlpha() + (light >> 12), 15));
		this.light = (a << 12) | (b << 8) | (g << 4) | r;
	}
	public void removeLight(int light) {
		byte r = (byte) (Math.max(getRedLighting() - (light & 15), 0));
		byte g = (byte) (Math.max(getGreenLighting() - ((light >> 4) & 15), 0));
		byte b = (byte) (Math.max(getBlueLighting() - ((light >> 8) & 15), 0));
		byte a = (byte) (Math.max(getAlpha() - (light >> 12), 0));
		this.light = (a << 12) | (b << 8) | (g << 4) | r;
	}
	public float getRedLighting() {
		return light & 15;
	}
	public float getGreenLighting() {
		return (light >> 4) & 15;
	}
	public float getBlueLighting() {
		return (light >> 8) & 15;
	}
	public float getAlpha() {
		return light >> 12;
	}
	public boolean isSolid() {
		return false;
	}
	public TileType getType() {
		return type;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public int getTileX() {
		return tileX;
	}
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	public int getTileY() {
		return tileY;
	}
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public int getLight() {
		return light;
	}
	public void setLight(int light) {
		this.light = light;
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
}
