package com.davidclue.tanksii.entities;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.projectiles.Projectile;
import com.davidclue.tanksii.entities.projectiles.RayCast;
import com.davidclue.tanksii.util.Velocity;

public class Enemy extends LivingEntity {
	
	protected Entity target;
	protected Controller controller;

	public Enemy(Level level, double x, double y) {
		super(level, x, y);
		this.controller = new Controller(this);
	}
	public void tick() {
		controller.tick();
		
		super.tick();
	}
	public Entity getTarget() {
		return target;
	}
	public void setTarget(Entity target) {
		this.target = target;
	}
	public boolean shootAtTarget(double size, int bounces, int immuneFrames, int lifeSpan, double bulletSpeed) {
		double angle = Math.PI/30;
		RayCast cast = null;
		for (int i=0; i < 60; i++) {
			RayCast tempCast = new RayCast(level, x+(width/2)-(size/2), y+(height/2)-(size/2), size, size, this);
			if (tempCast.simulateCast(target, new Velocity(Math.cos(i*angle), Math.sin(i*angle)).normalize().multiply(bulletSpeed), bounces, immuneFrames, lifeSpan))
				if (cast == null || tempCast.getBounces() < cast.getBounces())
					cast = tempCast;
		}
		if (cast == null)
			return false;
		Projectile bullet = new Projectile(level, cast.getPosX(), cast.getPosY(), size, size, this);
		bullet.setVelocity(cast.getVelocity().normalize().multiply(bulletSpeed));
		bullet.setBounces(bounces);
		level.handler.addObject(bullet);
		return true;
	}
}
