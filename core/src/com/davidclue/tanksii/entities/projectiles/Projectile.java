package com.davidclue.tanksii.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.EntityType;
import com.davidclue.tanksii.entities.LivingEntity;
import com.davidclue.tanksii.map.TileFace;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Projectile extends Entity {
	
	protected Entity shooter;
	protected int immuneFrames = 20;
	protected int lifeTicks = 500;
	protected int bounces = 1;
	protected double damage = 10.0;

	public Projectile(Level level, double x, double y, double width, double height) {
		super(level, x, y);
		this.type = EntityType.PROJECTILE;
		this.width = width;
		this.height = height;
	}
	public Projectile(Level level, double x, double y, double width, double height, Entity shooter) {
		super(level, x, y);
		this.type = EntityType.PROJECTILE;
		this.width = width;
		this.height = height;
		this.shooter = shooter;
	}
	@Override
	public void tick() {
		if (lifeTicks-- == 0) {
			remove();
			return;
		}
		TileFace face = simulateMapCollsions(false);
		if (face == TileFace.RIGHT || face == TileFace.LEFT) {
			velocity.reverseXVel();
			bounces--;
		} else if (face != null) {
			velocity.reverseYVel();
			bounces--;
		}
		if (bounces < 0) {
			remove();
			return;
		}
		addVelocity();
		
		for (Entity obj : level.handler.getObjectList())
			if (obj instanceof LivingEntity && obj.collides(x, y, width, height) && (immuneFrames <= 0 || !obj.equals(shooter))) {
				((LivingEntity) obj).damageEntity(damage);
				remove();
				return;
			} else if (obj instanceof Projectile && !obj.equals(this) && obj.collides(x, y, width, height)) {
				remove();
				obj.remove();
				return;
			}
		immuneFrames--;
		
		if (!getTile().equals(lightUpdate)) {
			level.removeLight(lightUpdate, lighting);
			lightUpdate = getTile();
			level.addLight(lightUpdate, lighting);
		}
	}
	@Override
	public void render(ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.YELLOW);
	}
	public Entity getShooter() {
		return shooter;
	}
	public void setShooter(Entity shooter) {
		this.shooter = shooter;
	}
	public int getBounces() {
		return bounces;
	}
	public void setBounces(int bounces) {
		this.bounces = bounces;
	}
	public int getImmuneFrames() {
		return immuneFrames;
	}
	public void setImmuneFrames(int immuneFrames) {
		this.immuneFrames = immuneFrames;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
}
