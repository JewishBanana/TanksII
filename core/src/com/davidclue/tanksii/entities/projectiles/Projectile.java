package com.davidclue.tanksii.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.ID;
import com.davidclue.tanksii.entities.LivingEntity;
import com.davidclue.tanksii.objects.TileFace;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Projectile extends Entity {
	
	protected Entity shooter;
	protected int immuneFrames = 20;
	protected int bounces = 1;
	protected double damage = 10.0;

	public Projectile(Level level, double x, double y, double width, double height, ID id) {
		super(level, x, y, width, height, id);
	}
	public Projectile(Level level, double x, double y, double width, double height, ID id, Entity shooter) {
		super(level, x, y, width, height, id);
		this.shooter = shooter;
	}
	@Override
	public void tick() {
		TileFace face = simulateMapCollsions(false);
		if (face == TileFace.RIGHT || face == TileFace.LEFT) {
			velocity.reverseXVel();
			bounces--;
		} else if (face != null) {
			velocity.reverseYVel();
			bounces--;
		}
		if (bounces < 0)
			dead = true;
		addVelocity();
		
		for (Entity obj : TanksII.handler.getObjectList())
			if (obj instanceof LivingEntity && obj.collides(x, y, width, height) && (immuneFrames <= 0 || !obj.equals(shooter))) {
				((LivingEntity) obj).damageEntity(damage);
				dead = true;
				return;
			} else if (obj instanceof Projectile && !obj.equals(this) && obj.collides(x, y, width, height)) {
				dead = true;
				obj.setDead(true);
				return;
			}
		immuneFrames--;
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
