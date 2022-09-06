package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import general.Level;

public class Tile {
	
	protected TileType type;
	protected Level level;
	protected int x, y, width, height;
	
	public Tile(Level level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
	}
	public void render(int x, int y, Graphics g) {
	}
	public boolean isSolid() {
		return false;
	}
	public TileType getType() {
		return type;
	}
	public void setType(TileType type) {
		this.type = type;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int) y, width, height);
	}
}
