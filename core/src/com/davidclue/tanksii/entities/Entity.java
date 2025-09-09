package com.davidclue.tanksii.entities;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.map.Tile;
import com.davidclue.tanksii.map.TileFace;
import com.davidclue.tanksii.util.Velocity;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Entity {
	
	private static double collisionOffset = 18;
	
	protected double x, y;
	protected Velocity velocity = new Velocity(0, 0);
	protected double width,height;
	protected EntityType type;
	protected Level level;
	protected Tile lightUpdate;
	protected int lighting = 0x4000;
	
	private boolean dead;
	private boolean immune;
	
	public Entity(Level level, double x, double y) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = TanksII.tileSize;
		this.height = TanksII.tileSize;
		this.lightUpdate = getTile();
		this.level.getEntities().add(this);
	}
	public void tick() {
		simulateMapCollsions(true);
		addVelocity();
		updateLight(false);
		//lighting increase
		if (this.level.camera.getTrackingObject().equals(this)) {
			if (lighting < 0xF000) {
				lighting += 0x1000;
				updateLight(true);
			}
		} else if (lighting > 0x4000) {
			changeLighting(lighting - 0x1000);
			updateLight(true);
		}
	}
	public void render(ShapeDrawer drawer) {
	}
	public TileFace simulateMapCollsions(boolean patchVelocity) {
		double farX = x+width, farY = y+height;
		TileFace face = null;
		Tile tile = level.getTile(x+velocity.getX(), y+collisionOffset);
		if (tile.isSolid() && tile.getX()+tile.getWidth() > x+velocity.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX()+tile.getWidth() - x);
			face = TileFace.LEFT;
		}
		tile = level.getTile(x+velocity.getX(), farY-collisionOffset);
		if (tile.isSolid() && tile.getX()+tile.getWidth() > x+velocity.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX()+tile.getWidth() - x);
			face = TileFace.LEFT;
		}
		tile = level.getTile(farX+velocity.getX(), y+collisionOffset);
		if (tile.isSolid() && farX+velocity.getX() > tile.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX() - farX);
			face = TileFace.RIGHT;
		}
		tile = level.getTile(farX+velocity.getX(), farY-collisionOffset);
		if (tile.isSolid() && farX+velocity.getX() > tile.getX() && tile.getY()+tile.getHeight() >= y && tile.getY() <= farY) {
			if (patchVelocity)
				velocity.setX(tile.getX() - farX);
			face = TileFace.RIGHT;
		}
		
		tile = level.getTile(x+collisionOffset, y+velocity.getY());
		if (tile.isSolid() && tile.getY()+tile.getHeight() > y+velocity.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY()+tile.getHeight() - y);
			face = TileFace.UP;
		}
		tile = level.getTile(farX-collisionOffset, y+velocity.getY());
		if (tile.isSolid() && tile.getY()+tile.getHeight() > y+velocity.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY()+tile.getHeight() - y);
			face = TileFace.UP;
		}
		tile = level.getTile(x+collisionOffset, farY+velocity.getY());
		if (tile.isSolid() && farY+velocity.getY() > tile.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY() - farY);
			face = TileFace.DOWN;
		}
		tile = level.getTile(farX-collisionOffset, farY+velocity.getY());
		if (tile.isSolid() && farY+velocity.getY() > tile.getY() && tile.getX()+tile.getWidth() >= x && tile.getX() <= farX) {
			if (patchVelocity)
				velocity.setY(tile.getY() - farY);
			face = TileFace.DOWN;
		}
		return face;
	}
	public void changeLighting(int light) {
		level.removeLight(lightUpdate, lighting);
		this.lighting = light;
		level.addLight(lightUpdate, lighting);
	}
	public void updateLight(boolean force) {
		if (force || !getTile().equals(lightUpdate)) {
			level.removeLight(lightUpdate, lighting);
			lightUpdate = getTile();
			level.addLight(lightUpdate, lighting);
		}
	}
	public Tile getTile() {
		return level.getTile(x+width/2, y+height/2);
	}
	public void addVelocity() {
		x += velocity.getX();
		y += velocity.getY();
	}
	public boolean collides(double tempX, double tempY, double tempWidth, double tempHeight) {
		return (tempX+tempWidth > x && tempX < x+width && tempY+tempHeight > y && tempY < y+height);
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
	public Velocity getVelocity() {
		return velocity.clone();
	}
	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}
	public EntityType getType() {
		return type;
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
	public boolean isDead() {
		return dead;
	}
	public void remove() {
		this.dead = true;
		this.level.getEntities().remove(this);
		this.level.removeLight(lightUpdate, lighting);
	}
	public int getLighting() {
		return lighting;
	}
	public void setLighting(int lighting) {
		this.lighting = lighting;
	}
	public boolean isImmune() {
		return immune;
	}
	public void setImmune(boolean immune) {
		this.immune = immune;
	}
}
