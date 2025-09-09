package com.davidclue.tanksii.entities.projectiles;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.LivingEntity;
import com.davidclue.tanksii.map.TileFace;
import com.davidclue.tanksii.util.Velocity;

public class RayCast extends Entity {
	
	private Entity shooter;
	private int bounces;
	private double posX,posY;

	public RayCast(Level level, double x, double y, double width, double height, Entity shooter) {
		super(level, x, y);
		this.width = width;
		this.height = height;
		this.posX = x;
		this.posY = y;
		this.shooter = shooter;
	}
	public boolean simulateCast(Entity target, Velocity castVelocity, int maxBounces, int immuneFrames, int lifeSpan) {
		this.velocity = castVelocity.clone();
		for (int i=0; i < lifeSpan; i++) {
			TileFace face = simulateMapCollsions(false);
//			if (face == null)
//				level.handler.addParticle(new Particle(level, x, y, width, height, 60, Color.GREEN));
//			else
//				level.handler.addParticle(new Particle(level, x, y, width, height, 60, Color.BLUE));
			if (face == TileFace.RIGHT || face == TileFace.LEFT) {
				castVelocity.reverseXVel();
				bounces++;
				if (bounces > maxBounces)
					return false;
			} else if (face != null) {
				castVelocity.reverseYVel();
				bounces++;
				if (bounces > maxBounces)
					return false;
			}
			x += castVelocity.getX();
			y += castVelocity.getY();
			for (Entity obj : level.handler.getObjectList())
				if (obj instanceof LivingEntity && obj.collides(x, y, width, height)) {
					if (obj.equals(shooter) && immuneFrames > 0)
						break;
					return obj.equals(target);
				}
			immuneFrames--;
		}
		return false;
	}
	public int getBounces() {
		return bounces;
	}
	public void setBounces(int bounces) {
		this.bounces = bounces;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
}
