package com.davidclue.tanksii.entities;

import com.davidclue.tanksii.Level;

public class LivingEntity extends Entity {
	
	protected double health = 10.0;
	protected double bulletDamage = 10.0;
	protected double armor = 0.0;
	protected double movementSpeed = 5.0;
	protected double acceleration = 1.0;
	protected double decceleration = 0.5;

	public LivingEntity(Level level, double x, double y) {
		super(level, x, y);
	}
	public void damageEntity(double damage) {
		if (this.isImmune())
			return;
		this.health -= Math.max(damage - armor, 1.0);
		if (health <= 0) {
			remove();
		}
	}
}
