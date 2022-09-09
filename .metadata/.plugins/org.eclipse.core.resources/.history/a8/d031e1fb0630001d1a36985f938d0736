package entities;

import java.awt.Graphics;

import general.Level;
import objects.Tile;
import objects.TileFace;
import util.Velocity;

public abstract class GameObject {
	protected double x, y;
	protected Velocity velocity = new Velocity(0, 0);
	protected double width,height;
	protected ID id;
	protected Level level;
	protected boolean shouldRemove;
	
	public GameObject(Level level, double x, double y, double width, double height, ID id) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	public GameObject(Level level, double x, double y, double width, double height) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void tick(Level level);
	public abstract void render(Graphics g);

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
	public Velocity getVelocity() {
		return velocity.clone();
	}
	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}
	public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
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
	public boolean isShouldRemove() {
		return shouldRemove;
	}
	public void setShouldRemove(boolean shouldRemove) {
		this.shouldRemove = shouldRemove;
	}
	public boolean collides(double tempX, double tempY, double tempWidth, double tempHeight) {
		return (tempX+tempWidth > x && tempX < x+width && tempY+tempHeight > y && tempY < y+height);
	}
	public TileFace simulateMapCollsions(boolean patchVelocity) {
		double farX = x+width, farY = y+height;
		TileFace face = null;
		Tile tile = level.getTile(x+velocity.getX(), y+5);
		if (tile.isSolid() && tile.getX()+tile.getWidth() > x+velocity.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX()+tile.getWidth() - x);
			face = TileFace.LEFT;
		}
		tile = level.getTile(x+velocity.getX(), farY-5);
		if (tile.isSolid() && tile.getX()+tile.getWidth() > x+velocity.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX()+tile.getWidth() - x);
			face = TileFace.LEFT;
		}
		tile = level.getTile(farX+velocity.getX(), y+5);
		if (tile.isSolid() && farX+velocity.getX() > tile.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX() - farX);
			face = TileFace.RIGHT;
		}
		tile = level.getTile(farX+velocity.getX(), farY-5);
		if (tile.isSolid() && farX+velocity.getX() > tile.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX() - farX);
			face = TileFace.RIGHT;
		}
		
		tile = level.getTile(x+5, y+velocity.getY());
		if (tile.isSolid() && tile.getY()+tile.getHeight() > y+velocity.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY()+tile.getHeight() - y);
			face = TileFace.UP;
		}
		tile = level.getTile(farX-5, y+velocity.getY());
		if (tile.isSolid() && tile.getY()+tile.getHeight() > y+velocity.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY()+tile.getHeight() - y);
			face = TileFace.UP;
		}
		tile = level.getTile(x+5, farY+velocity.getY());
		if (tile.isSolid() && farY+velocity.getY() > tile.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY() - farY);
			face = TileFace.DOWN;
		}
		tile = level.getTile(farX-5, farY+velocity.getY());
		if (tile.isSolid() && farY+velocity.getY() > tile.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY() - farY);
			face = TileFace.DOWN;
		}
		return face;
	}
	public void addVelocity() {
		x += velocity.getX();
		y += velocity.getY();
	}
}
